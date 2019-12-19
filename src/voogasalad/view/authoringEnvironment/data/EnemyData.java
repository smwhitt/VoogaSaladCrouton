package voogasalad.view.authoringEnvironment.data;

import javafx.scene.Node;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Data;
import voogasalad.view.authoringEnvironment.Editor;
import voogasalad.view.authoringEnvironment.controllers.CancelEditController;
import voogasalad.view.authoringEnvironment.controllers.enemy.EditEnemy;

public class EnemyData extends Data {
    private Node myOldSaveNCancel;

    public EnemyData(Editor view, Engine engine) {
        super(view, engine);
    }

    @Override
    public void load(int id) {
        // Remove old save and cancel
        myOldSaveNCancel = myView.getSaveNCancel();

        // Put new save and cancel to update
        Node newButtons = myView.createSaveNCancel(15, ENGLISH.getString("save"),
                new EditEnemy(id, myView, super.getEngine(), myOldSaveNCancel), ENGLISH.getString("toolSave"),
                ENGLISH.getString("cancel"), new CancelEditController(myView, super.getEngine(), myOldSaveNCancel),
                ENGLISH.getString("toolCancel"));
        newButtons.setId("newbutton");
        myView.getPane().add(newButtons,2,3);

        // Load values from backend into texfields to edit
        myView.clearInputs();
        myView.updateInputs(super.getEngineParamsSortedByKey(id));
    }

    @Override
    public void updateUI(int new_id){
        myView.getEditorSelector().createContent(new_id, this);
    }
}
