package voogasalad.gameEngine.components;

import voogasalad.gameEngine.exceptions.IncorrectVariableTypeException;

import java.util.List;
import java.util.Map;

/**
 * component to hold spawning limits for specific entities that may not infinitely spawn, like spawn points
 * @authors Milan Shah, Lakshya Bakshi
 */
public class SpawnerLimit extends Component {
    private static final String className = "SpawnerLimit";
    private int numberspawn;

    public SpawnerLimit(String numbertospawn) {
        super(className);
        numberspawn=Integer.parseInt(numbertospawn);
    }

    public SpawnerLimit(Map<String, String> params) {
        super(className);
        parseParameters(params);
    }

    @Override
    public void parseParameters(Map<String, String> params) {
        try { numberspawn=Integer.parseInt(params.get("spawnerlimit"));
        } catch (Exception e) {
            throw new IncorrectVariableTypeException(className);
        }
    }

    public SpawnerLimit(){
        this("0");
    }

    /**
     * get the spawner limit
     * @return number to be spawned
     */
    public int getNumberToSpawn(){return numberspawn;}

    /**
     * reduces the spawner limit to increment towards 0 for each successful spawn
     */
    public void reduceSpawnable(){
        numberspawn--;
    }

    @Override
    public Map<String, String> asMap() {
        return Map.of("spawnerlimit", numberspawn+"");
    }

    @Override
    public Component copy() {
        return new SpawnerLimit(numberspawn+"");
    }
}
