package voogasalad.view.authoringEnvironment.controllers.grid;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;
import voogasalad.view.authoringEnvironment.data.WaveData;
import voogasalad.view.authoringEnvironment.views.PopUpWaves;
import voogasalad.view.authoringEnvironment.views.grid.GridData;
import voogasalad.view.authoringEnvironment.views.grid.GridView;

/**
 * @author Angel Huizar
 * This class is responsible for allowing the user to shift click on tiles with arrows to set them as start/end points
 */
public class SetStartNEndPoints extends Controller {
    private boolean isEndSet;
    private GridData myData;
    private GridView myParent;
    private WaveData myWaveData;

    /**
     * This constructor tells this class what it will be working with
     * @param data the map data so we can check for end points or set points as start/end
     * @param view the MapEditor so we can setTile images in it
     * @param engine the engine this class will be working with
     * @param waveData the waveData created by the user in the event that the user clicks on a start point so
     *                 we can pass it to a PopUpWaves view
     */
    public SetStartNEndPoints(GridData data, GridView view, Engine engine, WaveData waveData){
        super(engine);
        myData = data;
        myParent = view;
        myWaveData = waveData;
    }

    /**
     * this method sets some actions to happen when the user shift clicks on a tile
     * @param node the tile as a node
     * @param location the location(x,y in pixels) of the tile
     * @param startStyle the filePath of the image for the start point
     * @param endStyle the filePath of the image for the end point
     */
    public void addNode(Node node, Point2D location, String startStyle, String endStyle){
        node.setOnMouseClicked(event -> {
            if (event.isShiftDown() && myData.containsTileImage(location)){
                if (!myData.containsEndPoint()){
                    myParent.setTileImage(endStyle,location);
                    myData.setEnd(location);
                }
                else{
                    myParent.setTileImage(startStyle,location);
                    myData.setStart(location);
                }
            }
            else if(!event.isShiftDown() && myData.isStartPoint(location)){
                PopUpWaves waves = new PopUpWaves(super.getEngine(),400, location, myWaveData);
                waves.display(waves.startVisualization());
            }
        });
    }


}
