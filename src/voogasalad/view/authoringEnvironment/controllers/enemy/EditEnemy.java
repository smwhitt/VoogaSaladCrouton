package voogasalad.view.authoringEnvironment.controllers.enemy;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;
import voogasalad.view.authoringEnvironment.Editor;

import java.util.*;

public class EditEnemy extends Controller {

    private static final ResourceBundle myParams = ResourceBundle.getBundle("voogasalad/properties/Minion");

    private int myId;
    private Editor myEditor;
    private Node myOldSaveNCancel;

    public EditEnemy(int id, Editor editor, Engine engine, Node oldSaveNCancel) {
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

        // SENDS THE FOLLOWING VALUES TO THE BACKEND IN ALPHABETICAL ORDER: damage, health, image, resource, speed, title
        List<String> stringInputs = super.getInputs(myEditor);

        stringInputs.add(2, myEditor.getImageSelector().getFileName());
        super.getEngine().edit(myId, super.createMap(myParams.getKeys(), stringInputs));

        // and delete new one!
        myEditor.getPane().getChildren().remove(super.findNewButton(myEditor));

        // Once updated restore old save button!!
        myEditor.getPane().add(myOldSaveNCancel,2,3);

        myEditor.clearInputs();

    }
}
