package voogasalad.view.authoringEnvironment;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;
import voogasalad.view.Data;
import voogasalad.view.View;
import voogasalad.view.authoringEnvironment.controllers.defenses.EditDefense;
import voogasalad.view.clickableobjects.ClickableImage;
import voogasalad.view.exceptions.FileException;

import java.io.FileInputStream;
import java.util.Map;

/**
 * This class displays the images for objects created by the author
 * @author Samantha Whitt
 */
public class EditorSelector {
    private ScrollPane editorScroll;
    private View myView;
    private HBox clickableEntities;

    public EditorSelector(int width, int height, View view) {
        editorScroll = new ScrollPane();
        editorScroll.setMaxWidth(width);
        editorScroll.setMaxHeight(height);
        myView = view;
        clickableEntities = new HBox();

    }

    public void createContent(int id, Controller data) {
        Data myData = (Data) data;
        Engine myEngine = data.getEngine();

        Map<String, String> entity = myEngine.getEntity(id);
        String filePath = entity.get("image");
        Image image = null;
        try {
            image = new Image(new FileInputStream(filePath));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new FileException("Could not find image with given file path!");
        }
        ImageView images = new ImageView(image);
        images.setFitHeight(100);
        images.setFitWidth(100);
        ClickableImage clickImage = new ClickableImage(id, images, ID -> myData.load(id));

//        ClickableImage clickImage = new ClickableImage(images, new EditDefense(id, myData, myView));

        clickableEntities.getChildren().add(clickImage.getNode());
        editorScroll.setContent(clickableEntities);
    }

    public Node getNode() {
       return editorScroll;
    }
}
