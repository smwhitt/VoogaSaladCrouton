package voogasalad.gameEngine.components;

import voogasalad.gameEngine.Entity;

import java.util.*;

/**
 * Component class: Abstract class allowing the extension of various components for entities
 *
 * @author Milan Shah, Lakshya Bakshi, Micheal Head, Alex Qiao
 */
public abstract class Component {
    private static final String PROPERTIES_PATH="voogasalad/properties/ComponentParameters";
    private int owner;
    private final List<String> fields;

    protected Component(String filename) {
        this.fields = Arrays.asList(ResourceBundle.getBundle(PROPERTIES_PATH).getString(filename).split(","));
    }

    public void setOwner(Entity entity) {
        owner = entity.id();
    }

    public int owner() {
        return owner;
    }
    /**
     * returns this component as a map of its relevant values
     * @return map of parameters
     */
	public abstract Map<String, String> asMap();

    /**
     * returns a duplicate copy of this component with matching parameters
     * @return a new identical component
     */
    public abstract Component copy();

    /**
     * returns the expected parameter keys of this component
     * @return array of expected parameters
     */
    public String[] fields() {
        return List.copyOf(fields).toArray(new String[0]);
    }
    /**
     * parses the relevant parameters for this component
     * @param params map of all parameters for the parent entity
     * @throws InvalidPropertiesFormatException
     */
    public abstract void parseParameters(Map<String, String> params) throws InvalidPropertiesFormatException;

    @Override
    public String toString() {
        return "Component{" +
                "owner=" + owner +
                ", " + this.asMap() +
                '}';
    }

    String returnListParameters(List<Integer> listInput) {
        StringBuilder out= new StringBuilder("");
        listInput.forEach(c->out.append(c).append(","));
        return out.substring(0, out.length()-1);
    }
}
