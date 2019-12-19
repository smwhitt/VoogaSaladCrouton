package voogasalad.view.authoringEnvironment.controllers.waves;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;
import voogasalad.view.Data;
import voogasalad.view.authoringEnvironment.Editor;
import voogasalad.view.authoringEnvironment.data.EnemyData;
import voogasalad.view.authoringEnvironment.data.WaveData;
import voogasalad.view.clickableobjects.ClickableEditorEntry;
import voogasalad.view.clickableobjects.LabeledInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class defines the behavior that occurs when a user presses save and adds a wave to the game
 * @author Gabriela Rodriguez-Florido, Angel Huizar
 */
public class AddWave extends Controller {

    private static final String OBJECT_NAME = "Wave";
    private static final String SPAWNPOINT = "SpawnPoint";
    private static final String RESOURCES = "voogasalad/properties/";

    private ResourceBundle myWaveParams;
    private ResourceBundle mySpawnPointParams;
    private LabeledInput myWaveName;
    private LabeledInput myEnemyCount;
    private LabeledInput myEnemySpawnFrequency;
    private ChoiceBox<String> myEnemy;
    private List<Integer> mySpawnPoints;
    private ObservableList<String> myWaveNames;
    private EnemyData myEnemyData;
    private Data myWaveData;
    private Editor myWaveEditor;
    private ObservableList myWaves;
    private ClickableEditorEntry myEntry;

    public AddWave(Data waveData, Editor editor, LabeledInput waveName, ClickableEditorEntry entry,
                   ChoiceBox<String> enemyName, ObservableList<String> waveNames, Engine engine){
        super(engine);
        myWaveParams = ResourceBundle.getBundle(RESOURCES+OBJECT_NAME); // only param is a list of spawnpoints
        mySpawnPointParams = ResourceBundle.getBundle(RESOURCES+SPAWNPOINT);
        myWaveData = waveData;
        myWaveEditor = editor;

        // Backend spawnpoint params
        myWaveName = waveName;
        myEntry = entry;
        myEnemy = enemyName;
        myWaveNames = waveNames; // list of all created waves to be shown in drop down for level

        // list of spawnpoints
        mySpawnPoints = new ArrayList<>();
    }

    @Override
    public void execute() {
        System.out.println("New level name: "+ myWaveName.getText());

        // Creating backend SpawnPoint object (not passing all params bc spawnpoint location will be set when the spawnpoints
        // are loaded up in player
        System.out.println("Spawn point params: " + mySpawnPointParams.keySet());
        System.out.println("Size: " + myEntry.getEntries().size());
        System.out.println("Selected IDs from choicebox" + super.getSelectedIDsFromChoiceBox(myWaveEditor));
        for(int i=0; i<myEntry.getEntries().size(); i++){
            System.out.println("Sending enemy frequency: " + myEntry.getEnemyFrequency(i).getText());
            System.out.println("Sending enemy count: " + myEntry.getEnemyCount(i).getText());
            System.out.println("Sending the following enemy id: " + myWaveEditor.getSubEditor());
            System.out.println("Sending the following enemy id: " + myWaveEditor.getSubEditor().getIDMap());
            System.out.println("Sending the following enemy id: " + myWaveEditor.getSubEditor().getIDMap().get(myEntry.getChoiceBoxes().get(i).getValue()));

            ((WaveData)myWaveData).saveSpawnPoints(myWaveName.getText(), SPAWNPOINT, super.createMap(mySpawnPointParams.getKeys(),
                    Arrays.asList(myEntry.getEnemyFrequency(i).getText(), Integer.toString(myWaveEditor.getSubEditor().getIDMap().get(myEntry.getChoiceBoxes().get(i).getValue())),
                            myEntry.getEnemyCount(i).getText(), Integer.toString(-1), Integer.toString(-1))));
            }

        if(myWaveData.save(myWaveName.getText(),OBJECT_NAME,super.createMap(myWaveParams.getKeys(),
                Arrays.asList(((WaveData)myWaveData).getSpawnPointList())))){
            System.out.println("Saving a wave???");
            myWaveNames.add(myWaveName.getText());
            myWaveEditor.clearInputs();
        }

        System.out.println("Saving wave data... ");
        //super.getEngine().save("TestSpawnPoints");
    }

}
