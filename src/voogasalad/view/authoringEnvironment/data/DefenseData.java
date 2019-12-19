package voogasalad.view.authoringEnvironment.data;
import javafx.scene.Node; import voogasalad.gameEngine.Engine; import voogasalad.view.Data; import voogasalad.view.authoringEnvironment.controllers.CancelEditController; import voogasalad.view.authoringEnvironment.controllers.defenses.EditDefense; import voogasalad.view.authoringEnvironment.editors.DefenseEditor;

/**
 * DefenseData keeps track of all the defenses via its super class in handling the interaction with the backend and how to update the frontend accordingly.
 *
 * I included this also in my masterpiece to show off how data is the final step in communicating between the frontend and backend by having a list of all
 * names mapped to the ids in the constructor then loading appropriately when clicked on, or in this case saving appropriately when AddDefense is called.
 * It is a part of the abstraction for data classes and is tailored to defense editor.
 *
 * @author Gabriela Rodriguez-Florido, Samantha Whitt
 */
public class DefenseData extends Data {
    private Node myOldSaveNCancel;

    /**
     * Saves data and creates map according to id with the defense name
     * @param view, engine
     */
    public DefenseData(DefenseEditor view, Engine engine) {
        super(view,engine);
    }

    /**
     * Defines the behavior when a user clicks on a created object image to edit it
     * @param id
     */
    @Override
    public void load(int id) {
        // Remove old save and cancel
        myOldSaveNCancel = myView.getSaveNCancel();
        // Put new save and cancel to update
        Node newButtons = myView.createSaveNCancel(15, ENGLISH.getString("save"),
                new EditDefense(id, myView,super.getEngine(),myOldSaveNCancel), ENGLISH.getString("toolSave"),
                ENGLISH.getString("cancel"), new CancelEditController(myView, super.getEngine(), myOldSaveNCancel),
                ENGLISH.getString("toolCancel"));
        newButtons.setId("newbutton");
        myView.getPane().add(newButtons,2,3);
        // Load values from backend into texfields to edit
        myView.clearInputs();
        myView.updateInputs(super.getEngineParamsSortedByKey(id));
    }

    /**
     * Updates the editor selector of defense editor according to the provided defense id
     * @param new_id
     */
    @Override
    public void updateUI(int new_id){
        myView.getEditorSelector().createContent(new_id, this);
    }
}