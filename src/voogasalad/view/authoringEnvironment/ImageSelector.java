package voogasalad.view.authoringEnvironment;

import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import voogasalad.view.Controller;
import voogasalad.view.authoringEnvironment.controllers.ChooseImageController;
import voogasalad.view.clickableobjects.ClickableImage;
import voogasalad.view.exceptions.FileException;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Creates a class that finds .png files in a directory and adds them to a scroll pane. Will be used by editors.
 *
 * @author Gabriela Rodriguez-Florido, Samantha Whitt
 */

public class ImageSelector {
    private Pane myImages;
    private File myFolder;
    int myWidth, myHeight, myPicWidth, myPicHeight;
    private Controller myTarget;
    int myIndex = 0;
    private Map <ImageView, String> imageViewPaths = new HashMap<>();

    /**
     * Intializes the parameters used to create a scrollpane full of loaded images
     * @param vertical
     * @param pane_width
     * @param pane_height
     * @param pic_width
     * @param pic_height
     * @param imageDirectory
     * @param target defines the action that occurs when an image is clicked on in the scrollpane
     */
    public ImageSelector(boolean vertical, int pane_width, int pane_height, int pic_width, int pic_height, String imageDirectory, Controller target){
        myTarget = target;
        myWidth = pane_width;
        myHeight = pane_height;
        myPicWidth = pic_width;
        myPicHeight = pic_height;
        myFolder = new File(imageDirectory);
        myImages = getImages(vertical);
    }

    /**
     * Allows the view trying to display the pane of images to show it
     * @return scroll pane of clickable images
     */
    public ScrollPane getPane(){
        ScrollPane pane = new ScrollPane();
        pane.setMaxWidth(myWidth);
        pane.setMaxHeight(myHeight);
        pane.setContent(myImages);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setFitToWidth(true);
        return pane;
    }

    /**
     * updates scroll pane with new clickable image
     * @param newImage
     * @return updated pane
     */
    public ScrollPane updateImages(Image newImage, String filePath) {
        myImages.getChildren().add(createClickableImage(newImage,myIndex,filePath).getNode());
        System.out.println("Adding file name " + filePath + " to the image selector");
        return getPane();
    }

    /**
     * allows the backend to receive the file name for the current selected image that the user has selected.
     * @return current selected image file name
     */
    public String getFileName(){
        return imageViewPaths.get(((ChooseImageController)myTarget).getImageView());
    }

    /**
     * allows the backend to receive the file name for the current selected image that the user has selected.
     * @return current selected image file name
     */
    public void deselectImage(){
        ((ChooseImageController)myTarget).deselectImage();
    }

    /**
     * allows the backend to receive the file name for the current selected image that the user has selected.
     * @return current selected image file name
     */
    public void selectImage(String filePath){
        ((ChooseImageController)myTarget).selectImage(filePath);
    }

    private Pane getImages(boolean vertical){
        File[] listOfFiles = myFolder.listFiles();
        Pane bundle;
        bundle = vertical ? new VBox() : new HBox();
        try {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    if(listOfFiles[i].getName().substring(listOfFiles[i].getName().length()-3).equals("png")){
                        Image image = new Image(new FileInputStream(listOfFiles[i].getAbsolutePath()));
                        bundle.getChildren().add(createClickableImage(image, i, listOfFiles[i].getAbsolutePath()).getNode());
                    }
                }
            }
            return bundle;
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new FileException("Could not find any .png files in view/images!");
        }
    }

    private ClickableImage createClickableImage(Image image, int index, String filePath) {

        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(myPicWidth);
        imageView.setFitHeight(myPicHeight);
        ClickableImage clickImage = new ClickableImage(imageView, myTarget);

        ((ChooseImageController)myTarget).addNode(index,(ImageView) clickImage.getNode(), filePath);
        imageViewPaths.put((ImageView) clickImage.getNode(),filePath);

        myIndex++;

        return clickImage;
    }
}
