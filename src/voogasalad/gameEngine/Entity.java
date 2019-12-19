package voogasalad.gameEngine;

import voogasalad.gameEngine.components.Component;
import voogasalad.gameEngine.components.Health;

import java.util.*;

/**
 * The primary representation for all game and engine objects in the engine.
 *
 * An Entity is a bucket of Components with a unique ID, and contains no real behavior or control
 * over its state.
 *
 * ComponentSystems and Components contain/handle the behavior and state, respectively.
 */
public class Entity {

    private int myEntityID;
    private List<Component> myComponents;

    /**
     * create an entity object with a unique ID
     *
     * This should be called when direct control is absolutely necessary. EntityFactory and
     * EntityManager are suited for managing all Entities.
     * @param id unique ID assigned during construction
     */
    public Entity(int id){
        myEntityID = id;
        myComponents = new ArrayList<>();
    }

    /**
     * Add a Component to the entity
     * @param newcomponent
     */
    public void addComponent(Component newcomponent){
        newcomponent.setOwner(this);
        myComponents.add(newcomponent);
    }

    /**
     * retrieve the ID of the entity.
     * @return entity ID
     */
    public int id(){
        return myEntityID;
    }

    /**
     * retrieve the entity's components. Well, a copy of them.
     * @return copied list of components
     */
    List<Component> getComponents(){ return List.copyOf(myComponents); }

    /**
     * remove all components from the entity, and return them.
     * @return List of the deleted components
     */
    List<Component> removeComponents() {
        List<Component> componentsCopy = List.copyOf(myComponents);
        myComponents = new ArrayList<Component>();
        return componentsCopy;
    }

    /**
     * retrieve a map view of the entity, that is, a mapping of all component fields to their
     * values in a single map.
     * @return map of string component to string component value
     */
    public Map<String,String> asMap() {
        Map<String,String> map = new HashMap<>();
        myComponents.forEach(c -> map.putAll(c.asMap()));
        return map;
    }

    public String toString() {
        return String.format("Entity{ID=%s, components=%s}",myEntityID,myComponents);
    }
}
