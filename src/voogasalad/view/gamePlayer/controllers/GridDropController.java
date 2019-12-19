package voogasalad.view.gamePlayer.controllers;

import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;
import voogasalad.view.gamePlayer.views.PlayerGrid;

/**
 * @author Angel Huizar
 * This class is responsible for handling the recieve portion of the drag and drop style of placing defenses
 */
public class GridDropController extends Controller {
    private PlayerGrid myParent;
    private UpgradeDefense myUpgrades;

    /**
     * This constructor tells the class what engine and playerGrid it will be working with
     * @param engine the engine this class will be working with
     * @param grid the playerGrid this class will be working with
     */
    public GridDropController(Engine engine, PlayerGrid grid) {
        super(engine);
        myParent = grid;
        myUpgrades = new UpgradeDefense(engine);
    }

    /**
     * This method defines the receive portion of the drag and drop style of placing for a particular node on the grid
     * @param node the node on the grid that will receive this functionality
     * @param r the row the node is in
     * @param c the column the node is in
     * @param scaleFactor the scale factor to use when the user has the ability to drop a defense into the node
     */
    public void addNode(Node node, int r, int c, double scaleFactor) {
        node.setOnDragOver(event -> {
            if (event.getGestureSource() != node && !myParent.containsTileImage(r,c) && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });
        node.setOnDragEntered(event -> {
            if (event.getGestureSource() != node && !myParent.containsTileImage(r,c) && event.getDragboard().hasString()) {
                node.setScaleX(scaleFactor);
                node.setScaleY(scaleFactor);
            }
            event.consume();
        });
        node.setOnDragExited(event -> {
            node.setScaleX(1.0);
            node.setScaleY(1.0);
        });
        node.setOnDragDropped(event -> {
            onDragDroppedAction(r, c, event);
        });
    }

    private void onDragDroppedAction(int r, int c, DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if(db.hasString()){
            int id = Integer.parseInt(db.getString().split(",")[0]);
            int ret = super.getEngine().place(id,myParent.makeLocation(r,c).getX(),myParent.makeLocation(r,c).getY(),0);
            if(id!=ret){
                myParent.setTileImage(db.getString().split(",")[1],r,c,0,true);
                myUpgrades.addNode(myParent.getMyData().getTileImage(myParent.makeLocation(r,c)),ret);
            }
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }
}
