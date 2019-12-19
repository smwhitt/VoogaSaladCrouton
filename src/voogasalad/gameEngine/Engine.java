package voogasalad.gameEngine;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import voogasalad.gameEngine.components.*;
import voogasalad.gameEngine.exceptions.FileCouldNotConvertException;
import voogasalad.gameEngine.exceptions.FileNotCreatedException;
import voogasalad.gameEngine.exceptions.ItemNotUpgradableException;
import voogasalad.gameEngine.exceptions.NotEnoughMoneyException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The primary driver for the components of the game engine. The Engine (indirectly) manages
 * the state of the game engine, including all the user-created objects and object-templates
 * that can be saved and loaded by the game player.
 * <p>
 * Engine is also the primary public interface for a frontend model to interact with the engine, as
 * well as start up the game player. This includes methods for creating new engine objects,
 * as well as configuring and editing them.
 * <p>
 * The basic structure of the objects in the engine (as Engine reveals publicly) is
 * string->string mappings with an associated ID, which is used in the Engine's API to uniquely
 * identify each engine object. The responsibility of any frontend connecting to the Engine is to
 * maintain its own mapping of ID to communicate with the Engine.
 *
 * @author Nishant Iyengar, Lakshya Bakshi, Milan Shah, Alex Qiao, Micheal Head
 */
public class Engine {
	private static final String DATA = "/data/";
	private static final String XML_TAG = ".xml";

	private EntityManager entityManager;
	private SystemsManager systemsManager;
	private XStream mySerializer;

	public Engine() {
		entityManager = new EntityManager();
		systemsManager = new SystemsManager(entityManager);
		mySerializer = new XStream(new DomDriver());
	}

	/**
	 * creates an object in the Engine with the given parameter mapping.
	 * The parameters provided are checked against the `type` given, and must contain
	 * all fields required by a given type, found in an associated .properties file.
	 *
	 * @param type   the identity of the object, e.g. Tower, Minion, Objective
	 * @param params the parameters necessary for the object as defined in the properties files
	 * @return a unique ID to associate the new engine object with.
	 */
	public int create(String type, Map<String, String> params) {
		Entity e = (new EntityFactory()).populate((entityManager.newEntity()), params, type);
		systemsManager.addEntity(e);
		System.out.println(String.format("Received an object of type %s with parameters %s. Its assigned ID is %s", type, params, e.id()));
		return e.id();
	}

	/**
	 * Resume play of the game player, from a paused state between waves.
	 */
	public void playgame() {systemsManager.playgame();}

	/**
	 * Edit the given set of fields of an object that has already been created in the engine.
	 * Any fields found with an associated value are overwritten, and any new fields are simply
	 * added on.
	 *
	 * @param params mapping of {attribute name} -> {value as String}
	 * @param id     ID of the entity to edit
	 * @return that same entity ID
	 */
	public int edit(int id, Map<String, String> params) {
		System.out.println("The original object at ID " + id + " is " + getEntity(id));
		System.out.println("This is then changing to " + params);

		Entity entity = entityManager.getEntity(id);

		Map<String, String> overwrittenParams = new HashMap<>(getEntity(id));
		overwrittenParams.putAll(params);

		systemsManager.removeComponents(id);
		entity.removeComponents();

		EntityFactory ef = new EntityFactory();
		if (overwrittenParams.containsKey("type")) {
			String type = overwrittenParams.remove("type");
			overwrittenParams.remove("activestatus");
			ef.populate(entity, overwrittenParams, type);
		} else {
			ef.populate(entity, overwrittenParams);
		}

		return id;
	}

	/**
	 * copy the object with the given ID (deeply!) and assign it a location and heading.
	 * The last bit is functionally identical to calling Engine.edit() with a location and heading.
	 *
	 * @param id    ID of the object to be copied.
	 * @param x,y   location of the copied object.
	 * @param angle heading of the copied object.
	 * @return the new entity's ID.
	 */
	public int place(int id, double x, double y, double angle) {
		if (systemsManager.placedefense(id)) {
			Entity copy = entityManager.copyOf(id);

			Map<String, String> params = Map.of("x", x + "", "y", y + "", "angle", angle + "");

			copy.addComponent(new Location(x + "", y + ""));
			copy.addComponent(new Angle(angle + ""));
			copy.addComponent(new AI("findstrong"));

			for (Component c : copy.getComponents()) {
				if (c instanceof ActiveStatus) {
					((ActiveStatus) c).setStatus(true);
				}
			}
			systemsManager.addEntity(copy);
			return copy.id();
		} else {
			return id;
		}
	}

	/**
	 * completely deletes the given object from the engine. Also provides the ID of the object just
	 * deleted, so any frontend can also be assured it destroys the object as well.
	 * <p>
	 * The ID occupied by the deleted object will NOT be overwritten or reused.
	 *
	 * @param id ID of the entity to delete
	 * @return ID of the entity just deleted
	 */
	public int delete(int id) {
		systemsManager.removeComponents(id);
		entityManager.deleteEntity(id);
		return id;
	}

	/**
	 * returns a set containing IDs of all active entities.
	 * <p>
	 * These ID-to-entity mappings form a unique correspondence, and individual IDs are never reused:
	 * once an ID is associated with an entity internally, that ID is assured to always map to
	 * that entity. The deletion of an ID from this list indicates that the entity has been
	 * deleted from the game, and that ID can be ignored.
	 * <p>
	 * changes made to the set of active entities are reflected in the returned set!
	 */
	public Set<Integer> getIDs() {
		return entityManager.getIDs();
	}

	/**
	 * returns a map view of the entity with the given ID.
	 * Specifically, that mapping is {attribute name} -> {value as String}
	 *
	 * @param id requested ID
	 * @return the map of parameters as associated
	 */
	public Map<String, String> getEntity(int id) {
		return entityManager.getEntity(id).asMap();
	}

	/**
	 * returns all entities. each entity (as a Map of String -> String) can be retrieved by an ID.
	 * <p>
	 * As this method uses `Engine.getEntity`, the returned map does not reflect changes made to
	 * the internal collection of entities.
	 *
	 * @return a map containing all entities.
	 */
	public Map<Integer, Map<String, String>> getEntities() {
		Map<Integer, Map<String, String>> ret = new HashMap<>();

		getIDs().forEach(i -> ret.put(i, getEntity(i)));

		return ret;
	}

	/**
	 * Returns all objects that should be drawn to the screen. This is formatted as a mapping from
	 * "title" to map view of the object (as would be returned by `Engine.getEntity`).
	 *
	 * @return A mapping of {String title} -> {map view of the associated engine object}
	 */
	public Map<String, Map<String, String>> getEntityVisuals() {
		Map<Integer, Map<String, String>> totalentities = (HashMap<Integer, Map<String, String>>) getEntities();
		Map<String, Map<String, String>> ret = new HashMap<>();
		for (int id : totalentities.keySet()) {
			if (getEntity(id).get("type").equals("Defense") || getEntity(id).keySet().containsAll(List.of("x", "y", "image"))) {
				ret.put(getEntity(id).get("title"), getEntity(id));
			}
		}
		return ret;
	}

	/**
	 * Updates the state of the game by the given time step.
	 *
	 * @param dt time step size, in seconds.
	 */
	public void update(double dt) {
		systemsManager.update(dt);
	}

	/**
	 * initialize the game loop.
	 */
	public void initGame() {
		systemsManager.initializeGame();
	}

	/**
	 * loads data from XML files, builds backend objects, and attaches the IDs to the active IDs list
	 *
	 * @param fXMLFile which is an XML File
	 * @return (probably doesn ' t need a return and just calls create over all objects loaded but thats fine
	 */
	public Map<Integer, Entity> loadData(File fXMLFile) throws FileCouldNotConvertException {
		Map<Integer, Entity> loadedmap = (Map<Integer, Entity>) mySerializer.fromXML(fXMLFile);
		try {
			entityManager = new EntityManager(loadedmap);
			systemsManager = new SystemsManager(entityManager);
			loadedmap.values().forEach(c -> systemsManager.addEntity(c));
			systemsManager.playgame();
			return loadedmap;
		} catch (Exception e) {
			throw (new FileCouldNotConvertException(" "));
		}
	}

	public Map<Integer, Entity> getEntityMap() {
		return entityManager.getEntities();
	}

	/**
	 * save all live objects to an XML file.
	 *
	 * @param nameOfGame name of the XML file
	 */
	public void save(String nameOfGame) throws FileNotCreatedException {
		var mapToSave = entityManager.getEntities();
		System.out.println(mapToSave);
		String mySavedEnemy = mySerializer.toXML(mapToSave);
		String xmlFilePath = System.getProperty("user.dir") + "/data/" + nameOfGame + ".xml";
		try {
			java.io.FileWriter fw = new java.io.FileWriter(xmlFilePath);
			fw.write(mySavedEnemy);
			fw.close();
		} catch (IOException e) {
			throw (new FileNotCreatedException(" "));
		}

	}

	/**
	 * upgrade the given entity.
	 *
	 * @param id the ID of the object to upgrade.
	 * @throws NotEnoughMoneyException
	 * @throws ItemNotUpgradableException
	 */
	public void upgrade(int id) throws NotEnoughMoneyException, ItemNotUpgradableException {
		systemsManager.upgrade(id);
	}

	/**
	 * filter for only entities that belong on the game player's map.
	 *
	 * @return map of all entities that belong on the map with only image, location and angle components in the form of
	 * Map(ID -> Map(Component Type (String) -> Component Value (String)))
	 */
	public Map<Integer, Map<String, String>> getMapEntities() {
		Map<Integer, Map<String, String>> filteredMap = new HashMap<>();
		for (int ID : getEntities().keySet()) {
			if (getEntity(ID).get("type").equalsIgnoreCase("path")) {
				if (Boolean.parseBoolean(getEntity(ID).get("activestatus"))) {
					Map<String, String> componentMap = new HashMap<>();
					componentMap.put("type", getEntity(ID).get("type"));
					componentMap.put("image", getEntity(ID).get("image"));
					componentMap.put("x", getEntity(ID).get("x"));
					componentMap.put("y", getEntity(ID).get("y"));
					componentMap.put("angle", getEntity(ID).get("angle"));
					filteredMap.put(ID, componentMap);
				}
			} else if (getEntity(ID).keySet().contains("x")) {
				Map<String, String> componentMap = new HashMap<>();
				componentMap.put("type", getEntity(ID).get("type"));
				componentMap.put("image", getEntity(ID).get("image"));
				componentMap.put("x", getEntity(ID).get("x"));
				componentMap.put("y", getEntity(ID).get("y"));
				if (getEntity(ID).keySet().contains("angle")) {
					componentMap.put("angle", getEntity(ID).get("angle"));
				}
				filteredMap.put(ID, componentMap);
			}
		}

		return filteredMap;
	}

	/**
	 * filter for only entities that belong on the game player's menu.
	 *
	 * @return map of all entities that belong on the menu with only image component in the form of
	 * Map(ID -> Map(Component Type (String) -> Component Value (String)))
	 */
	public Map<Integer, Map<String, String>> getMenuEntities() {
		Map<Integer, Map<String, String>> filteredMap = new HashMap<>();
		for (int ID : getEntities().keySet()) {
			if (!getEntity(ID).keySet().containsAll(List.of("location", "angle")) && getEntity(ID).keySet().containsAll(List.of("image")) && getEntity(ID).get("type").equalsIgnoreCase("defense")) {
				Map<String, String> componentMap = new HashMap<>();
				componentMap.put("image", getEntity(ID).get("image"));
				componentMap.put("cost", getEntity(ID).get("cost"));
				componentMap.put("type", getEntity(ID).get("type"));
				filteredMap.put(ID, componentMap);
			}
		}

		return filteredMap;
	}

	/**
	 * filter for game metadata
	 *
	 * @return map(string, string) with key value pairs: (tower->tower health), (resources->resource value), (level->level name),
	 * (game->game title), (score->score value)
	 */
	public Map<String, String> getMetaData() {
		Map<String, String> filteredMap = new HashMap<>();
		for (int ID : getEntities().keySet()) {
			if (getEntity(ID).get("type").equalsIgnoreCase("tower")) {
				filteredMap.put("tower", getEntity(ID).get("health"));
			} else if (getEntity(ID).get("type").equalsIgnoreCase("resources")) {
				filteredMap.put("resources", getEntity(ID).get("value"));
			} else if (getEntity(ID).get("type").equalsIgnoreCase("levels")) {
				filteredMap.put("levels", getEntity(ID).get("name"));
			}
		}
		return filteredMap;
	}
}
