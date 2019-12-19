package voogasalad.view.clickableobjects;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import voogasalad.view.ClickableNode;
import voogasalad.view.Controller;
import voogasalad.view.Data;
import voogasalad.view.View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.function.Consumer;
import java.util.function.Function;


/**
 * Defines an imageview that can be clicked on with an action.
 *
 * @author Gabriela Rodriguez-Florido, Samantha Whitt
 */
public class ClickableImage extends ClickableNode {

    ImageView myImageView;

    /**
     * pre-existing image
     * @param image
     * @param target
     */
    public ClickableImage(ImageView image, Controller target) {
        myImageView = image;
        super.onClickAction(image, target);
    }

    public ClickableImage(int id, ImageView imageView, Consumer<Integer> load) {
        myImageView = imageView;
        myImageView.setOnMouseClicked(e -> load.accept(id));
    }

    @Override
    public Node getNode(){
        return myImageView;
    }
}
