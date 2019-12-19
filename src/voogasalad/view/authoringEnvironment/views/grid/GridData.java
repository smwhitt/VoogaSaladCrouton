package voogasalad.view.authoringEnvironment.views.grid;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import voogasalad.gameEngine.Engine;
import voogasalad.gameEngine.exceptions.TileImageNotFoundException;
import voogasalad.view.Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

/**
 * @author Angel Huizar
 * This class is responsible for holding the data the user creates when the user the map editor or we are loading
 * up a file
 */
public class GridData extends Controller {
    private Map<Point2D, ImageView> myPathMap; //just the points with images in them
    private String pathResource = "voogasalad/properties/Path";
    private String mapResource = "voogasalad/properties/Map";
    private Map<String, Integer> myNameMappedToId;
    private Map<Point2D,Map<String,String>> myGrid; //each point with its properties
    private Map<String,List<Integer>> myMappedIds; // the name of a grid mapped a list of tiles' ids
    private Map<String, Map<String,String>> myNameMappedToProperties; // name of a grid mapped to its properties (ids of tiles)
    private Map<String,Map<Point2D,Map<String,String>>> myMaps; //the name of a user created gird mapped to its grid states
    private ObservableList<String> myMapNames = FXCollections.observableArrayList();
    private Dimension2D myRectangleDimensions;
    private int myRows, myCols;
    private Function<Integer,Double> myColTrans,myRowTrans;


    /**
     * This constructor sets up the grid data with defined rows and columns, how to transform rows into an x-position (in pixels),
     * how to transform columns into a y-position (in pixels), the height and width of the tiles in the grid, and an engine to work with
     * @param rows the rows in the grid
     * @param columns the columns in the grid
     * @param rowToYPos how to transform rows to a y position in pixels
     * @param colToXPos how to transform columns to a x position in pixels
     * @param rectangleDimension the height and with of the tiles
     * @param engine the engine this class will be working with
     */
    public GridData(int rows, int columns, Function<Integer,Double> rowToYPos, Function<Integer,Double> colToXPos, Dimension2D rectangleDimension, Engine engine){
        super(engine);
        myGrid = new HashMap<>();
        myPathMap = new HashMap<>();
        myMappedIds = new HashMap<>();
        myNameMappedToProperties = new HashMap<>();
        myMaps = new HashMap<>();
        myRectangleDimensions = rectangleDimension;
        myRowTrans = rowToYPos;
        myColTrans = colToXPos;
        myRows = rows;
        myCols = columns;
        setUpDefaultGrid(rows,columns);
        myNameMappedToId = new HashMap<>();
    }

    /**
     * This method saves the name of a map to all the tiles in that grid
     * @param mapName the name of the map
     */
    public void saveMap(String mapName){
        if(myMapNames.contains(mapName)){
            myMappedIds.get(mapName).forEach(integer -> super.getEngine().delete(integer));
        }
        else{
            setUpMap(mapName);
        }
        myMappedIds.put(mapName,new ArrayList<>());
        myGrid.keySet().forEach(point2D -> {
            myMappedIds.get(mapName).add(super.getEngine().create("Path", myGrid.get(point2D)));
        });
        myNameMappedToProperties.get(mapName).put("paths",myMappedIds.get(mapName).toString().replace("[","").replace("]","").replace(" ",""));
        if(myNameMappedToId.containsKey(mapName)){
            super.getEngine().edit(myNameMappedToId.get(mapName),myNameMappedToProperties.get(mapName));
        }
        else {
            myNameMappedToId.put(mapName, super.getEngine().create("Map", myNameMappedToProperties.get(mapName)));
        }
        myMapNames.remove(mapName);
        myMapNames.add(mapName);
        myMaps.put(mapName,myGrid);
    }

    private void setUpMap(String mapName) {
        myNameMappedToProperties.put(mapName,new HashMap<>());
        ResourceBundle.getBundle(mapResource).keySet().forEach(s -> {
            myNameMappedToProperties.get(mapName).put("paths","");
        });
    }

    void setTileImage(String imagePath, Point2D location, Group whereToAdd){
        ImageView image = createImageView(imagePath,location);
        putInImage(location,whereToAdd,image);
        myGrid.get(location).put("image",imagePath);
        myGrid.get(location).put("path","accessible");

    }

    void setTileImage(String imagePath, Point2D location, int angle, Group whereToAdd, boolean calledFromPlayer){
        ImageView image;
        image = createImageView(imagePath, location);
        if(!calledFromPlayer) {
            myGrid.get(location).put("image", imagePath);
            myGrid.get(location).put("path", "accessible");
            myGrid.get(location).put("angle", String.valueOf(angle));
        }
        putInImage(location, whereToAdd, image);
    }

    private void putInImage(Point2D location, Group whereToAdd, ImageView image) {
        if (myPathMap.containsKey(location)) {//means there's already an image in this location
            whereToAdd.getChildren().remove(myPathMap.get(location));
        }
        myPathMap.put(location, image);
        whereToAdd.getChildren().add(image);
    }

    private ImageView createImageView(String imagePath, Point2D location) {
        ImageView ret = null;
        try {
            ret = new ImageView(new Image(new FileInputStream(imagePath)));
            setUpImage(ret,location);
        }
        catch (FileNotFoundException e){
            throw new TileImageNotFoundException(" ");
        }
        return ret;
    }

    void removeTileImage(Point2D location, Group whereToRemove) {
        whereToRemove.getChildren().remove(myPathMap.get(location));
        myPathMap.remove(location);
        myGrid.get(location).put("image","null");
        myGrid.get(location).put("path","inaccessible");
        myGrid.get(location).put("angle","-1");
    }

    /**
     * This method tiles you if a tile has an image
     * @param location the location of the tile, the x-position being the tile's x-position in pixels, and same for the
     *                 y-position; the x-position should be first in the point, then they y-position
     * @return true if the tile contains an image; false otherwise
     */
    public boolean containsTileImage(Point2D location) {
        return myPathMap.containsKey(location);
    }

    /**
     * This method sets a tile as a start point given it has an image in it already
     * @param location the location of the tile, the x-position being the tile's x-position in pixels, and same for the
     *                 y-position; the x-position should be first in the point, then they y-position
     */
    public void setStart(Point2D location) {
        myGrid.get(location).put("path","start");
    }

    /**
     * This method sets a tile as an end point given it has an image in it already
     * @param location the location of the tile, the x-position being the tile's x-position in pixels, and same for the
     *                 y-position; the x-position should be first in the point, then they y-position
     */
    public void setEnd(Point2D location) {
        myGrid.get(location).put("path","end");
    }

    void setGridSize(int rows, int columns, Dimension2D rectangleDimension) {
        myGrid = new HashMap<>();
        myPathMap = new HashMap<>();
        myRectangleDimensions = rectangleDimension;
        setUpDefaultGrid(rows,columns);
    }

    private void setUpDefaultGrid(int rows, int columns) {
        for(int r = 0; r < rows; r++){
            for (int c = 0; c < columns; c++){
                Point2D location = new Point2D(myColTrans.apply(c),myRowTrans.apply(r));
                myGrid.put(location,new HashMap<>());
                ResourceBundle.getBundle(pathResource).keySet().stream()
                        .forEach(s -> {
                            if (s.equals("x")){
                                myGrid.get(location).put(s, String.valueOf(location.getX()));
                            }
                            else if(s.equals("y")){
                                myGrid.get(location).put(s,String.valueOf(location.getY()));
                            }
                            else if(s.equals("width")){
                                myGrid.get(location).put(s,String.valueOf(myRectangleDimensions.getWidth()));
                            }
                            else if(s.equals("height")){
                                myGrid.get(location).put(s,String.valueOf(myRectangleDimensions.getHeight()));
                            }
                            else if(s.equals("path")){
                                myGrid.get(location).put(s,"inaccessible");
                            }
                            else if(s.equals("image")){
                                myGrid.get(location).put(s,"null");
                            }
                            else {
                                myGrid.get(location).put(s,"-1");
                            }
                        });
            }
        }

    }

    private void setUpImage(ImageView image, Point2D location) {
        image.setX(location.getX());
        image.setY(location.getY());
        image.setFitHeight(myRectangleDimensions.getHeight());
        image.setFitWidth(myRectangleDimensions.getWidth());
        image.setDisable(true);
    }

    /**
     * This method returns an unmodifiable version of the the observable list that contains the names of all
     * the maps' names
     * @return an unmodifiable version of the list that contains the names of all the maps
     */
    public ObservableList<String> getMapNames() {
        return FXCollections.unmodifiableObservableList(myMapNames);
    }


    /**
     * This method gets the grid data for a particular map; the name is the key, and the values are a map of points(x,y in pixels)
     * as keys and the components of those points as
     * a map of strings (key is the component type, value is the value of the component)
     * @return an unmodifiable map of points to a map of strings
     */
    public Map<Point2D, Map<String, String>> getGridStateFromName(String value) {
        return Collections.unmodifiableMap(myMaps.get(value));
    }

    /**
     * This method returns the id associated with a given map name
     * @param name the name of the map in question
     * @return the id of the map
     */
    public int getIDForName(Object name){
        return myNameMappedToId.get((String)name);
    }

    /**
     * This method deletes a map
     * @param text the name of the map to delete
     */
    public void deleteMap(String text) {
        myMapNames.remove(text);
        myMaps.remove(text);
    }

    /**
     * This method returns the size of the tiles so other images can be fit to be the same size
     * @return the size of the tiles (in pixels)
     */
    public double getImageSize() {
        return myRectangleDimensions.getHeight();
    }

    /**
     * This method tells you if a point on the grid is a start point
     * @param location the location of the tile (in pixels)
     * @return true if the point is a start point, false otherwise
     */
    public boolean isStartPoint(Point2D location) {
        return myGrid.containsKey(location) && myGrid.get(location).get("path").equals("start");
    }

    /**
     * This method tells you if the grid contains an end point on the grid
     * @return true if there is an end point on the grid, false otherwise
     */
    public boolean containsEndPoint() {
        AtomicBoolean toReturn = new AtomicBoolean(false);
        myGrid.forEach((point2D, stringStringMap) -> {
            if (stringStringMap.containsValue("end")){
                toReturn.set(true);
            }
        });
        return toReturn.get();
    }

    /**
     * This method returns the imageView (if any) from a tile on the grid
     * @param location the location of the tile (in pixels)
     * @return the imageView in that tile (if any)
     */
    public ImageView getTileImage(Point2D location){
        ImageView imageView = myPathMap.get(location);
        imageView.setDisable(false);
        Tooltip.install(imageView, new Tooltip("Click to upgrade!"));
        return imageView;
    }
}
