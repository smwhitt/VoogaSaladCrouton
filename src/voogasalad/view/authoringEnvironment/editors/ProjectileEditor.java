package voogasalad.view.authoringEnvironment.editors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Data;
import voogasalad.view.authoringEnvironment.Editor;
import voogasalad.view.authoringEnvironment.EditorSelector;
import voogasalad.view.authoringEnvironment.ImageSelector;
import voogasalad.view.authoringEnvironment.controllers.CancelEditController;
import voogasalad.view.authoringEnvironment.controllers.CancelSaveController;
import voogasalad.view.authoringEnvironment.controllers.defenses.AddDefense;
import voogasalad.view.authoringEnvironment.controllers.projectile.AddProjectile;
import voogasalad.view.authoringEnvironment.data.ProjectileData;
import voogasalad.view.clickableobjects.LabeledInput;

import java.lang.reflect.Array;
import java.util.*;

/**
 * This class creates a projectile editor to allow the author to make new kinds of projectiles
 * @author Gabriela Rodriguez-Florido, Samantha Whitt
 */
public class ProjectileEditor extends Editor {


    private static final ResourceBundle projectileBundle = ResourceBundle.getBundle("voogasalad/view/authoringEnvironment/resources/Projectile");
    private static final ResourceBundle collisionBundle = ResourceBundle.getBundle("voogasalad/properties/Interaction");
    private static final ResourceBundle AIBundle = ResourceBundle.getBundle("voogasalad/properties/AI");
    private ObservableList<String> myProjectilesNames = FXCollections.observableArrayList();

    private Data projectileData;
    private GridPane projectilePane;
    private LabeledInput projectileName, projectileDamage, projectileHealth, projectileSpeed, myDuration;
    private EditorSelector projectileSelector;
    private Node mySaveNCancel;
    private List<LabeledInput> projectileInputs = new ArrayList<>();
    private List<ChoiceBox> myChoiceBoxes = new ArrayList<>();
    // For collision functionality
    private ChoiceBox myCollisionBehaviors, myAI;
    private ObservableList myCollisionOptions = FXCollections.observableArrayList(collisionBundle.keySet());
    private ObservableList myAIOptions = FXCollections.observableArrayList(AIBundle.keySet());


    public ProjectileEditor(String title, Engine engine) {
        super(title, engine);
        projectileSelector = super.createEditorSelector(new EditorSelector(1000,500,this));
        projectileData = new ProjectileData(this, engine);
        projectilePane = super.createGridPane();
    }

    @Override
    public Node startVisualization() {
        return setupPane();
    }

    @Override
    public void clearInputs() {
        super.clearLabeledTexts(projectileInputs);
        getImageSelector().deselectImage();
    }

    @Override
    public void updateInputs(Collection<String> loadedInputStrings) {
        Object inputs[] = loadedInputStrings.toArray();
        projectileDamage.setText((String)inputs[1]);
        projectileHealth.setText((String)inputs[2]);
        super.getImageSelector().selectImage((String)inputs[3]);
        // interaction is 3
        projectileName.setText((String)inputs[4]);
        projectileSpeed.setText((String)inputs[5]);
    }

    /**
     * Allows editors to return their save and cancel buttons so that their functionality can be changed to edit rather
     * than create
     * @return save and cancel buttons
     */
    @Override
    public Node getSaveNCancel(){
        return mySaveNCancel;
    }

    /**
     * This method returns the list of projectile names for other editors to use in a combo box
     * @return an observable list of projectile names
     */
    public ObservableList<String> getMyProjectilesNames() {
        return FXCollections.unmodifiableObservableList(myProjectilesNames);
    }

    @Override
    public GridPane getPane() {
        return projectilePane;
    }

    @Override
    public List<LabeledInput> getLabeledInputs() {
        return projectileInputs;
    }

    @Override
    public Editor getSubEditor() {
        return null;
    }

    @Override
    public List<ChoiceBox> getChoiceBoxes() {
        return myChoiceBoxes;
    }

    @Override
    public Map<String, Integer> getIDMap() {
        return projectileData.getIDNameMap();
    }

    /**
     * Adds new name to combobox for selection of projectile in defense
     * @return
     */
    public Node createProjectileSelector() {
        HBox box = new HBox();
        box.setSpacing(10);
        Label label = new Label(ResourceBundle.getBundle(languagePath+LANGUAGE).getString("PickProjectile"));
        box.getChildren().add(label);

        myChoiceBoxes.add(new ChoiceBox<>(myProjectilesNames));

        for (ChoiceBox choiceBox: myChoiceBoxes) {
            box.getChildren().add(choiceBox);
        }

        return box;
    }

    @Override
    protected Pane setupPane() {
        projectilePane.add(new Label(projectileBundle.getString("image_title")),
                Integer.parseInt(super.SPACING.getString("imageSelectorx")),
                Integer.parseInt(super.SPACING.getString("imageSelectory")));
        projectilePane.add(new Label(projectileBundle.getString("specs_title")),
                Integer.parseInt(super.SPACING.getString("titlex")),
                Integer.parseInt(super.SPACING.getString("titley")));
        projectilePane.add(super.setupImageSelector(Integer.parseInt(super.SPACING.getString("imageSelectorWidth")),
                Integer.parseInt(super.SPACING.getString("imageSelectorHeight")),
                Integer.parseInt(super.SPACING.getString("imageWidth")),
                Integer.parseInt(super.SPACING.getString("imageHeight")), projectileBundle.getString("projectile_path")),
                0, 2);
        projectilePane.add(setupInputs(), Integer.parseInt(super.SPACING.getString("labeledinputsx")),
                Integer.parseInt(super.SPACING.getString("labeledinputsy")));

        // Add save and cancel, save it globally so its behavior can be changed for editing
        mySaveNCancel = super.createSaveNCancel(15, super.ENGLISH.getString("save"),
                // in alphabetical order according to the backend properties file for creating
                // attackFreq, cost, image, title, upgradeFreqAmount, upgradeFreqCost
                new AddProjectile(projectileData, this, projectileSpeed,
                        projectileDamage, myDuration, super.getImageSelector(), projectileName, projectileHealth, myProjectilesNames,
                        myCollisionBehaviors, myAI,
                        super.getEngine()), super.ENGLISH.getString("toolSave"), super.ENGLISH.getString("cancel"),
                new CancelSaveController(this,super.getEngine()), super.ENGLISH.getString("toolCancel"));
        mySaveNCancel.setId("oldbutton");
        projectilePane.add(mySaveNCancel, 2,3);

        projectilePane.add(super.instructionText("ProjectileInstructions"), 5, 2);

        return super.wrapInBorder(projectilePane, projectileSelector.getNode());
    }

    @Override
    protected Pane setupInputs() {
        VBox inputs = createVBox();

        projectileName = new LabeledInput(projectileBundle.getString("projectile_title"));
        inputs.getChildren().add(projectileName.getNode());

        projectileDamage = new LabeledInput(projectileBundle.getString("projectile_damage"));
        inputs.getChildren().add(projectileDamage.getNode());

        projectileHealth = new LabeledInput(projectileBundle.getString("projectile_health"));
        inputs.getChildren().add(projectileHealth.getNode());

        projectileSpeed = new LabeledInput(projectileBundle.getString("projectile_speed"));
        inputs.getChildren().add(projectileSpeed.getNode());

        // Add drop down to select collision behavior for a projectile
        myCollisionBehaviors = new ChoiceBox(myCollisionOptions);
        inputs.getChildren().addAll( new Label(projectileBundle.getString("projectile_interaction_prompt")),myCollisionBehaviors);

        // Add input to select effect duration for collision
        myDuration = new LabeledInput("Insert effect duration ");
        inputs.getChildren().add(myDuration.getNode());

        // Add drop down to select AI behavior of projectile
        myAI = new ChoiceBox(myAIOptions);
        inputs.getChildren().addAll( new Label(projectileBundle.getString("projectile_ai_prompt")),myAI);

        // insert alphabetically according to the backend properties file for editing
        // Adding all labeled inputs to the list for comboboxes

        projectileInputs.add(projectileDamage);
        projectileInputs.add(myDuration);
        projectileInputs.add(projectileHealth);
        projectileInputs.add(projectileName);
        projectileInputs.add(projectileSpeed);

        return inputs;
    }
}

