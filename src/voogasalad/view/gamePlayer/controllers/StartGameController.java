package voogasalad.view.gamePlayer.controllers;

import javafx.stage.Stage;
import voogasalad.controller.GameLoop;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;
import voogasalad.view.SoundEffect;
import voogasalad.view.View;
import voogasalad.view.gamePlayer.views.EnemyManager;
import voogasalad.view.gamePlayer.views.PlayerView;

import java.util.List;

/**
 * @author Angel Huizar
 * This class is responsible for starting the game loop
 */
public class StartGameController extends Controller {
    private GameLoop myGameLoop;

    /**
     * This constructor makes a call to super, then keeps track of the game loop
     * @param engine the engine this class will work with
     * @param gameLoop the game loop that this class will start up when activated
     */
    public StartGameController(Engine engine, GameLoop gameLoop) {
        super(engine);
        myGameLoop = gameLoop;
    }

    @Override
    public void execute(){
        super.getEngine().playgame();
        myGameLoop.start(new Stage());
    }
}
