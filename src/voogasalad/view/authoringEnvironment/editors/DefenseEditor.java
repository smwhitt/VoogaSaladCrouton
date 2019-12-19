package voogasalad.view.authoringEnvironment.editors;
import javafx.scene.Node; import javafx.scene.control.ChoiceBox; import javafx.scene.control.Label; import javafx.scene.layout.GridPane; import javafx.scene.layout.HBox; import javafx.scene.layout.Pane; import javafx.scene.layout.VBox; import voogasalad.gameEngine.Engine; import voogasalad.view.Data; import voogasalad.view.authoringEnvironment.Editor; import voogasalad.view.authoringEnvironment.EditorSelector; import voogasalad.view.authoringEnvironment.controllers.CancelSaveController; import voogasalad.view.authoringEnvironment.controllers.defenses.AddDefense; import voogasalad.view.authoringEnvironment.data.DefenseData; import voogasalad.view.clickableobjects.LabeledInput; import java.util.*; import java.util.List;

/**
 * Edits a specific defense in the authoring environment
 *
 * This is the final piece of my masterpiece to show off refactoring of the view subclass editor and its pattern of setting up the labels and
 * other view components found on the screen. As another abstraction, it demonstrates how even frontend code can be encapsulated with frontend
 * specifics but general design to increase the flexibility should someone want to add an editor that looked just like this
 *
 * @author Samantha Whitt, Gabriela Rodriguez-Florido
 */
public class DefenseEditor extends Editor {
    private static final ResourceBundle myDefenseBundle = ResourceBundle.getBundle("voogasalad/view/authoringEnvironment/resources/Defense");
    private Data defenseData; EditorSelector defenseSelector; ProjectileEditor myProjectileEditor; GridPane defensePane; Node mySaveNCancel;
    private LabeledInput defenseName, defenseCost, attackFreq, freqCost, freqAmount;
    private List<LabeledInput> defenseInputs = new ArrayList<>(); List<ChoiceBox> myChoiceBoxes = new ArrayList<>();

    public DefenseEditor(ProjectileEditor projectileEditor, String title, Engine engine) {
        super(title, engine);
        myProjectileEditor = projectileEditor;
        defenseData = new DefenseData(this, engine);
        defenseSelector = super.createEditorSelector(new EditorSelector(1000,500,this));
        defensePane = super.createGridPane(); // Contains all of the UI elements for a defense Editor
    }

    /**
     * Sets up defense editor pane
     * @return defense editor border pane
     */
    @Override
    public Pane startVisualization() {
        return setupPane();
    }

    /**
     * Allows editors to return their save and cancel buttons so that their functionality can be changed to edit rather than create
     * @return save and cancel buttons
     */
    @Override
    public Node getSaveNCancel(){
        return mySaveNCancel;
    }

    /**
     * @return list of labeled texts
     */
    @Override
    public List<LabeledInput> getLabeledInputs(){
        return defenseInputs;
    }

    /**
     * @return "sub" editor located in the view
     */
    @Override
    public Editor getSubEditor() {
        return myProjectileEditor;
    }

    /**
     * @return list of choiceboxes in the view
     */
    @Override
    public List<ChoiceBox> getChoiceBoxes() {
        return myChoiceBoxes;
    }

    /**
     * @return corresponding data's map of defense names to their ids in the backend
     */
    @Override
    public Map<String, Integer> getIDMap() {
        return defenseData.getIDNameMap();
    }

    /**
     * @return pane holding all of the inputs
     */
    @Override
    public GridPane getPane() {
        return defensePane;
    }

    /**
     * clears the labeled inputs and deselects the image to start fresh for a new type of defense
     */
    @Override
    public void clearInputs() {
        super.clearLabeledTexts(defenseInputs);
        getImageSelector().deselectImage();
    }

    /**
     * updates the inputs upon an author clicking on a pre-existing defense to whatever settings were established for that defense
     * @param inputsAlphabetically
     */
    @Override
    public void updateInputs(Collection<String> inputsAlphabetically) {
        Object inputs[] = inputsAlphabetically.toArray();
        //takes in loaded inputs in alphabetical order
        defenseName.setText((String)inputs[3]);
        super.getImageSelector().selectImage((String)inputs[2]);
        defenseCost.setText((String)inputs[0]);
        attackFreq.setText((String)inputs[1]);
        freqCost.setText((String)inputs[5]);
        freqAmount.setText((String)inputs[6]);
    }

    /**
     * @return images of created defenses
     */
    public EditorSelector getEditorSelector() {
        return defenseSelector;
    }

    /**
     * Instantiates the border pane for the defense editor and adds all the relevant inputs
     * @return
     */
    @Override
    protected Pane setupPane() {
        defensePane.add(new Label(myDefenseBundle.getString("image_title")),
                Integer.parseInt(super.SPACING.getString("imageSelectorx")),
                Integer.parseInt(super.SPACING.getString("imageSelectory")));
        defensePane.add(new Label(myDefenseBundle.getString("specs_title")),
                Integer.parseInt(super.SPACING.getString("titlex")),
                Integer.parseInt(super.SPACING.getString("titley")));
        defensePane.add(super.setupImageSelector(Integer.parseInt(super.SPACING.getString("imageSelectorWidth")),
                Integer.parseInt(super.SPACING.getString("imageSelectorHeight")),
                Integer.parseInt(super.SPACING.getString("imageWidth")),
                Integer.parseInt(super.SPACING.getString("imageHeight")),
                myDefenseBundle.getString("defense_path")),
                0, 2);
        defensePane.add(setupInputs(), Integer.parseInt(super.SPACING.getString("labeledinputsx")),
                Integer.parseInt(super.SPACING.getString("labeledinputsy")));
        // Add save and cancel, save it globally so its behavior can be changed for editing
        mySaveNCancel = super.createSaveNCancel(15, super.ENGLISH.getString("save"),
                // in alphabetical order according to the backend properties file for creating
                // cost, attackFreq, image, name, upgradecost, upgradequantity
                new AddDefense(defenseData, this, defenseCost, attackFreq, super.getImageSelector(), defenseName,
                        freqAmount, freqCost, super.getEngine()), super.ENGLISH.getString("toolSave"),
                super.ENGLISH.getString("cancel"), new CancelSaveController(this, super.getEngine()),
                super.ENGLISH.getString("toolCancel"));
        mySaveNCancel.setId("oldbutton");
        defensePane.add(mySaveNCancel, 2, 3);
        defensePane.add(super.instructionText("DefenseInstructions"), 5, 2);
        return super.wrapInBorder(defensePane, defenseSelector.getNode());
    }

    /**
     * instantiates the right-hand side of the editor with all of the labeled inputs
     * @return
     */
    @Override
    protected Pane setupInputs() {
        VBox inputs = createVBox();
        defenseName = new LabeledInput(myDefenseBundle.getString("defense_title"));
        inputs.getChildren().add(defenseName.getNode());
        defenseCost = new LabeledInput(myDefenseBundle.getString("cost"));
        inputs.getChildren().add(defenseCost.getNode());
        attackFreq = new LabeledInput(myDefenseBundle.getString("attack_freq"));
        inputs.getChildren().add(attackFreq.getNode());
        freqCost = new LabeledInput(myDefenseBundle.getString("freq_upgrade") + myDefenseBundle.getString("upgrade_cost"));
        freqAmount = new LabeledInput(myDefenseBundle.getString("upgrade_add"));
        inputs.getChildren().add(createUpgradeInput(freqCost, freqAmount));
        inputs.getChildren().add(myProjectileEditor.createProjectileSelector());
        List<ChoiceBox> projectileChoices = myProjectileEditor.getChoiceBoxes();
        for (ChoiceBox projectileChoice: projectileChoices) {
            myChoiceBoxes.add(projectileChoice);
        }
        // insert alphabetically according to the backend properties file for editing
        defenseInputs.add(defenseCost);
        defenseInputs.add(attackFreq);
        defenseInputs.add(defenseName);
        defenseInputs.add(freqCost);
        defenseInputs.add(freqAmount);

        return inputs;
    }

    // creates an upgrade hbox input for both cost and amount on the same line
    private HBox createUpgradeInput(LabeledInput cost, LabeledInput amount) {
        HBox upgrade = new HBox();
        upgrade.setSpacing(10);
        upgrade.getChildren().add(cost.getNode());
        upgrade.getChildren().add(amount.getNode());
        return upgrade;
    }
}