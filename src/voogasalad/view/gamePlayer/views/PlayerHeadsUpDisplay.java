package voogasalad.view.gamePlayer.views;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import voogasalad.gameEngine.Engine;
import voogasalad.view.View;

import java.io.FileNotFoundException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Angel Huizar
 * This class is responsible for displaying information to the user about the game,
 * like the amount of resources they have, their lives, and what level they are on
 */

public class PlayerHeadsUpDisplay extends View {
    private final String resourcePath = "voogasalad/view/gamePlayer/resources/PlayerView";
    private ResourceBundle myLanguage, myBundle;
    private GridPane pane = new GridPane();

    /**
     * This constructor sets up a heads up display
     * @param height the height of the heads up display
     * @param width the width of the heads up display
     * @param engine the engine the display works with
     * @param title the title for the heads up display
     */
    public PlayerHeadsUpDisplay(double height, double width, Engine engine, String title) {
        super(height,width, Color.BURLYWOOD,title,engine);
        myLanguage = ResourceBundle.getBundle(languagePath + LANGUAGE);
        myBundle = ResourceBundle.getBundle(resourcePath);
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setHgap(Double.parseDouble(myBundle.getString("spacing")));
    }

    /**
     * This method will be called by all views to begin displaying their contents.
     * @return a pane object to be used by a main view to display the contents of that view
     * @throws FileNotFoundException
     */
    @Override
    public Node startVisualization() {
        createLabelBox(pane);
        return pane;
    }

    private void createLabelBox(GridPane pane){
        int numberResources = getValueFromType("Resources");
        int health = getValueFromType("Tower");
        int numEnemies = getValFromSubMap("SpawnPoint", "spawnerlimit");
        int numLevels = countInstances("Level");
        String currLevel = getCurrentLevel();

        pane.getChildren().clear();
        pane.add(new Label(String.format(myLanguage.getString("health"),health)),0,0);
        pane.add(new Label(String.format(myLanguage.getString("resources"),numberResources)),1,0);
        pane.add(new Label(String.format(myLanguage.getString("currentLevel"),currLevel,numLevels)),2,0);
        pane.add(new Label(String.format(myLanguage.getString("numberEnemiesRemaining"),numEnemies)),3,0);
    }

    private String getCurrentLevel() {
        AtomicReference<String> ret = new AtomicReference<>("");
        super.getEngine().getEntities().forEach((integer, stringStringMap) -> {
            if(Boolean.parseBoolean(stringStringMap.get("activestatus")) && stringStringMap.get(identifier).equals("Levels")){
                ret.set(stringStringMap.get("name"));
            }
        });
        return ret.get();
    }

    private int countInstances(String type) {
        AtomicInteger count = new AtomicInteger();
        super.getEngine().getEntities().forEach((integer, stringStringMap) -> {
            if (stringStringMap.get(identifier).equalsIgnoreCase(type)){
                count.getAndIncrement();
            }
        });
        return count.get();
    }

    private int getValFromSubMap(String type, String paramName) {
        AtomicInteger ret = new AtomicInteger();

        super.getEngine().getEntities().forEach((integer, stringStringMap) -> {
            if (stringStringMap.get(identifier).equalsIgnoreCase(type) && stringStringMap.get(paramName) != null) {
                ret.set(Integer.parseInt(stringStringMap.get(paramName)));
            }
        });
        return ret.get();
    }

    private int getValueFromType(String type) {
        AtomicInteger ret = new AtomicInteger();
        super.getEngine().getMetaData().forEach((key,val) -> {
            if (key.equalsIgnoreCase(type)) {
                ret.set(Integer.parseInt(val));
            }
        });
        return ret.get();
    }

}
