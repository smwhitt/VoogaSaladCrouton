package voogasalad.view.gamePlayer.controllers;

import voogasalad.controller.GameLoop;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;

/**
 * @author Angel Huizar
 * This class is responsible for resuming the game
 */
public class ResumeGameController extends Controller {
    private GameLoop myGameLoop;

    /**
     * This constructor tells this class what engine and game loop it will work with
     * @param engine the engine this class will work with
     * @param gameLoop the gameLoop this class will work with
     */
    public ResumeGameController(Engine engine, GameLoop gameLoop) {
        super(engine);
        myGameLoop = gameLoop;
    }

    @Override
    public void execute(){
        mySoundEffect.play();
        myGameLoop.resume();
    }
}
