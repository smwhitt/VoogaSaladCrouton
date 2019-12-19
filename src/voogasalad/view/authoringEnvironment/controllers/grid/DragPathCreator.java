package voogasalad.view.authoringEnvironment.controllers.grid;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;
import voogasalad.view.authoringEnvironment.views.grid.GridView;

/**
 * @author Angel Huizar
 * This class is responsible for allowing the user to click and drag to create paths on the map editor
 */
public class DragPathCreator extends Controller {
    private int myRow, myCol;
    private double x,y;
    private GridView myParent;
    private MouseEvent myMouse;
    private final String[] arrowTypes = {"down","left","right","up"};
    private final String resourcesPath = "voogasalad/view/authoringEnvironment/resources/";
    private final String arrowImagesLocation = "images/arrows/arrow_";

    /**
     * This constructor defines which GridView and engine to work with
     * @param view the gridView currently displayed to the user
     * @param engine the engine this class will be working with
     */
    public DragPathCreator(GridView view, Engine engine) {
        super(engine);
        myParent = view;
    }

    @Override
    public void execute(){
        double deltaX = myMouse.getX() - x;
        double deltaY = myMouse.getY() - y;
        if (Math.abs(deltaX) < Math.abs(deltaY)) {
            String upDown = (deltaY < 0) ? arrowTypes[3] : arrowTypes[0];
            int angle = (deltaY < 0)? 0 : 180;
            String path = System.getProperty("user.dir") + "/src/" + resourcesPath + arrowImagesLocation + upDown + ".png";
            myParent.setTileImage(path, myRow, myCol, angle,false);
        } else {
            String leftRight = (deltaX > 0) ? arrowTypes[2] : arrowTypes[1];
            int angle = (deltaX > 0)? 90 : 270;
            String path = System.getProperty("user.dir") + "/src/" + resourcesPath + arrowImagesLocation + leftRight + ".png";
            myParent.setTileImage(path, myRow, myCol, angle,false);
        }
    }

    private void setMouse(MouseEvent event) {
        myMouse = event;
    }

    private void setPosition(int finalR, int finalC, double x, double y) {
        myRow = finalR;
        myCol = finalC;
        this.x = x;
        this.y = y;
    }

    /**
     * This method sets some actions to happen when the tile is dragged on so we can create arrows when the user drags
     * over them
     * @param r the row of the tile
     * @param c the column of the tile
     * @param node the tile as a node
     */
    public void addNode(int r, int c, Node node) {
        node.setOnMousePressed(event -> {
            if(event.isSecondaryButtonDown() && !event.isShiftDown()){
                myParent.removeTileImage(r,c);
            }
        });
        node.setOnDragDetected(event -> {
            node.startFullDrag();
            setPosition(r, c, event.getX(), event.getY());
        });
        node.setOnMouseDragOver(event -> {
            setPosition(r, c,event.getX(),event.getY());
        });
        node.setOnMouseDragExited(event -> {
            if(event.isPrimaryButtonDown() && !event.isShiftDown()) {
                setMouse(event);
                execute();
            }
            else if (event.isSecondaryButtonDown() && !event.isShiftDown()){
                myParent.removeTileImage(r,c);
            }
        });
    }
}
