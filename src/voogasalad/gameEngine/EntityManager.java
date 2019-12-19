package voogasalad.gameEngine;

import voogasalad.gameEngine.exceptions.EntityNotFoundException;

import java.util.*;
import java.util.function.Consumer;

/**
 * Maintains and manages the entities created within the engine. Enforces the unique-ID mapping
 * for all entities (that exist within the manager!), and provides some simple methods for
 * manipulating and retrieving information about entities.
 *
 * EntityManager should be used to create new entities, rather than relying on the Entity
 * constructor.
 *
 * EntityManager implements Iterable, in that the entities it contains can be iterated over cleanly.
 */
public class EntityManager implements Iterable<Entity> {
	private int currID;
	private Map<Integer, Entity> entities;

	public EntityManager() {
		currID = 0;
		entities = new HashMap<Integer, Entity>();
	}

	/**
	 * create a new EntityManager, given a map of entities already initialized.
	 * This would be needed in loading previous data, where the EntityManager is immediately
	 * populated with entities. IDs of entities created after this point will still be unique.
	 * @param entities A mapping of entities to initialize the EntityManager with.
	 */
	public EntityManager (Map<Integer, Entity> entities) {
		this.entities = entities;
		currID = entities.keySet().stream()
				.mapToInt(v -> v)
				.max().orElse(-1)
				 + 1;
	}

	/**
	 * create an empty entity with an assigned ID (without components!). A reference is kept to the
	 * entity, and can be retrieved later, given its ID.
	 *
	 * @return entity created
	 */
	public Entity newEntity() {
		Entity e = new Entity(currID);

		currID += 1;
		entities.put(e.id(), e);
		return e;
	}

	/**
	 * retrieve all the IDs of entities in existence in EntityManager.
	 * @return a set of IDs
	 */
	public Set<Integer> getIDs() {
		return entities.keySet();
	}

	/**
	 * retrieve the entity with the given ID.
	 * @param id the ID whose associated Entity will be returned
	 * @return the found entity, or null if not found
	 */
	public Entity getEntity(int id) {
		return entities.get(id);
	}

	/**
	 * return a mapping of {int ID} -> {entity} containing all entities in EntityManager.
	 * @return a map containing all entities
	 */
	public Map<Integer, Entity> getEntities() { return entities; }

	/**
	 * remove the given entity from the EntityManager. This obviously doesn't guarantee that the
	 * entity is completely wiped from memory.
	 *
	 * @param ID ID of the entity to be removed
	 */
	public void deleteEntity(int ID) {
		entities.remove(ID);
	}

	@Override
	public Iterator<Entity> iterator() {
		return entities.values().iterator();
	}

	@Override
	public void forEach(Consumer<? super Entity> action) {
		for (Entity e : entities.values()) {
			action.accept(e);
		}
	}

	/**
	 * create deep-copy of the given entity. This entity is assigned its own unique ID.
	 * @param entity entity to be copied
	 * @return the entity copy
	 */
	public Entity copyOf(Entity entity) {
		if (entity==null) throw new EntityNotFoundException();

		Entity e = newEntity();

		entity.getComponents().forEach(c -> e.addComponent(c.copy()));

		return e;
	}

	/**
	 * create deep-copy of the given entity. This entity is assigned its own unique ID.
	 * @param id the id of the entity to be copied
	 * @return the entity copy
	 */
	public Entity copyOf(int id) {
		return copyOf(entities.get(id));
	}
}
