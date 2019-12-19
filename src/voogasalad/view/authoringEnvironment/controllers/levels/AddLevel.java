package voogasalad.view.authoringEnvironment.controllers.levels;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;
import voogasalad.view.Data;
import voogasalad.view.authoringEnvironment.Editor;
import voogasalad.view.authoringEnvironment.editors.LevelEditor;
import voogasalad.view.authoringEnvironment.editors.WaveEditor;
import voogasalad.view.authoringEnvironment.views.grid.GridView;
import voogasalad.view.clickableobjects.LabeledInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class creates a new level in the backend when the user hits save from level editor
 * @author Gabriela Rodriguez-Florido, Shreya Hurley
 */
public class AddLevel extends Controller {

    private final String PATH = "voogasalad/properties/";
    private static final String OBJECT_NAME = "Levels";

    private Data myLevelData;
    private Editor myEditor;
    private LabeledInput myLevelName;
    private LabeledInput myLevelDescription, myTowerHealth;
    private List<ChoiceBox> myChoiceBoxes;
    private LevelEditor myLevelEditor;
    private ObservableList myLevels;
    private ChoiceBox mySelectedMap;
    private GridView myGridView;

    public AddLevel(Data data, Engine engine, Editor editor, LabeledInput levelName, LevelEditor levelEditor,
                    List<ChoiceBox> choiceBoxes, ObservableList levels, LabeledInput towerHealth, ChoiceBox selectedMap,
                    GridView gridView){
        super(engine);
        myLevelData = data;
        myEditor = editor;
        myLevelName = levelName;
        myLevelEditor = levelEditor;
        myChoiceBoxes = choiceBoxes;
        myLevels = levels;
        myTowerHealth = towerHealth;
        mySelectedMap = selectedMap;
        myGridView = gridView;
    }

    @Override
    public void execute(){
        ResourceBundle levels = ResourceBundle.getBundle(PATH+"Levels");
        ResourceBundle tower = ResourceBundle.getBundle(PATH+"Tower");

        int towerID = super.getEngine().create("Tower" , super.createMap(tower.getKeys(),
                Arrays.asList(myTowerHealth.getText(), Integer.toString(-1),Integer.toString(-1),Integer.toString(-1))));

        if(myLevelData.save(myLevelName.getText(), OBJECT_NAME, super.createMap(levels.getKeys(), List.of(
                Integer.toString(myGridView.getMyData().getIDForName(mySelectedMap.getValue())), // map id
                myLevelName.getText(), // name of level
                Integer.toString(towerID), // towerid
                choiceBoxValueExtraction(myLevelEditor).toString().replace("[","").replace("]","").
                    replace(" ","") // waves for level
        )))){
            myLevels.add(myLevelName.getText());
        }

        myEditor.clearInputs();

    }

}
