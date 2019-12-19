package voogasalad.view.authoringEnvironment.controllers.defenses;
import voogasalad.gameEngine.Engine; import voogasalad.view.Controller; import voogasalad.view.Data; import voogasalad.view.authoringEnvironment.Editor; import voogasalad.view.authoringEnvironment.ImageSelector; import voogasalad.view.authoringEnvironment.editors.DefenseEditor; import voogasalad.view.clickableobjects.LabeledInput; import java.util.ArrayList; import java.util.Arrays; import java.util.List; import java.util.ResourceBundle;

/**
 * Called in the authoring environment when a user clicks "save" and creates a new type of defense as a new action.
 *
 * This code is part of my masterpiece because it's a subclass of the controller abstraction which details specific actions that
 * can be called by other classes or passed into other methods. It also displays the internal MVC model in that it interacts with
 * the both the frontend and backend by calling the intermediary class data to save all of the inputs from the defense editor view.
 *
 * @author Samantha Whitt
 */
public class AddDefense extends Controller {
    private final String PATH = "voogasalad/properties/Defense", OBJECT_NAME = "Defense";
    private LabeledInput myName, myDefenseCost, myAttackFreq, myFreqCost, myFreqAmount;
    private ImageSelector myImageSelector; List<String> myInputs; private DefenseEditor myEditor; private Data myData;

    /**
     * assumes that inputs are in alphabetical order according to the properties file in the backend and instantiates a new defense object in the backend
     * @param defenseData, attackFreq, cost, imageSelector, name, upAmount, upCost
     */
    public AddDefense(Data defenseData, Editor editor, LabeledInput cost, LabeledInput attackFreq, ImageSelector imageSelector,
                      LabeledInput name, LabeledInput upCost, LabeledInput upAmount, Engine engine) {
        super(engine);
        myData = defenseData; myEditor = (DefenseEditor) editor; myName = name; myDefenseCost = cost;
        myAttackFreq = attackFreq; myFreqCost = upCost; myFreqAmount = upAmount; myImageSelector = imageSelector;
    }

    /**
     * Calls data.save() to create a new object in the backend given the input fields in the frontend then clears the screen
     * The inputs must be in alphabetical order according to the corresponding backend properties file
     */
    @Override
    public void execute() {
        ResourceBundle bundle = ResourceBundle.getBundle(PATH);
        myInputs = new ArrayList<>();
        myInputs.addAll(Arrays.asList(myDefenseCost.getText(), myAttackFreq.getText(), myImageSelector.getFileName(),
                myName.getText(), Integer.toString(super.getSelectedIDsFromChoiceBox(myEditor).get(0)), myFreqAmount.getText(), myFreqCost.getText()));
        if(myData.save(myName.getText(), OBJECT_NAME, super.createMap(bundle.getKeys(), myInputs))){
            myEditor.clearInputs();
        }
    }
}