package voogasalad.view.authoringEnvironment.controllers.grid;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;
import voogasalad.view.authoringEnvironment.views.grid.GridData;

/**
 * @author Angel Huizar
 * This class is responsible for saving the state of the grid
 */
public class SaveGridState extends Controller {
    private GridData myData;
    private TextField myText;
    private ChoiceBox<String> myChoice;

    /**
     * This constructor tells this class what it will be working with
     * @param data where to save the data
     * @param inputtedName the name inputted by the user
     * @param maps the choiceBox displayed to the user on the map editor
     * @param engine the engine this class will be working with
     */
    public SaveGridState(GridData data, TextField inputtedName, ChoiceBox<String> maps, Engine engine){
        super(engine);
        myData = data;
        myText = inputtedName;
        myChoice = maps;
    }

    /**
     * Defines the action that will occur for when a Node is interacted with (clickableNode)
     * Will be passed in to each ClickableNode to define its behavior on action
     */
    @Override
    public void execute() {
       myData.saveMap(myText.getText());
       myChoice.setValue(myText.getText());
    }
}
