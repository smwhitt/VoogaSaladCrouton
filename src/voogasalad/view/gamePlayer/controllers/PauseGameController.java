package voogasalad.view.gamePlayer.controllers;

import voogasalad.controller.GameLoop;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;

/**
 * @author Angel Huizar
 * This class is responsible for pausing the game loop
 */
public class PauseGameController extends Controller {
    private GameLoop myGameLoop;

    /**
     * This constructor tells this class what engine and gameLoop it will be working with
     * @param engine the engine this class will be working with
     * @param gameLoop the gameLoop this class will be working with
     */
    public PauseGameController(Engine engine, GameLoop gameLoop) {
        super(engine);
        myGameLoop = gameLoop;
    }

    @Override
    public void execute(){
        mySoundEffect.play();
        myGameLoop.pause();
    }
}
