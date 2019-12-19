package voogasalad.view.authoringEnvironment.controllers.projectile;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;
import voogasalad.view.authoringEnvironment.Editor;

import java.util.*;

public class EditProjectile extends Controller {

    private static final ResourceBundle myParams = ResourceBundle.getBundle("voogasalad/properties/Projectile");

    private int myId;
    private Editor myEditor;
    private Node myOldSaveNCancel;

    public EditProjectile(int id, Editor editor, Engine engine, Node oldSaveNCancel) {
        super(engine);
        myEditor = editor;
        myId = id;
        myOldSaveNCancel = oldSaveNCancel;
    }

    /**
     * Defines what happens when the save button to edit a projectile is clicked
     */
    @Override
    public void execute() {

        // SENDS THE FOLLOWING VALUES TO THE BACKEND IN ALPHABETICAL ORDER: damage, health, image, speed, name

        List<String> stringInputs = super.getInputs(myEditor);
        System.out.println("String inputs: "  + stringInputs);

        stringInputs.add(2, myEditor.getImageSelector().getFileName());
        super.getEngine().edit(myId, super.createMap(myParams.getKeys(), stringInputs));

        // and delete new one!
        System.out.println("REMOVED NEW BUTTON?" + myEditor.getPane().getChildren().remove(super.findNewButton(myEditor)));
        myEditor.getPane().getChildren().remove(super.findNewButton(myEditor));

        // Once updated restore old save button!!
        myEditor.getPane().add(myOldSaveNCancel,2,3);

        myEditor.clearInputs();

    }
}
