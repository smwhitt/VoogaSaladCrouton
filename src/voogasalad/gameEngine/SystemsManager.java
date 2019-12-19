package voogasalad.gameEngine;

import voogasalad.gameEngine.components.*;
import voogasalad.gameEngine.exceptions.ItemNotUpgradableException;
import voogasalad.gameEngine.exceptions.NotEnoughMoneyException;
import voogasalad.gameEngine.systems.*;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Manages and updates the ComponentSystems of the engine.
 * That is, it is ultimately responsible for the entire primary behavior of the engine's entities.
 * Rather than handle this by itself, it distributes that load over a number of systems that each
 * define behavior over a specific set of component types.
 *
 * The SystemsManager is also responsible for the timed updates of each system.
 */

/**
 * @author Milan Shah
 * @author Lakshya Bakshi
 * @author Micheal Head
 * @author Alex Qiao
 * @author Nishant Iyengar
 */
public class SystemsManager {
	private List<ComponentSystem> systems;

	// All components types
	private Map<Integer, AI> ais = new TreeMap<>();
	private Map<Integer, Angle> angles = new TreeMap<>();
	private Map<Integer, Collision> boxes = new TreeMap<>();
	private Map<Integer, Damage> damagers = new TreeMap<>();
	private Map<Integer, Health> healths = new TreeMap<>();
	private Map<Integer, Image> images = new TreeMap<>();
	private Map<Integer, Location> locations = new TreeMap<>();
	private Map<Integer, Path> paths = new TreeMap<>();
	private Map<Integer, Resource> resources = new TreeMap<>();
	private Map<Integer, Spawner> spawners = new TreeMap<>();
	private Map<Integer, Speed> speeds = new TreeMap<>();
	private Map<Integer, Title> titles = new TreeMap<>();
	private Map<Integer, Wave> waves = new TreeMap<>();
	private Map<Integer, ActiveStatus> statuses = new TreeMap<>();
	private Map<Integer,Cost> costs = new TreeMap<>();
	private Map<Integer,Upgrade> upgrades = new TreeMap<>();
	private Map<Integer, SpawnerLimit> limits = new TreeMap<>();
	private Map<Integer, Interaction> interactions = new TreeMap<>();
	private Map<Integer, Affected> affected = new TreeMap<>();
	private Map<Integer, Type> type = new TreeMap<>();
	private Map<Integer, GameMap> maps = new TreeMap<>();
	private Map<Integer, Level> levels = new TreeMap<>();
	private Game game = new Game();

	private final EntityManager entityManager;

	private Double elapsedTime =0.0;

	private int tilewidth;
	private int tileheight;
	private Collision box = new Collision();

	private UpgradeSystem upgradeSystem;

	/**
	 * create a SystemsManager, using the given EntityManager to pass to its distributed systems.
	 * Initializes all systems with the component lists that they need.
	 *
	 * @param entityManager
	 */
	public SystemsManager(EntityManager entityManager) {
		elapsedTime = 0.0;
		this.entityManager = entityManager;
		tilewidth = 0;
		upgradeSystem = new UpgradeSystem(costs,resources,damagers,upgrades,game);

		systems = List.of(
				new SpawningSystem(this, locations, spawners, angles, statuses, limits, box),
				new CollisionSystem(locations, boxes, healths, damagers, interactions, speeds,type,affected),
				new ProgressionSystem(levels,waves,type,statuses, limits, game, maps, this, locations, paths,tilewidth),
				new HealthSystem(this, healths, resources,game),
				new MovementSystem(speeds, locations, angles),
				new PathingSystem(locations, boxes, paths, angles, type),
				new StatSystem(),
				new TargetingSystem(type, healths, locations, angles, ais)//,
		);

	}

	/**
	 * Initialize the systems needed to start up the game player.
	 */
	public void initializeGame(){
		for (ComponentSystem c : systems){
			if (c instanceof ProgressionSystem){
				((ProgressionSystem) c).initGame(game);
			}
		}
		for (int i : type.keySet()){
			if (type.get(i).getType().equalsIgnoreCase("path")){
				tilewidth = (int) boxes.get(i).getWidth();
				tileheight = (int)boxes.get(i).getHeight();
				box.setWidth((int) tilewidth);
				box.setHeight((int) tileheight);
			}
		}
	}

	/**
	 * resume play of the game player.
	 */
	public void playgame(){game.play();}

	/**
	 * try to buy and create a defense entity of the same type and parameters as the entity
	 * provided.
	 * @param id the ID of the defense entity to be bought
	 * @return whether the given defense entity can be created
	 */
	public boolean placedefense(int id){
		int currentcost = costs.get(id).getCost();
		if (resources.get(game.getResourceid()).canBuy(currentcost)){
			resources.get(game.getResourceid()).buy(currentcost);
			return true;
		}else{return false;}
	}

	/**
	 * attempt to upgrade the given entity
	 * @param entityID the ID of the entity to upgrade
	 * @throws ItemNotUpgradableException
	 * @throws NotEnoughMoneyException
	 */
	public void upgrade (Integer entityID) throws ItemNotUpgradableException, NotEnoughMoneyException {
		//upgradeSystem should also take in resource component to update the resources left
		upgradeSystem.upgrade(entityID);
	}

	/**
	 * update all systems with the given time step.
	 * @param dt
	 */
	public void update(double dt) {
		elapsedTime += dt;

		if (!game.status()){
			systems.forEach(c -> c.updateSystem(dt));
		} else{
			for (ComponentSystem c : systems){
				if (c instanceof UpgradeSystem || c instanceof StatSystem){
					c.updateSystem(dt);
				}
			}

		}
	}

	/**
	 * return the current elapsed time of the systems.
	 * @return the elapsed time
	 */
	public double getElapsedTime() {
		return elapsedTime;
	}

	/**
	 * register the components of the given entity with the systems manager, allowing them
	 * to be acted on by systems.
	 * @param entity
	 */
	public void addEntity(Entity entity) {
		entity.getComponents().forEach(c -> addComponent(c, entity.id()));
	}

	/**
	 * Spawns a new Entity based on the components of this template entity
	 * @param id : ID of the template Entity
	 * @return integer representation of ID of new Entity
	 */
	public Entity copyEntity(int id){
		Entity instance = entityManager.copyOf(id);
		return instance;
	}

	/**
	 * removes entity's components from systemsmanager,
	 * and remove reference to entity in entitymanager,
	 * essentially DELETING the entity.
	 * @param ID
	 */
	public void deleteEntity(int ID) {
		removeComponents(ID);
		entityManager.deleteEntity(ID);
	}

	/**
	 * removes references within the systemsmanager to the given entity's components.
	 * the entitymanager may still hold a reference to entity,
	 * and the entity may still have references to its components,
	 * so this is NOT guaranteed to delete those components.
	 * @param id
	 */
	public void removeComponents(int id) {
		entityManager.getEntity(id).getComponents().forEach(c -> deleteComponent(c, id));
	}

	public void addComponent(Component component, int id) {
		switch (component.getClass().getSimpleName()) {
			case ("AI"):
				ais.put(id, (AI) component);
				break;
			case ("Angle"):
				angles.put(id,     (Angle) component);
				break;
			case ("Affected"):
				affected.put(id, (Affected) component );
				break;
			case ("Collision"):
				boxes.put(id, (Collision) component);
				break;
			case ("Damage"):
				damagers.put(id,    (Damage) component);
				break;
			case ("Game"):
				game=(Game) component;
				break;
			case ("Health"):
				healths.put(id,    (Health) component);
				break;
			case ("Image"):
				images.put(id,     (Image) component);
				break;
			case ("Location"):
				locations.put(id,  (Location) component);
				break;
			case ("Path"):
				paths.put(id,      (Path) component);
				break;
			case ("Resource"):
				resources.put(id,  (Resource) component);
				break;
			case ("Spawner"):
				spawners.put(id,   (Spawner) component);
				break;
			case ("Speed"):
				speeds.put(id,     (Speed) component);
				break;
			case ("Title"):
				titles.put(id,     (Title) component);
				break;
			case ("Wave"):
				waves.put(id,      (Wave) component);
				break;
			case ("ActiveStatus"):
				statuses.put(id, (ActiveStatus) component);
				break;
			case ("Cost"):
				costs.put(id,		(Cost) component);
				break;
			case("Interaction"):
				System.out.print("Interaction YOO!!");
				interactions.put(id,	(Interaction) component);
				break;
			case("GameMap"):
				maps.put(id, 		(GameMap) component);
				break;
			case("Level"):
				levels.put(id, 		(Level) component);
				break;
			case("SpawnerLimit"):
				limits.put(id, 		(SpawnerLimit) component);
				break;
			case("Type"):
				type.put(id, 		(Type) component);
				break;
			case("Upgrade"):
				upgrades.put(id,	(Upgrade) component);
				break;
		}
	}

	/**
	 * remove the given component from the systems-manager.
	 * @param component the component to be deleted
	 * @param id the ID of the entity that owns the given component.
	 */
	private void deleteComponent(Component component, int id) {
		switch (component.getClass().getSimpleName()) {
			case ("AI"):
				ais.remove(id);
				break;
			case ("Affected"):
				affected.remove(id);
				break;
			case ("Angle"):
				angles.remove(id);
				break;
			case ("Collision"):
				boxes.remove(id);
				break;
			case ("Damage"):
				damagers.remove(id);
				break;
			case ("Game"):
				game=new Game();
				break;
			case ("Health"):
				healths.remove(id);
				break;
			case ("Image"):
				images.remove(id);
				break;
			case ("Location"):
				locations.remove(id);
				break;
			case ("Path"):
				paths.remove(id);
				break;
			case ("Resource"):
				resources.remove(id);
				break;
			case ("Spawner"):
				spawners.remove(id);
				break;
			case ("Speed"):
				speeds.remove(id);
				break;
			case ("Title"):
				titles.remove(id);
				break;
			case ("Wave"):
				waves.remove(id);
				break;
			case ("ActiveStatus"):
				statuses.remove(id);
				break;
			case ("Cost"):
				costs.remove(id);
				break;
			case("Interaction"):
				interactions.remove(id);
				break;
			case("GameMap"):
				maps.remove(id);
				break;
			case("Level"):
				levels.remove(id);
				break;
			case("SpawnerLimit"):
				limits.remove(id);
				break;
			case("Type"):
				type.remove(id);
				break;
			case("Upgrade"):
				upgrades.remove(id);
				break;
		}
	}
}
