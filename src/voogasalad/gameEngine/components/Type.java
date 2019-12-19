package voogasalad.gameEngine.components;

import voogasalad.gameEngine.exceptions.IncorrectVariableTypeException;

import java.util.Map;

/**
 * Component to hold the type of this entity, like defense, projectile, etc
 * @author Lakshya Bakshi
 */
public class Type extends Component {
    private static final String className = "Type";
    private String type;

    public Type(String entitytype) {
        super(className);
        type = entitytype;
    }

    public Type() {
        super("None");
    }

    /**
     * return the type of this component's parent entity
     * @return type
     */
    public String getType(){return type;}

    public Type(Map<String, String> params) {
        super(className);
        parseParameters(params);
    }

    public void parseParameters(Map<String, String> params) {
        try { this.type=params.get("type");
        } catch (Exception e) {
            throw new IncorrectVariableTypeException(className);
        }
    }

    @Override
    public Map<String, String> asMap() {
        return Map.of(
                "type", type+""
        );
    }

    @Override
    public Component copy() {
        return new Type(type);
    }
}
