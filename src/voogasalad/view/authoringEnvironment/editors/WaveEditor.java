package voogasalad.view.authoringEnvironment.editors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Data;
import voogasalad.view.authoringEnvironment.Editor;
import voogasalad.view.authoringEnvironment.EditorSelector;
import voogasalad.view.authoringEnvironment.controllers.CancelSaveController;
import voogasalad.view.authoringEnvironment.controllers.enemy.AddEnemyToWave;
import voogasalad.view.authoringEnvironment.controllers.levels.AddWaveToLevel;
import voogasalad.view.authoringEnvironment.controllers.levels.DeleteWaveFromLevel;
import voogasalad.view.authoringEnvironment.controllers.projectile.AddProjectile;
import voogasalad.view.authoringEnvironment.controllers.waves.AddWave;
import voogasalad.view.authoringEnvironment.data.ProjectileData;
import voogasalad.view.authoringEnvironment.data.WaveData;
import voogasalad.view.clickableobjects.ClickableEditorEntry;
import voogasalad.view.clickableobjects.Entry;
import voogasalad.view.clickableobjects.LabeledInput;
import voogasalad.view.authoringEnvironment.controllers.enemy.EditEnemy;

import java.util.*;

/**
 * Creates an editor class that allows the author to change the level names, descriptions, and other specs for their project.
 * Instantiated in OpenLevelEditor.
 *
 * @author Gabriela Rodriguez-Florido, Samantha Whitt
 */

public class WaveEditor extends Editor {

    private final ResourceBundle waveBundle = ResourceBundle.getBundle("voogasalad/view/authoringEnvironment/resources/Wave");

    private LabeledInput myWaveName;
    private ObservableList<String> myWaves;
    private Data myWaveData;
    private Node mySaveNCancel;
    private EnemyEditor myEnemyEditor; // needed to associate the selected enemy to the wave
    private ClickableEditorEntry myRow;
    private GridPane wavePane;
    private List<LabeledInput> myWaveInputs = new ArrayList<>();

    public WaveEditor(EnemyEditor enemyEditor, String title, Engine engine){
        super(title, engine);
        myWaves = FXCollections.observableArrayList();
        myWaveData = new WaveData(this, engine);
        myEnemyEditor = enemyEditor;
        wavePane = super.createGridPane();
    }

    @Override
    public Pane startVisualization() {
        return setupPane();
    }


    @Override
    protected Pane setupPane() {

        wavePane.add(new Label(waveBundle.getString("SelectWaveEnemies")),
                Integer.parseInt(super.SPACING.getString("imageSelectorx")),
                Integer.parseInt(super.SPACING.getString("imageSelectory")));

        ClickableEditorEntry clickableEditorEntry = new ClickableEditorEntry("Enemy",
                waveBundle.getString("enemy_number"), waveBundle.getString("frequency"),
                myEnemyEditor.getCreatedEnemyList());

        wavePane.add(createScrollPane(clickableEditorEntry,550), 0, 2);

        // Creating button to add and delete new level to scrollpane
        Node addNdelete = super.createSaveNCancel(2, super.ENGLISH.getString("AddEnemy"),
                new AddEnemyToWave(super.ENGLISH.getString("Enemy"),
                        waveBundle.getString("enemy_number"),
                        waveBundle.getString("frequency"),
                        clickableEditorEntry,
                        super.getEngine()),
                super.ENGLISH.getString("toolAdd"),
                super.ENGLISH.getString("DeleteEnemy"),
                new DeleteWaveFromLevel(clickableEditorEntry,super.getEngine()),
                super.ENGLISH.getString("toolDelete"));
        wavePane.add(addNdelete,0,3);

        wavePane.add(new Label(waveBundle.getString("specs_title")),
                Integer.parseInt(super.SPACING.getString("titlex")),
                Integer.parseInt(super.SPACING.getString("titley")));

        wavePane.add(setupInputs(),Integer.parseInt(super.SPACING.getString("labeledinputsx")),
                Integer.parseInt(super.SPACING.getString("labeledinputsy")));

        // Add save and cancel, save it globally so its behavior can be changed for editing
        mySaveNCancel = super.createSaveNCancel(18, super.ENGLISH.getString("save"),
                // in alphabetical order according to the backend properties file for creating
                // attackFreq, cost, image, title, upgradeFreqAmount, upgradeFreqCost
                new AddWave(myWaveData,this,
                        myWaveName,clickableEditorEntry,
                        myEnemyEditor.getChoiceBoxes().get(0),myWaves,super.getEngine()), super.ENGLISH.getString("toolSave"),
                super.ENGLISH.getString("cancel"), new CancelSaveController(this, super.getEngine()),
                super.ENGLISH.getString("toolCancel"));
        mySaveNCancel.setId("oldbutton");

        wavePane.add(mySaveNCancel, 2,3);

        wavePane.add(super.instructionText("WaveInstructions"), 5, 2);

        return wavePane;
    }

    @Override
    protected Pane setupInputs() {
        VBox inputs = super.createVBox();

        // Create input for level name
        myWaveName = new LabeledInput(waveBundle.getString("title"));
        inputs.getChildren().add(myWaveName.getNode());

        // insert alphabetically according to the backend properties file for editing
        myWaveInputs.add(myWaveName);

        return inputs;
    }

    @Override
    public void clearInputs() {
        super.clearLabeledTexts(myWaveInputs);
    }

    @Override
    public void updateInputs(Collection<String> inputsAlphabetically) {

    }

    @Override
    public GridPane getPane() {
        return wavePane;
    }

    @Override
    public List<LabeledInput> getLabeledInputs() {
        return null;
    }

    @Override
    public Editor getSubEditor() {
        return myEnemyEditor;
    }

    @Override
    public List<ChoiceBox> getChoiceBoxes() {
        return myRow.getChoiceBoxes();
    }

    @Override
    public Map<String, Integer> getIDMap() {
        return myWaveData.getIDNameMap();
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

    public ClickableEditorEntry createClickableEditorEntries() {
        myRow = new ClickableEditorEntry(super.ENGLISH.getString("Wave"), myWaves);
        return myRow;
    }

    public ObservableList<String> getMyWaves(){
        return FXCollections.unmodifiableObservableList(myWaves);
    }

    public WaveData getData(){
        return (WaveData) myWaveData;
    }
}

