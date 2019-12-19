package voogasalad.view.authoringEnvironment.editors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.layout.*;

import javafx.stage.Stage;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Data;
import voogasalad.view.authoringEnvironment.Editor;
import voogasalad.view.authoringEnvironment.EditorSelector;

import voogasalad.view.authoringEnvironment.controllers.CancelSaveController;
import voogasalad.view.authoringEnvironment.controllers.enemy.AddEnemy;
import voogasalad.view.authoringEnvironment.data.EnemyData;
import voogasalad.view.clickableobjects.LabeledInput;

import java.util.*;

/**
 * This class allows the user to create or edit an enemy to use in their game
 * @author Gabriela Rodriguez-Florido, Samantha Whitt, Shreya Hurley
 */
public class EnemyEditor extends Editor {
    private final ResourceBundle enemyBundle = ResourceBundle.getBundle("voogasalad/view/authoringEnvironment/resources/Enemies");

    private LabeledInput enemyName, enemyHealth, enemySpeed, enemyResource, enemyDamage;
    private GridPane enemyPane;
    private EditorSelector enemySelector;
    private Data enemyData;
    private Node mySaveNCancel;
    private ObservableList<String> myEnemyNames;
    private List<LabeledInput> enemyInputs = new ArrayList<>();
    private List<ChoiceBox> myChoiceBoxes = new ArrayList<>();

    /**
     * constructor for editor for enemy type in the GUI
     * @param title
     * @param engine global instance of engine
     */
    public EnemyEditor(String title, Engine engine) {
        super(title, engine);
        enemySelector = super.createEditorSelector(new EditorSelector(1000,500,this));
        enemyData = new EnemyData(this, super.getEngine());
        enemyPane = super.createGridPane();
        myEnemyNames = FXCollections.observableArrayList();
    }

    /**
     * Adds all of the components of the enemy editor into one gridpane
     * @return pane
     */
    @Override
    protected Pane setupPane() {
        enemyPane.add(new Label(enemyBundle.getString("image_title")),
                Integer.parseInt(super.SPACING.getString("imageSelectorx")),
                Integer.parseInt(super.SPACING.getString("imageSelectory")));
        enemyPane.add(new Label(enemyBundle.getString("specs_title")),
                Integer.parseInt(super.SPACING.getString("titlex")),
                Integer.parseInt(super.SPACING.getString("titley")));
        enemyPane.add(super.setupImageSelector(Integer.parseInt(super.SPACING.getString("imageSelectorWidth")),
                Integer.parseInt(super.SPACING.getString("imageSelectorHeight")),
                Integer.parseInt(super.SPACING.getString("imageWidth")),
                Integer.parseInt(super.SPACING.getString("imageHeight")), enemyBundle.getString("enemies_path")),
                0, 2);
        enemyPane.add(setupInputs(), Integer.parseInt(super.SPACING.getString("labeledinputsx")),
                Integer.parseInt(super.SPACING.getString("labeledinputsy")));

        // Add save and cancel, save it globally so its behavior can be changed for editing
        Node controlButtons = super.createSaveNCancel(15, super.ENGLISH.getString("save"),
                // in alphabetical order according to the backend properties file for creating
                // damage, health, image, name, speed, value
                new AddEnemy(enemyData, this, enemyDamage, enemyHealth, super.getImageSelector(), enemyName,
                        enemySpeed, enemyResource, myEnemyNames, super.getEngine()), super.ENGLISH.getString("toolSave"),
                super.ENGLISH.getString("cancel"), new CancelSaveController(this, super.getEngine()),
                super.ENGLISH.getString("toolCancel"));
        mySaveNCancel = controlButtons;
        controlButtons.setId("oldbutton");
        enemyPane.add(mySaveNCancel, 2,3);
        enemyPane.add(super.instructionText("EnemyInstructions"), 5, 2);
        return super.wrapInBorder(enemyPane, enemySelector.getNode());
    }

    /**
     * Sets up all of the user inputs required to create an enemy
     * @return
     */
    @Override
    protected Pane setupInputs() {
        VBox inputs = super.createVBox();

        enemyName = new LabeledInput(enemyBundle.getString("enemy_title"));
        inputs.getChildren().add(enemyName.getNode());

        enemyHealth = new LabeledInput(enemyBundle.getString("enemy_health"));
        inputs.getChildren().add(enemyHealth.getNode());

        enemyDamage = new LabeledInput(enemyBundle.getString("enemy_damage"));
        inputs.getChildren().add(enemyDamage.getNode());

        enemySpeed = new LabeledInput(enemyBundle.getString("enemy_speed"));
        inputs.getChildren().add(enemySpeed.getNode());

        enemyResource = new LabeledInput(enemyBundle.getString("enemy_resource"));
        inputs.getChildren().add(enemyResource.getNode());

        // insert alphabetically according to the backend properties file for editing
        // damage, health, image, name, speed, value
        enemyInputs.add(enemyDamage);
        enemyInputs.add(enemyHealth);
        enemyInputs.add(enemyName);
        enemyInputs.add(enemySpeed);
        enemyInputs.add(enemyResource);

        return inputs;
    }

    /**
     * Allows a controller to clear the inputs, for example when saving it is nice to have all the inputs cleared after
     * you're done, or you may want to cancel your changes.
     */
    @Override
    public void clearInputs() {
        for(LabeledInput l : enemyInputs){
            l.setText("");
        }
        getImageSelector().deselectImage();
    }

    /**
     * Allows a controller to update the contents of the inputs, which is useful for editing an enemy's parameters
     * @param inputsAlphabetically
     */
    @Override
    public void updateInputs(Collection<String> inputsAlphabetically) {
        Object inputs[] = inputsAlphabetically.toArray();

        //takes in loaded inputs in alphabetical order
        enemyDamage.setText((String)inputs[0]);
        enemyHealth.setText((String)inputs[1]);
        getImageSelector().selectImage((String)inputs[2]);
        enemyName.setText((String)inputs[3]);
        enemySpeed.setText((String)inputs[4]);
        enemyResource.setText((String)inputs[5]);
    }

    /**
     * Allows a controller to get the enemy pane in order to add things like error messages
     * @return enemyPane
     */
    @Override
    public GridPane getPane() {
        return enemyPane;
    }

    /**
     * Allows a controller to get all the text inputs in the enemy editor so that the contents can be extracted and
     * sent to the backend
     * @return
     */
    @Override
    public List<LabeledInput> getLabeledInputs() {
        return enemyInputs;
    }

    /**
     * Allows a controller to get the editor, which this current editor depends on having created something to create
     * this object. For an enemy, there is no dependent editor, however.
     * @return
     */
    @Override
    public Editor getSubEditor() {
        return null;
    }

    /**
     * Allows a controller to get all the choice boxes in an editor so that it can get the selected value.
     * @return
     */
    @Override
    public List<ChoiceBox> getChoiceBoxes() {
        ChoiceBox<String> defenseChoices = new ChoiceBox<>(myEnemyNames);
        myChoiceBoxes.add(defenseChoices);

        return myChoiceBoxes;
    }

    /**
     * Allows a controller to get the id for a given frontend name so that when a user edits an enemy, it edits the correct
     * backend enemy.
     * @return
     */
    @Override
    public Map<String, Integer> getIDMap() {
        return enemyData.getIDNameMap();
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
     * Calls method to add all user interaction components to a gridpane, which is then added to a border pane
     * @return pane containing everything in this editor
     */
    @Override
    public Pane startVisualization(){
        return setupPane();
    }

    /**
     * Another view that needs to know which enemies were created (like waves) in order to associate gets the enemy list
     * in order to provide options to the user for what enemy to spawn for that object
     * @return created enemy names
     */
    public ObservableList getCreatedEnemyList(){
        return myEnemyNames;
    }
}
