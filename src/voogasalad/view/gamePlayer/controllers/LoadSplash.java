package voogasalad.view.gamePlayer.controllers;

import javafx.stage.Stage;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;

public class LoadSplash extends Controller {

    private Stage myParent;

    public LoadSplash(Stage stage, Engine engine){
        super(engine);
        myParent = stage;
    }

}
