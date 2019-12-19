package voogasalad.view.authoringEnvironment.controllers.waves;

import javafx.geometry.Point2D;
import javafx.scene.control.CheckBox;
import voogasalad.gameEngine.Engine;
import voogasalad.gameEngine.components.Wave;
import voogasalad.view.Controller;
import voogasalad.view.View;
import voogasalad.view.authoringEnvironment.data.WaveData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Angel Huizar
 * This class saves a wave to a spawn point on the grid
 */
public class SaveWaveToSpawnPoint extends Controller {

    private WaveData myWaveData;
    private Map<CheckBox, String> mySelectedWaves;
    private Point2D myPoint;
    private View myWindow;

    /**
     * This constructor tells this class what it will be working with
     * @param engine the engine this class will use
     * @param waveData the waveData the user created
     * @param selectedWaves a Map of checkboxes to the names of those checkboxes
     * @param point the point on the grid where the spawn point is (in pixels)
     * @param view the view to close when the action is completed
     */
    public SaveWaveToSpawnPoint(Engine engine, WaveData waveData, Map<CheckBox, String> selectedWaves, Point2D point, View view) {
        super(engine);
        myWaveData = waveData;
        mySelectedWaves = selectedWaves;
        myPoint = point;
        myWindow = view;
    }

    @Override
    public void execute(){
        for(CheckBox checkBox : mySelectedWaves.keySet()){
            if(checkBox.isSelected()){
                myWaveData.getIDNameMap().get(mySelectedWaves.get(checkBox));

                Map<String, String> edits = new HashMap<>();
                edits.put("x", String.valueOf(myPoint.getX()));
                edits.put("y", String.valueOf(myPoint.getY()));

                int spawnPointID = myWaveData.getSpawnPointMap().get(checkBox.getText());

                super.getEngine().edit(spawnPointID,edits);
            }
        }
        myWindow.closeScene();
    }
}
