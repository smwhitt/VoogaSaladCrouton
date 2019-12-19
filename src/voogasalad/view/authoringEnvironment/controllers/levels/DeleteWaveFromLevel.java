package voogasalad.view.authoringEnvironment.controllers.levels;

import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;
import voogasalad.view.clickableobjects.ClickableEditorEntry;

public class DeleteWaveFromLevel extends Controller {

    private ClickableEditorEntry myEntry;

    public DeleteWaveFromLevel(ClickableEditorEntry e, Engine engine){
        super(engine);
        myEntry = e;
    }

    @Override
    public void execute() {
        myEntry.deleteEntry();
    }

}
