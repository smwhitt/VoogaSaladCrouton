package voogasalad.view.authoringEnvironment.controllers;

import javafx.scene.image.ImageView;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseImageController extends Controller {

    Map<Integer, ImageView> imageViews = new HashMap<>();
    private  boolean isActive; // If false means never selected image so error
    private int previousClick;
    private ImageView activeImage;
    private List<String> filepaths;

    public ChooseImageController(Engine engine) {
        super(engine);
        filepaths = new ArrayList<>();
    }

    @Override
    public void execute() {
    }

    public void addNode(int index, ImageView imageView, String filePath){

        imageViews.put(index,imageView);
        filepaths.add(filePath);

        imageView.setOnMouseClicked(event -> {
            if(!isActive){
                imageView.scaleXProperty().setValue(1.5);
                imageView.scaleYProperty().setValue(1.5);
                isActive = true;
                activeImage = imageView;
            }
            else if (index != previousClick){
                imageViews.get(previousClick).scaleXProperty().setValue(1);
                imageViews.get(previousClick).scaleYProperty().setValue(1);
                imageView.scaleXProperty().setValue(1.5);
                imageView.scaleYProperty().setValue(1.5);
                activeImage = imageView;
            }
            else{
                imageViews.get(previousClick).scaleXProperty().setValue(1);
                imageViews.get(previousClick).scaleYProperty().setValue(1);
                isActive = false;
//                activeImage = null; //no image selected now
            }
            previousClick = index;

        });
    }

    /**
     * Used by the parent view to deselect an image on the image selector when the user clears or saves
     */
    public void deselectImage(){
        System.out.println("DESELECTING IMAGE");
        // In the case the user didn't click on an image and it defaulted to one
        if(activeImage!=null){
            activeImage.scaleXProperty().setValue(1);
            activeImage.scaleYProperty().setValue(1);
            isActive = true;
        }
    }

    /**
     * Used by the parent view to select an image on the image selector when the user loads from the engine
     */
    public void selectImage(String filepath){
        int index = 0;
        for(String i: filepaths){
            if(i.equals(filepath)){
                imageViews.get(index).scaleXProperty().setValue(1.5);
                imageViews.get(index).scaleYProperty().setValue(1.5);
                if(index!=previousClick){
                    imageViews.get(previousClick).scaleXProperty().setValue(1);
                    imageViews.get(previousClick).scaleYProperty().setValue(1);
                }
                isActive = true;
                activeImage = imageViews.get(index);
                previousClick = index;
            }
            index++;
        }
    }

    public ImageView getImageView(){
        return imageViews.get(previousClick);
    }
}
