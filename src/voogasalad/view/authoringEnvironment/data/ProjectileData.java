package voogasalad.view.authoringEnvironment.data;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Data;
import voogasalad.view.View;
import voogasalad.view.authoringEnvironment.Editor;
import voogasalad.view.authoringEnvironment.controllers.CancelEditController;
import voogasalad.view.authoringEnvironment.controllers.defenses.EditDefense;
import voogasalad.view.authoringEnvironment.controllers.projectile.EditProjectile;
import voogasalad.view.authoringEnvironment.editors.DefenseEditor;
import voogasalad.view.authoringEnvironment.editors.ProjectileEditor;

import java.util.*;

public class ProjectileData extends Data {
    private Node myOldSaveNCancel;

    public ProjectileData(ProjectileEditor projectileEditor, Engine engine) {
        super(projectileEditor, engine);
    }

    @Override
    public void updateUI(int new_id){
        myView.getEditorSelector().createContent(new_id, this);
    }

    /**
     * Defines what happens when an image in the editor selector list is clicked on
     * @param id
     */
    @Override
    public void load(int id) {
        // Remove old save and cancel
        System.out.println("LABELED INPUTS: " + myView.getLabeledInputs());
        myOldSaveNCancel = ((ProjectileEditor)myView).getSaveNCancel();
        System.out.println("Removed?" + myView.getPane().getChildren().remove(myOldSaveNCancel));

        // Put new save and cancel to update
        Node newButtons = myView.createSaveNCancel(15, ENGLISH.getString("save"),
                new EditProjectile(id, myView,super.getEngine(),myOldSaveNCancel), ENGLISH.getString("toolSave"),
                ENGLISH.getString("cancel"), new CancelEditController(myView,super.getEngine(),myOldSaveNCancel),
                ENGLISH.getString("toolCancel"));
        newButtons.setId("newbutton");
        myView.getPane().add(newButtons,2,3);

        myView.clearInputs();
        myView.updateInputs(super.getEngineParamsSortedByKey(id));
    }

}
