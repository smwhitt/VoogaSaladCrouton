package voogasalad.view.authoringEnvironment.controllers.grid;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;
import voogasalad.view.authoringEnvironment.views.grid.GridData;
import voogasalad.view.authoringEnvironment.views.grid.GridView;

/**
 * @author Angel Huizar
 * This class is responsible for deleting a saved map as well as clearing the map of all the tiles
 */
public class DeleteGridState extends Controller {
    private GridData myData;
    private ChoiceBox<String> myChoices;
    private GridView myParent;

    /**
     * This constructor tells this class what it will be working with
     * @param data the map data created by the user
     * @param choices the choiceBox that shows the available list of options to the user
     * @param view the GridView which displays the tiles
     * @param engine the engine this class will be working with
     */
    public DeleteGridState(GridData data, ChoiceBox<String> choices, GridView view, Engine engine) {
        super(engine);
        myData = data;
        myChoices = choices;
        myParent = view;
    }

    @Override
    public void execute(){
        myData.deleteMap(myChoices.getValue());
        myParent.resetGrid();
    }
}
