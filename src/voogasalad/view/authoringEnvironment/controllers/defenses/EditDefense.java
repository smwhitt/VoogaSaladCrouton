package voogasalad.view.authoringEnvironment.controllers.defenses;

import javafx.scene.Node;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;
import voogasalad.view.View;
import voogasalad.view.authoringEnvironment.Editor;
import voogasalad.view.authoringEnvironment.editors.DefenseEditor;
import voogasalad.view.authoringEnvironment.editors.ProjectileEditor;
import voogasalad.view.clickableobjects.LabeledInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Called in the authoring environment when a user clicks "save" upon already clicking a pre-existing defense to edit it
 * @author Samantha Whitt, Gabriela Rodriguez-Florido
 */
public class EditDefense extends Controller {

    private static final ResourceBundle myParams = ResourceBundle.getBundle("voogasalad/properties/Defense");

    private int myId;
    private Editor myEditor;
    private Node myOldSaveNCancel;

    /**
     * creates the functionality to edit rather than create a defense
     * @param id
     * @param view
     * @param engine
     * @param oldSaveNCancel
     */
    public EditDefense(int id, Editor view, Engine engine, Node oldSaveNCancel) {
        super(engine);
        myEditor = view;
        myId = id;
        myOldSaveNCancel = oldSaveNCancel;

    }

    /**
     * Defines what happens when the save button to edit a defense is clicked and assumes inputs are alphabetical
     */
    @Override
    public void execute() {
        // SENDS THE FOLLOWING VALUES TO THE BACKEND IN ALPHABETICAL ORDER:
        // cost, frequency, image, name, spawnentity, upgradecost, upgradeamount

        List<LabeledInput> myInputs = myEditor.getLabeledInputs();
        List<String> stringInputs = new ArrayList<>();

        // Getting labeled input text from defense editor
        for (int i=0; i<myInputs.size(); i++) {
                stringInputs.add(myInputs.get(i).getText());
        }

        stringInputs.add(2, ((Editor) myEditor).getImageSelector().getFileName());

        stringInputs.add(4, Integer.toString(super.getSelectedIDsFromChoiceBox(myEditor).get(0)));

        super.getEngine().edit(myId, super.createMap(myParams.getKeys(), stringInputs));

        updateButtons();

        myEditor.clearInputs();


    }

    private void updateButtons(){
        // SENDS THE FOLLOWING VALUES TO THE BACKEND IN ALPHABETICAL ORDER: damage, health, image, speed, name
        // and delete new one!
        myEditor.getPane().getChildren().remove(super.findNewButton(myEditor));

        // Once updated restore old save button!!
        myEditor.getPane().add(myOldSaveNCancel,2,3);
    }
}