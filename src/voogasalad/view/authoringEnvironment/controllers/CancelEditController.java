package voogasalad.view.authoringEnvironment.controllers;
import javafx.scene.Node;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;
import voogasalad.view.View;
import voogasalad.view.authoringEnvironment.Editor;

public class CancelEditController extends Controller {

    private Editor myView;
    private Node myOldSaveNCancel;

    public CancelEditController(View view, Engine engine, Node oldSaveNCancel){
        super(engine);
        myView = (Editor) view;
        myOldSaveNCancel = oldSaveNCancel;
    }

    @Override
    public void execute() {
        myView.clearInputs();
        myView.closeScene();
        myView.getPane().getChildren().remove(super.findNewButton(myView));
        myView.getPane().add(myOldSaveNCancel,2,3);
    }
}