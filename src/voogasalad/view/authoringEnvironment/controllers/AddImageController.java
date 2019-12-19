package voogasalad.view.authoringEnvironment.controllers;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;
import voogasalad.view.authoringEnvironment.ImageSelector;
import voogasalad.view.exceptions.FileException;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Opens desktop files to add a new image to the image selector
 * @author Samantha Whitt
 */
public class AddImageController extends Controller {

    private Desktop desktop = Desktop.getDesktop();
    private FileChooser fileChooser = new FileChooser();
    private ImageSelector myImages;

    /**
     * sets the overall list of images from the imageselector
     * @param images
     */
    public AddImageController(ImageSelector images, Engine engine) {
        super(engine);
        myImages = images;
    }

    /**
     * opens desktop's finder/folder views
     */
    @Override
    public void execute() {
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            openFile(file);
        }
    }

    private void openFile(File file) {
        try {
            desktop.open(file);
            Image fileInput = new Image(new FileInputStream(file));
            myImages.updateImages(fileInput,file.getAbsolutePath());
        } catch (IOException ex) {
            throw new FileException("Could not find your selected file!");
        }
    }
}
