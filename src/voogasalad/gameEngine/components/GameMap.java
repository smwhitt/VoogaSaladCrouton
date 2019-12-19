package voogasalad.gameEngine.components;

import voogasalad.gameEngine.exceptions.IncorrectVariableTypeException;

import java.util.*;

/**
 * Component that holds pointers to all relevant path entities for the current map
 * @author Lakshya Bakshi
 */
public class GameMap extends Component {
    private static final String className = "GameMap";

    private List<Integer> pathIDs;

    /**
     * Map component that contains IDs of all path IDs.
     * @param paths : A List of path entity IDs
     */
    @Deprecated
    public GameMap(List<Integer> paths) {
        super(className);
        pathIDs = paths;
    }

    public GameMap() {
        this(List.of());
    }

    public GameMap(Map<String, String> params) {
        super(className);
        parseParameters(params);
    }

    /**
     * returns the sequence of entity IDs for the paths relevant to this map
     * @return
     */
    public List<Integer> getPathIDs(){return Collections.unmodifiableList(pathIDs);
    }

    @Override
    public void parseParameters(Map<String, String> params) {
        try {
        pathIDs=new ArrayList<>();
        Arrays.asList(params.get("paths").split(",")).forEach(c->pathIDs.add(Integer.parseInt(c)));
        } catch (Exception e) {
            throw new IncorrectVariableTypeException(className);
        }
    }

    @Override
    public java.util.Map<String, String> asMap() {
        return Map.of("paths", returnListParameters(pathIDs));
    }

    @Override
    public Component copy() {
        return new GameMap(pathIDs);
    }
}
