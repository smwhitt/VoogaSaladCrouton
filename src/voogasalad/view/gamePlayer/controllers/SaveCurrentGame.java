package voogasalad.view.gamePlayer.controllers;

import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Angel Huizar
 * This class is responsible for saving the current state of the game
 */
public class SaveCurrentGame extends Controller {
    /**
     * This constructor just makes a call to super
     * @param engine
     */
    public SaveCurrentGame(Engine engine) {
        super(engine);
    }

    @Override
    public void execute(){
        AtomicReference<String> nameOfGame = new AtomicReference<>("");
        super.getEngine().getEntities().forEach((integer, stringStringMap) -> {
            if (stringStringMap.get("type").equalsIgnoreCase("game")){
                nameOfGame.set(stringStringMap.get("name"));
            }
        });
        super.getEngine().save(nameOfGame.get());
        mySoundEffect.play();
    }
}
