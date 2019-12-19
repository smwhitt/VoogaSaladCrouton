package voogasalad.gameEngine;

import voogasalad.gameEngine.components.*;
import voogasalad.gameEngine.exceptions.InvalidEntityParametersException;
import voogasalad.gameEngine.exceptions.MissingGameComponentsException;
import voogasalad.gameEngine.exceptions.PropertyExceptions;
import voogasalad.gameEngine.exceptions.UnableToBuildException;
import voogasalad.view.exceptions.ReflectionException;

import java.lang.reflect.InvocationTargetException;
import java.util.*;


/**
 * Populates entities with parameters, by translating those parameters into any number of discrete
 * components to be added to the entity.
 *
 * This class is primarily used as a mediator in communicating the data in entities externally:
 * externally, entities are returned as simple String->String mappings. EntityFactory translates
 * those mappings into components that the engine can assign to an entity and use.
 *
 * To be clear, EntityFactory is NOT for *creating* entities. EntityManager should be used to
 * create entities, because it will manage and enforce the unique ID mapping and
 * maintains the entire collection of entities in the engine.
 *
 * @authors Lakshya Bakshi, Micheal Head, Milan Shah
 */
public class EntityFactory {
	private static List<String> COMPONENT_LIST=Collections.list(ResourceBundle.getBundle("voogasalad/properties/ComponentParameters").getKeys());
	private static final String COMPONENT_PACKAGE_PATH="voogasalad.gameEngine.components.";

	/**
	 * given a map of parameters, the method looks through the given items and attempts to create
	 * any components it can create whose fields match keys in the map.
	 * @param params a map of parameters to create components from. Not mutated.
	 * @return an array of components created from the map. NOT guaranteed to exhaust the map.
	 */
	public Component[] makeComponents(Map<String,String> params) {
		List<Component> components = new ArrayList<>();
		COMPONENT_LIST.removeAll(List.of("Type","ActiveStatus"));
		COMPONENT_LIST.stream().filter( c -> {
			try {
				return containsParams((Class<? extends Component>) Class.forName(COMPONENT_PACKAGE_PATH+c), params);
			} catch (ClassNotFoundException e) {
				//e.printStackTrace();
				throw new MissingGameComponentsException(" ");
				//return false;
			}
		}).forEach(c-> {
					try {
						components.add((Component) Class.forName(COMPONENT_PACKAGE_PATH+c).getDeclaredConstructor(Map.class).newInstance(params));
					} catch (Exception e) {
						throw new UnableToBuildException(" ");
					}
				}
		);
		components.add(new Type(params.get("type")));
		components.add(new ActiveStatus("false"));

		return components.toArray(new Component[0]);
	}

	/**
	 * given an entity and a map of parameters, attempts to create as many possible components
	 * out of the map as possible whose fields match keys in the map. This also adds all components
	 * made to the entity.
	 *
	 * The method is essentially a handy extension of `makeComponents` that removes the need to
	 * manually add the created components to an entity.
	 *
	 * This also keeps the components themselves encapsulated within this method, and helps prevent
	 * rogue unowned components.
	 *
	 * @param entity an entity (preferably empty!) to add the components to.
	 * @param params the map of parameters to construct components out of
	 * @return A reference to the same entity passed in the parameters, but populated.
	 */
	public Entity populate(Entity entity, Map<String,String> params) {
		Component[] components = makeComponents(params);
		Arrays.stream(components)
				.forEach(c -> entity.addComponent(c));

		return entity;
	}

	/**
	 * Given an entity and map of parameters, the method attempts to create as many possible
	 * components out of the map as possible whose fields match keys in the map. This also adds all
	 * components made to the entity.
	 *
	 * The given parameters are checked against the parameters specified by the `type` (the names
	 * of which can be found in an associated .properties file at voogasalad/properties/). The given
	 * parameters must contain all parameters required by the type, or the population won't go
	 * through.
	 * @param entity The entity to add components to.
	 * @param params The parameter mappings to initialize the entity with.
	 * @param type the `type` of the object, that determines what parameters are required.
	 * @return the same entity just edited.
	 */
	public Entity populate(Entity entity, Map<String,String> params, String type){
		System.out.println("Type is:  " + type);
		if (checkValidParams(params,type)){
			Map<String,String> newParams = new HashMap<>(params);
			newParams.put("type", type);
			newParams.put("activestatus","false");
			return populate(entity, newParams);
		}else{
			throw new InvalidEntityParametersException(entity.id(),type);
		}
	}

	private boolean checkValidParams(Map<String,String> params, String type){
		Set<String> validComponents = ResourceBundle.getBundle(String.format("voogasalad/properties/%s", type)).keySet();
		boolean ret = true;
		for(String s : params.keySet()){
			if(validComponents.contains(s) || s.equals("x") || s.equals("y") || s.equals("angle")){
				ret = true;
			}else{
				ret = false;
			}
		}
		return ret;
	}

	/**
	 * retrieves the field names of a Component
	 */
	private List<String> paramNames(Class<? extends Component> cls) {
		try {
			//System.out.println("attempting to access class "+cls);
			Component comp = (Component) (cls.getConstructor().newInstance());
			return Arrays.asList(comp.fields());
		} catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
			throw new ReflectionException(PropertyExceptions.ERROR_RESOURCES.getString("Reflection"), "could not find names of fields of the component " + cls);
		}
	}

	/**
	 * checks if the parameter map's keys contains the given class's field names.
	 */
	private boolean containsParams(Class<? extends Component> cls, Map<String,String> params) {
		return params.keySet().containsAll(paramNames(cls));
	}
}
