package voogasalad.view.authoringEnvironment.controllers;

import javafx.stage.Stage;
import voogasalad.controller.GameLoop;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;
import voogasalad.view.SoundEffect;

/**
 * This class allows the user to go back to the previous screen
 *
 * @author Gabriela Rodriguez-Florido
 */

public class BackController extends Controller {

    Stage myStage;
    Stage myPrevStage;
    private GameLoop myLoop;
    private SoundEffect mySound;

    /**
     * Allows nodes on the screen that are intended to allow the user to go back to the previous window to close the current stage
     * and open the old one.
     * @param currStage stage to be closed
     * @param prevStage stage to be reopened
     */
    public BackController(Stage currStage, Stage prevStage, Engine engine){
        super(engine);
        myStage = currStage;
        myPrevStage = prevStage;
    }

    public BackController(Stage currStage, Stage prevStage, Engine engine, SoundEffect effect, GameLoop loop){
        super(engine);
        myStage = currStage;
        myPrevStage = prevStage;
        mySound = effect;
        myLoop = loop;
    }

    /**
     * Defines the closing and reopening of the window behavior in the execute method from the parent Controller class
     */
    @Override
    public void execute() {
        myStage.close();
        myPrevStage.show();
        mySoundEffect.play();
        if(mySound!=null){
            mySound.endLoop();
        }
        if(myLoop!=null){
            myLoop.pause();
        }
        System.out.println("Switching back to the main selection screen");
    }
}
