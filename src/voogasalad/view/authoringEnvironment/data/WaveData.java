package voogasalad.view.authoringEnvironment.data;

import voogasalad.gameEngine.Engine;
import voogasalad.view.Data;
import voogasalad.view.authoringEnvironment.Editor;
import voogasalad.view.clickableobjects.Entry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaveData extends Data {
    Map<String, Integer> mySpawnPointIds = new HashMap();

    public WaveData(Editor view, Engine engine) {
        super(view, engine);
    }

    public void saveSpawnPoints(String frontend_name, String backend_name, Map<String, String> properties){
        int spawnpointID = super.getEngine().create(backend_name, properties);
        mySpawnPointIds.put(frontend_name,spawnpointID);
    }

    public Map<String, Integer> getSpawnPointMap(){
        return mySpawnPointIds;
    }

    public String getSpawnPointList(){
        List<Integer> ret = new ArrayList<>();
        mySpawnPointIds.forEach((string,integer)->{
            ret.add(integer);
        });
        return ret.toString().replace("[","").replace("]","").
                replace(" ","");
    }

    @Override
    public void updateUI(int new_id) {
        System.out.println("Update UI!!!!");
    }

    @Override
    public void load(int id) {

    }
}
