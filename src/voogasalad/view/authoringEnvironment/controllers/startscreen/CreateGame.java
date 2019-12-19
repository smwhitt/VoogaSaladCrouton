package voogasalad.view.authoringEnvironment.controllers.startscreen;

import javafx.stage.Stage;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;
import voogasalad.view.SoundEffect;
import voogasalad.view.View;
import voogasalad.view.authoringEnvironment.views.AuthoringView;

/**
 * @author Angel Huizar
 * This class takes you into the authroing environment
 */
public class CreateGame extends Controller {
    private Stage myParent;

    /**
     * This constructor tells this class which stage it will add the new scene to
     * @param stage the stage for which the authoring environment will pop up
     * @param engine the engine this class will be working with
     */
    public CreateGame(Stage stage, Engine engine){
        super(engine);
        myParent = stage;
    }

    @Override
    public void execute() {
        View popUp = new AuthoringView(myParent,new Engine());
        myParent.close();
        popUp.display(popUp.startVisualization());
    }
}
