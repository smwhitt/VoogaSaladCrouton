package voogasalad.view.gamePlayer.views;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import voogasalad.gameEngine.Engine;
import voogasalad.gameEngine.exceptions.CouldNotFindFileException;
import voogasalad.view.View;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Angel Huizar
 * This class is responsible for displaying projectiles on the screen
 */
public class ProjectileManager extends View {
    private Map<Integer, Node> myProjectilesMap;
    private double myImageSize;
    private Group myProjectiles;
    private Rectangle myGrid;

    /**
     * This constructor makes a call to super with the engine passed in as well as the board size.
     * @param engine the engine this class will be working with
     * @param imageSize the size of the projectiles
     * @param boardSize the size of the board/screen for which projectiles can move
     */
    public ProjectileManager(Engine engine, double imageSize, double boardSize) {
        super(boardSize, boardSize, Color.WHITESMOKE, "", engine);
        myProjectiles = new Group();
        myImageSize = imageSize;
        myProjectilesMap = new HashMap<>();
        myGrid = new Rectangle(0,25,boardSize,boardSize);
    }

    /**
     * This method will be called by all views to begin displaying their contents.
     * @return a pane object to be used by a main view to display the contents of that view
     * @throws FileNotFoundException
     */
    @Override
    public Node startVisualization() {
        updateProjectiles();
        return myProjectiles;
    }

    private void updateProjectiles() {
        super.getEngine().getEntities().forEach((integer, stringStringMap) -> {
            if (stringStringMap.get(identifier).equalsIgnoreCase("projectile") && stringStringMap.containsKey("x")) {
                if (!myProjectilesMap.containsKey(integer)) {
                    try {
                        ImageView imageView = new ImageView(new Image(new FileInputStream(stringStringMap.get("image"))));
                        imageView.setFitWidth(myImageSize);
                        imageView.setFitHeight(myImageSize);
                        myProjectiles.getChildren().add(imageView);
                        imageView.setX(Double.parseDouble(stringStringMap.get("x")));
                        imageView.setY(Double.parseDouble(stringStringMap.get("y")));
                        myProjectilesMap.put(integer, imageView);
                    } catch (FileNotFoundException e) {
                        Circle circle = new Circle(Double.parseDouble(stringStringMap.get("x")), Double.parseDouble(stringStringMap.get("y")), myImageSize / 2.0, Paint.valueOf("green"));
                        myProjectilesMap.put(integer, circle);
                    }
                } else {
                    ((ImageView)myProjectilesMap.get(integer)).setX(Double.parseDouble(stringStringMap.get("x")));
                    ((ImageView)myProjectilesMap.get(integer)).setY(Double.parseDouble(stringStringMap.get("y")));
                    checkBounds(integer);
                }
            }
            else{
                myProjectiles.getChildren().remove(myProjectilesMap.get(integer));
                myProjectilesMap.remove(integer);
            }
        });
        List<Integer> todelete = new ArrayList<>();
        for (int id : myProjectilesMap.keySet()){
            if (!super.getEngine().getEntities().containsKey(id)){
                todelete.add(id);
            }
        }
        todelete.forEach(id -> myProjectiles.getChildren().remove(myProjectilesMap.remove(id)));
    }

    private void checkBounds(int ID){
        if (!myGrid.getBoundsInLocal().contains(((ImageView)myProjectilesMap.get(ID)).getX(),((ImageView)myProjectilesMap.get(ID)).getY())) {
            super.getEngine().delete(ID);
            myProjectiles.getChildren().remove(myProjectilesMap.get(ID));
            myProjectilesMap.remove(ID);
        }
    }
}
