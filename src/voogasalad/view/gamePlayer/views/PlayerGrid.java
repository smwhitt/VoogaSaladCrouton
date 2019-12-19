package voogasalad.view.gamePlayer.views;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import voogasalad.gameEngine.Engine;
import voogasalad.view.authoringEnvironment.views.grid.GridView;
import voogasalad.view.gamePlayer.controllers.GridDropController;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Angel Huizar
 * This class is responsible for displaying the grid to the user in a game simulation
 */

public class PlayerGrid extends GridView {
    private final String resourcePath = "voogasalad/view/gamePlayer/resources/";
    private final String propertiesFile = "PlayerGrid";
    private final String tile = "Path";
    private final String image = "image";
    private int numberTiles = 0;
    private int numberOfStartPoints = 0;
    private int numberOfEndPoints = 0;
    private ResourceBundle myBundle;
    private List<Point2D> mySpawnPoints,myEndPoints;

    /**
     * This constructor is the main one used to set up the grid
     * @param size the size of the grid
     * @param engine the engine this view will be working with
     */
    public PlayerGrid(double size, Engine engine){
        super(size,engine);
        myBundle = ResourceBundle.getBundle(resourcePath + propertiesFile);
        mySpawnPoints = new ArrayList<>();
        myEndPoints = new ArrayList<>();
    }
    /**
     * This method will be called by all views to begin displaying their contents.
     * @return a pane object to be used by a main view to display the contents of that view
     * @throws FileNotFoundException
     */
    @Override
    public Node startVisualization() {
        myPane.setLeft(setUpPlayerGrid());
        return myPane;
    }

    private Node setUpPlayerGrid() {
        getTiles();
        numberTiles = (int) Math.sqrt(numberTiles);
        setGridSize(numberTiles,numberTiles);
        GridDropController dropController = new GridDropController(super.getEngine(),this);
        for (int r = 0; r < myRows; r++){
            for (int c = 0; c < myColumns; c++){
                dropController.addNode(createRectangle(r,c),r,c,Double.parseDouble(myBundle.getString("scaleFactor")));
            }
        }
        setTileImages();
        return tileHolder;
    }

    private void getTiles() {
        super.getEngine().getEntities().forEach((integer, stringStringMap) -> {
            if(stringStringMap.get(identifier).equals(tile) && Boolean.parseBoolean(stringStringMap.get("activestatus"))){
                numberTiles++;
                if(stringStringMap.get("path").equalsIgnoreCase("start")){
                    mySpawnPoints.add(new Point2D(Double.parseDouble(stringStringMap.get("x")),Double.parseDouble(stringStringMap.get("y"))));
                }
                else if(stringStringMap.get("path").equalsIgnoreCase("end")){
                    myEndPoints.add(new Point2D(Double.parseDouble(stringStringMap.get("x")),Double.parseDouble(stringStringMap.get("y"))));
                }
            }
        });
    }

    private void setTileImages() {
        super.getEngine().getEntities().forEach((integer, stringStringMap) -> {
            if(stringStringMap.get(identifier).equals(tile) && !stringStringMap.get(image).equals("null")){
                double x = Double.parseDouble(stringStringMap.get("x"));
                double y = Double.parseDouble(stringStringMap.get("y"));
                setTileImage(stringStringMap.get(image),new Point2D(x,y),0,true);
            }
            else if(stringStringMap.get(identifier).equalsIgnoreCase("defense") && Boolean.parseBoolean(stringStringMap.get("activestatus"))){
                double x = Double.parseDouble(stringStringMap.get("x"));
                double y = Double.parseDouble(stringStringMap.get("y"));
                setTileImage(stringStringMap.get(image),new Point2D(x,y),0,true);
            }
        });
    }

    private void setXYOfType(String type, List<Point2D> possiblePoints, int index) {
        super.getEngine().getEntities().forEach((integer, stringStringMap) -> {
            if (stringStringMap.get(identifier).equalsIgnoreCase(type)) {
                stringStringMap.put("x", String.valueOf(possiblePoints.get(index).getX()));
                stringStringMap.put("y", String.valueOf(possiblePoints.get(index).getY()));
                super.getEngine().edit(integer, stringStringMap);
            }
        });
    }

    private Node createRectangle(int r, int c) {
        myGrid[r][c] = new Rectangle(getXPosition(c),getYPosition(r),myRectangleWidth,myRectangleHeight);
        myGrid[r][c].setFill(myColor);
        myGrid[r][c].setId(myBundle.getString("TileCSSStyle"));
        tileHolder.getChildren().add(myGrid[r][c]);
        return myGrid[r][c];
    }
}
