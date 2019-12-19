package voogasalad.view.authoringEnvironment.controllers;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;
import voogasalad.view.View;
import voogasalad.view.authoringEnvironment.Editor;

public class CancelSaveController extends Controller {
    private View myView;

    public CancelSaveController(View view, Engine engine){
        super(engine);
        myView = view;
    }

    @Override
    public void execute() {
        try {
            ((Editor)myView).clearInputs();
        }
        catch (ClassCastException e){
            myView.closeScene();
        }
        myView.closeScene();
    }
}
