package voogasalad.view.authoringEnvironment.editors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import voogasalad.gameEngine.Engine;

import voogasalad.view.Data;
import voogasalad.view.View;
import voogasalad.view.authoringEnvironment.Editor;
import voogasalad.view.authoringEnvironment.controllers.CancelSaveController;
import voogasalad.view.authoringEnvironment.controllers.levels.AddLevel;
import voogasalad.view.authoringEnvironment.controllers.levels.DeleteWaveFromLevel;
import voogasalad.view.authoringEnvironment.data.LevelData;
import voogasalad.view.authoringEnvironment.views.grid.GridView;
import voogasalad.view.clickableobjects.ClickableButton;
import voogasalad.view.clickableobjects.ClickableEditorEntry;
import voogasalad.view.authoringEnvironment.controllers.levels.AddWaveToLevel;
import voogasalad.view.clickableobjects.LabeledInput;

import java.util.*;


/**
 * Creates an editor that allows the author to add new levels, and starting parameters to their game.
 * Will be called when an author clicks on a button in the main view of the game authoring environment.
 *
 * @author Gabriela Rodriguez-Florido, Samantha Whitt, Shreya Hurli, Angel Huizar
 */

public class LevelEditor extends Editor {

    private final ResourceBundle levelBundle = ResourceBundle.getBundle("voogasalad/view/authoringEnvironment/resources/Level");
    private ObservableList<String> myMaps, myWaves, myLevels;
    private LabeledInput levelName, towerHealth;
    private WaveEditor myWaveEditor;
    private Data myLevelData;
    private GridPane levelPane;
    private ClickableEditorEntry myRow;
    private GridView myGridView;
    private Node mySaveNCancel;
    private List<ChoiceBox> mySelectedWaves = new ArrayList<>();
    private List<LabeledInput> levelInputs = new ArrayList<>();

    public LevelEditor(GridView gridView, WaveEditor waveEditor, String title, Engine engine){
        super(title, engine);
        myWaves = waveEditor.getMyWaves();
        myLevels = FXCollections.observableArrayList();
        myLevelData = new LevelData(this, engine);
        myWaveEditor = waveEditor;
        myGridView = gridView;
        levelPane = super.createGridPane();
    }

    /**
     * Calls method to add all user interaction components to a gridpane, which is then added to a border pane
     * @return pane containing everything in this editor
     */
    @Override
    public Pane startVisualization() {
        return setupPane();
    }

    /**
     * This method sets the observable list that contains all the map names the user created
     * @param mapNames an observable list that contains the name of the maps created by the user
     */
    public void setMapData(ObservableList<String> mapNames) {
        myMaps = mapNames;
    }

    /**
     * Adds all of the components of the enemy editor into one gridpane
     * @return pane
     */
    @Override
    protected Pane setupPane() {
        // Creating row with labels and button to edit level
        ClickableEditorEntry row = myWaveEditor.createClickableEditorEntries();
        System.out.println("ROW: " + row.getEnemyCount(0) + "" + row.getEnemyFrequency(0));
        mySelectedWaves = row.getChoiceBoxes();

        levelPane.add(new Label(levelBundle.getString("specs_title")),
                Integer.parseInt(super.SPACING.getString("titlex")),
                Integer.parseInt(super.SPACING.getString("titley")));
        levelPane.add(new Label(levelBundle.getString("wave_title")),
                Integer.parseInt(super.SPACING.getString("imageSelectorx")),
                Integer.parseInt(super.SPACING.getString("imageSelectory")));
        levelPane.add(createScrollPane(row,200), 0, 2);
        levelPane.add(setupInputs(), Integer.parseInt(super.SPACING.getString("labeledinputsx")),
                Integer.parseInt(super.SPACING.getString("labeledinputsy")));

        // Creating button to add and delete new level to scrollpane
        Node addNdelete = super.createSaveNCancel(2, super.ENGLISH.getString("AddWave"),
                new AddWaveToLevel(super.ENGLISH.getString("Wave"),
                        row, super.getEngine()), super.ENGLISH.getString("toolAdd"),
                super.ENGLISH.getString("DeleteWave"), new DeleteWaveFromLevel(row,super.getEngine()),
                super.ENGLISH.getString("toolDelete"));
        levelPane.add(addNdelete,0,3);

        levelPane.add(new Label(levelBundle.getString("SelectMap")), 5, 1);

        // Creating drop down options for
        ChoiceBox<String> mapChoices = new ChoiceBox<>(myMaps);
        levelPane.add(mapChoices,6,1);

        mySaveNCancel = super.createSaveNCancel(15, super.ENGLISH.getString("save"),
                new AddLevel(myLevelData, super.getEngine(), this, levelName,
                        this, row.getChoiceBoxes(), myLevels, towerHealth, mapChoices,myGridView),
                super.ENGLISH.getString("toolSave"),
                super.ENGLISH.getString("cancel"), new CancelSaveController(this, super.getEngine()),
                super.ENGLISH.getString("toolCancel"));
        mySaveNCancel.setId("oldbutton");
        levelPane.add(mySaveNCancel, 2, 3);

        levelPane.add(super.instructionText("LevelInstructions"), 8, 2);


        return levelPane;
    }

    /**
     * Sets up all of the user inputs required to create a level
     * @return
     */
    @Override
    protected Pane setupInputs() {
        VBox inputs = super.createVBox();

        levelName = new LabeledInput(levelBundle.getString("LevelName"));
        inputs.getChildren().add(levelName.getNode());

        towerHealth = new LabeledInput(super.ENGLISH.getString("towerPrompt"));
        inputs.getChildren().add(towerHealth.getNode());

        levelInputs.add(levelName);
        levelInputs.add(towerHealth);

        return inputs;
    }

    /**
     * Allows a controller to clear the inputs, for example when saving it is nice to have all the inputs cleared after
     * you're done, or you may want to cancel your changes.
     */
    @Override
    public void clearInputs() {
        super.clearLabeledTexts(levelInputs);
    }

    /**
     * Allows a controller to update the contents of the inputs, which is useful for editing a level's parameters
     * @param inputsAlphabetically
     */
    @Override
    public void updateInputs(Collection<String> inputsAlphabetically) {

    }

    /**
     * Allows a controller to get the level pane in order to add things like error messages
     * @return enemyPane
     */
    @Override
    public GridPane getPane() {
        return levelPane;
    }

    /**
     * Allows a controller to get all the text inputs in the level editor so that the contents can be extracted and
     * sent to the backend
     * @return
     */
    @Override
    public List<LabeledInput> getLabeledInputs() {
        return levelInputs;
    }

    /**
     * Allows a controller to get the editor, which this current editor depends on having created something to create
     * this object. For an enemy, there is no dependent editor, however.
     * @return
     */
    @Override
    public Editor getSubEditor() {
        return myWaveEditor;
    }

    /**
     * Allows a controller to get all the choice boxes in an editor so that it can get the selected value.
     * @return
     */
    @Override
    public List<ChoiceBox> getChoiceBoxes() {
        return mySelectedWaves;
    }

    /**
     * Allows a controller to get the id for a given frontend name so that when a user edits a level, it edits the correct
     * backend level.
     * @return
     */
    @Override
    public Map<String, Integer> getIDMap() {
        return myLevelData.getIDNameMap();
    }

    /**
     * Allows editors to return their save and cancel buttons so that their functionality can be changed to edit rather
     * than create
     * @return save and cancel buttons
     */
    @Override
    public Node getSaveNCancel() {
        return mySaveNCancel;
    }

    /**
     * Allows the parent view (game) to create a list of entries of created levels to associate to the game in a certain
     * progression
     * @return entry
     */
    public ClickableEditorEntry createClickableEditorEntries() {
        myRow = new ClickableEditorEntry(super.ENGLISH.getString("level"), myLevels);
        return myRow;
    }
}
