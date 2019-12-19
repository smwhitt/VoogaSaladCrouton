package voogasalad.view.authoringEnvironment.controllers;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;
import voogasalad.view.authoringEnvironment.data.LevelData;
import voogasalad.view.authoringEnvironment.editors.GameEditor;
import voogasalad.view.authoringEnvironment.editors.LevelEditor;
import voogasalad.view.clickableobjects.LabeledInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Angel Huizar
 * This class just saves a game to xml file
 */
public class SaveGameToXML extends Controller {

    private final String PATH = "voogasalad/properties/";

    private LabeledInput myName, myStartingResources;
    private GameEditor myGameEditor;
    private List<ChoiceBox> myChoiceBoxes;

    /**
     * This constructor tells this class what to save, and its name
     * @param gameEditor the editor tab so we can access its data
     * @param gameName the name of the game to save
     * @param startingResources amount of resources to start with
     * @param engine the engine this class will work with
     * @param choiceBoxes the list of choiceBoxes presented to the user so we can get their selected values
     */
    public SaveGameToXML(GameEditor gameEditor, LabeledInput gameName, LabeledInput startingResources,
                         Engine engine, List<ChoiceBox> choiceBoxes){
        super(engine);
        myName = gameName;
        myStartingResources = startingResources;
        myGameEditor = gameEditor;
        myChoiceBoxes = choiceBoxes;
    }

    @Override
    public void execute(){
        ResourceBundle resources = ResourceBundle.getBundle(PATH+"Resources");
        ResourceBundle tower = ResourceBundle.getBundle(PATH+"Tower");
        ResourceBundle game = ResourceBundle.getBundle(PATH+"Game");

        int resourceID = super.getEngine().create("Resources" , super.createMap(resources.getKeys(),
                Arrays.asList(myStartingResources.getText())));

        List<String> values = new ArrayList<>();
        for(int i = 0; i < (myGameEditor).getChoiceBoxes().size(); i++){
            values.add(Integer.toString(
                    myGameEditor.getSubEditor().getIDMap().get(myGameEditor.getChoiceBoxes().get(i).getValue()
                    )));
        }

        super.getEngine().create("Game", super.createMap(game.getKeys(),
                List.of(values.toString().replace("[","").replace("]","").
                        replace(" ",""),
                        myName.getText(),
                        Integer.toString(resourceID))));

        super.getEngine().save(myName.getText());
    }


}
