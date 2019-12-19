package voogasalad.view.authoringEnvironment.controllers.grid;

import javafx.scene.paint.Color;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;
import voogasalad.view.View;
import voogasalad.view.authoringEnvironment.views.grid.GridView;

/**
 * @author Angel Huizar
 * This class changes the color of the grid
 */
public class ChangeGridColor extends Controller {
    private View myGrid;
    private Color myColor;

    /**
     * This constructor just tells this class what view, what color, and what engine to use
     * @param grid the view for which we will change the color of the grid
     * @param color the color we wish to use
     * @param engine the engine this class will work with
     */
    public ChangeGridColor(View grid, Color color, Engine engine){
        super(engine);
        myGrid = grid;
        myColor = color;
    }

    @Override
    public void execute() {
        ((GridView)myGrid).setGridColor(myColor);
    }
}
