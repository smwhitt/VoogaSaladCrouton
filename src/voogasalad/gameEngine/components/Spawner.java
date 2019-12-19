package voogasalad.gameEngine.components;

import voogasalad.gameEngine.exceptions.IncorrectVariableTypeException;

import java.util.List;
import java.util.Map;

/**
 * component for pointing to entities needing creation, such as spawnpoints making minions or defenses making darts
 * @authors Milan Shah, Lakshya Bakshi
 */
public class Spawner extends Component{
    private static final String className = "Spawner";
    private static final List<String> fields = List.of("spawnentity", "frequency");
    private float frequency;
    private int spawnentity;

    public Spawner(String freq, String entityIDtocreate){
        super(className);
        frequency = Float.parseFloat(freq);
        spawnentity = Integer.parseInt(entityIDtocreate);
    }

    public Spawner(){
        this("0","0");
    }

    public Spawner(Map<String, String> params) {
        super(className);
        parseParameters(params);
    }

    @Override
    public void parseParameters(Map<String, String> params) {
        try { frequency=Float.parseFloat(params.get("frequency"));
        spawnentity=Integer.parseInt(params.get("spawnentity"));
        } catch (Exception e) {
            throw new IncorrectVariableTypeException(className);
        }
    }

    /**
     * returns the frequency at which this spawner spawns
     * @return frequency
     */
    public float getFrequency(){return frequency;}

    /**
     * returns the id of the entity that gets spawned by this component
     * @return entity id to be spawned
     */
    public int getEntityToSpawn(){return spawnentity;}

    @Override
    public Map<String, String> asMap() {
        return Map.of(
                "frequency", frequency+"",
                "spawnentity",  spawnentity+""
        );
    }

    @Override
    public Component copy() {
        return new Spawner(frequency+"",spawnentity+"");
    }
}
