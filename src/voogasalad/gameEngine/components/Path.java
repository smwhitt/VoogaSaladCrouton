package voogasalad.gameEngine.components;

import voogasalad.gameEngine.exceptions.IncorrectVariableTypeException;

import java.util.Map;

/**
 * Component to hold path type data, including Inaccessible, Accessible, Start, or End
 * @author Lakshya Bakshi
 */
public class Path extends Component {
    private static final String className = "Path";

    private String pathType;
    public Path(String pathType) {
        super(className);
        this.pathType = pathType;
    }

    public Path() { this("Inaccessible"); }

    public Path(Map<String, String> params) {
        super(className);
        parseParameters(params);
    }

    public void parseParameters(Map<String, String> params) {
        try {
            this.pathType=(params.get("path"));
        } catch (Exception e) {
            throw new IncorrectVariableTypeException(className);
        }
    }

    /**
     * Modifies the path type
     * @param pathType new path type
     */
    public void modifyPath(String pathType) {
        this.pathType = pathType;
    }

    /**
     * returns this path's type
     * @return pathtype
     */
    public String getPathType(){
        return pathType;
    }

    @Override
    public Map<String, String> asMap() {
        return Map.of(
                "path", pathType+""
        );
    }

    @Override
    public Component copy() {
        return new Path(pathType);
    }


}
