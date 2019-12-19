package voogasalad.gameEngine.components;

import voogasalad.gameEngine.exceptions.IncorrectVariableTypeException;

import java.util.*;

/**
 * Component that holds pointers to the current wave's spawnpoints
 * @author Lakshya Bakshi
 */
public class Wave extends Component {
    private static final String className = "Wave";

    private List<Integer> spawnpoints;

    public Wave(List<Integer> spawnpoints) {
        super(className);
        this.spawnpoints = spawnpoints;
    }

    public Wave(){
        this(List.of(-1));
    }

    public Wave(Map<String, String> params) {
        super(className);
        parseParameters(params);
    }

    @Override
    public void parseParameters(Map<String, String> params) {
        try {
        spawnpoints=new ArrayList<>();
        Arrays.asList(params.get("spawnpoints").split(",")).forEach(c->spawnpoints.add(Integer.parseInt(c)));
        } catch (Exception e) {
            throw new IncorrectVariableTypeException(className);
        }
    }

    /**
     * returns the spawnpoint ids for this list
     * @return spawnpoints as an unmodifiable list
     */
    public List<Integer> getSpawns(){return Collections.unmodifiableList(spawnpoints);
    }

    @Override
    public Map<String, String> asMap() {
        return Map.of(
                "spawnpoints",returnListParameters(spawnpoints)
        );
    }

    @Override
    public Component copy() {
        return new Wave(spawnpoints);
    }
}
