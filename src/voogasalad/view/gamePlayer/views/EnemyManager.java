package voogasalad.view.gamePlayer.views;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import voogasalad.gameEngine.Engine;
import voogasalad.gameEngine.exceptions.CouldNotFindFileException;
import voogasalad.view.View;

import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Angel Huizar
 */

public class EnemyManager extends View {
    private Group myEnemies;
    private Map<Integer,ImageView> myEnemyMap;
    private double myImageSize;

    /**
     * This is the main constructor for this display
     * @param engine the engine this view will work with
     * @param imageSize the size of the enemies (in pixels)
     */
    public EnemyManager(Engine engine, double imageSize){
        super(Toolkit.getDefaultToolkit().getScreenSize().getWidth(),Toolkit.getDefaultToolkit().getScreenSize().getHeight(),Color.WHITESMOKE,"",engine);
        myEnemies = new Group();
        myImageSize = imageSize;
        myEnemyMap = new HashMap<>();
    }
    /**
     * This method will be called by all views to begin displaying their contents
     * @return a pane object to be used by a main view to display the contents of that view
     * @throws FileNotFoundException
     */
    @Override
    public Node startVisualization() {
        updateEnemies();
        return myEnemies;
    }

    private void updateEnemies() {
        super.getEngine().getEntities().forEach((integer, stringStringMap) -> {
            if(stringStringMap.get(identifier).equalsIgnoreCase("minion") && stringStringMap.containsKey("x")){
                try{
                    if(!myEnemyMap.containsKey(integer)) {
                        ImageView imageView = new ImageView(new Image(new FileInputStream(stringStringMap.get("image"))));
                        imageView.setFitWidth(myImageSize);
                        imageView.setFitHeight(myImageSize);
                        myEnemies.getChildren().add(imageView);
                        imageView.setX(Double.parseDouble(stringStringMap.get("x")));
                        imageView.setY(Double.parseDouble(stringStringMap.get("y")));
                        myEnemyMap.put(integer, imageView);
                    }
                    else {
                        myEnemyMap.get(integer).setX(Double.parseDouble(stringStringMap.get("x")));
                        myEnemyMap.get(integer).setY(Double.parseDouble(stringStringMap.get("y")));
                        if(myEnemyMap.get(integer).getX() > myWidth || myEnemyMap.get(integer).getX() < 0
                                || myEnemyMap.get(integer).getY() > myHeight || myEnemyMap.get(integer).getY() < 0){
                            super.getEngine().delete(integer);
                            myEnemies.getChildren().remove(myEnemyMap.get(integer));
                            myEnemyMap.remove(integer);
                        }
                    }
                }
                catch (FileNotFoundException e){
                    throw new CouldNotFindFileException(" ");
                }
            }
            else{
                myEnemies.getChildren().remove(myEnemyMap.get(integer));
                myEnemyMap.remove(integer);
            }
        });
        List<Integer> todelete = new ArrayList<>();
        for (int id : myEnemyMap.keySet()){
            if (!super.getEngine().getEntities().containsKey(id)){
                todelete.add(id);
            }
        }
        todelete.forEach(id -> myEnemies.getChildren().remove(myEnemyMap.remove(id)));

    }


}
