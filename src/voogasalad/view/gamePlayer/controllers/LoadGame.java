package voogasalad.view.gamePlayer.controllers;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import voogasalad.gameEngine.Engine;
import voogasalad.gameEngine.exceptions.CannotLoadXMLException;
import voogasalad.view.Controller;
import voogasalad.view.gamePlayer.views.PlayerView;

import java.io.File;

/**
 * Defines what happens when the load game button is pressed
 * @author Angel Huizar
 * @author Gabriela Rodriguez-Florido
 */
public class LoadGame extends Controller {

    private Stage myParent;

    public LoadGame(Stage stage, Engine engine){
        super(engine);
        myParent = stage;
    }

    /**
     * Opens a file chooser that selects an xml file that defines the authored game
     */
    @Override
    public void execute() {
        getFile();

        myParent.close();
        PlayerView p = new PlayerView(myParent,super.getEngine());

        p.display(p.startVisualization());
    }

    private void getFile(){

        FileChooser fileChooser = new FileChooser();

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                if(! selectedFile.getName().contains(".xml")){
                    throw new Exception();
                }
                super.getEngine().loadData(selectedFile);
                super.getEngine().initGame();
            } catch(Exception e){
                throw new CannotLoadXMLException(" ");
            }
        }
    }
}
