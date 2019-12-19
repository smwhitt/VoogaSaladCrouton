package voogasalad.view.gamePlayer.views;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import voogasalad.gameEngine.Engine;
import voogasalad.gameEngine.exceptions.CouldNotFindFileException;
import voogasalad.view.View;
import voogasalad.view.gamePlayer.controllers.DragDropDefenses;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;

/**
 * @author Angel Huizar
 * This class is responsible for displaying the defenses on the side menu to the user
 */
public class PlayerDefenses extends View {
    private ScrollPane myPane;
    private final String resourcePath = "voogasalad/view/gamePlayer/resources/";
    private final String fileName = "PlayerDefenses";
    private DragDropDefenses myController;

    /**
     * This is the main constructor for the view
     * @param width the width of the display
     * @param height the height of the display
     * @param engine the engine this view will be working with
     */
    public PlayerDefenses(double width, double height, Engine engine){
        super(width,height, Color.BEIGE,"Defenses",engine);
        myPane = new ScrollPane();
        myPane.setPrefSize(myWidth,myHeight);
        myPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        myPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        myController = new DragDropDefenses(engine);
    }
    /**
     * This method will be called by all views to begin displaying their contents.
     * @return a pane object to be used by a main view to display the contents of that view
     * @throws FileNotFoundException
     */
    @Override
    public Node startVisualization() {
        VBox box = new VBox();
        setUpDefenses(box);
        myPane.setContent(box);
        return myPane;
    }

    private void setUpDefenses(VBox box) {
        double size = Double.parseDouble(ResourceBundle.getBundle(resourcePath + fileName).getString("imageSize"));
        super.getEngine().getEntities().forEach((integer, stringStringMap) -> {
            if(stringStringMap.get("type").equalsIgnoreCase("defense") && !Boolean.parseBoolean(stringStringMap.get("activestatus"))){
                try {
                    ImageView image = new ImageView(new Image(new FileInputStream(stringStringMap.get("image"))));
                    image.setFitHeight(size);
                    image.setFitWidth(size);
                    box.getChildren().add(image);
                    myController.addNode(image,stringStringMap.get("image"),ResourceBundle.getBundle(languagePath + LANGUAGE).getString("playerDefenseHover"),integer);
                } catch (FileNotFoundException e) {
                    throw new CouldNotFindFileException(" ");
                }
            }
        });
    }
}
