package voogasalad.view.authoringEnvironment;

import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javafx.scene.text.Text;
import javafx.stage.Stage;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;
import voogasalad.view.View;

import voogasalad.view.authoringEnvironment.controllers.AddImageController;
import voogasalad.view.authoringEnvironment.controllers.CancelSaveController;
import voogasalad.view.authoringEnvironment.controllers.ChooseImageController;
import voogasalad.view.clickableobjects.ClickableButton;
import voogasalad.view.authoringEnvironment.controllers.CancelEditController;
import voogasalad.view.clickableobjects.LabeledInput;


import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * Creates an abstract class that defines some useful methods for all editors in the project, like reading images to
 * display from a directory
 *
 * @author Gabriela Rodriguez-Florido, Samantha Whitt, Shreya Hurli
 */

public abstract class Editor extends View {

    private final String IMAGE_DIRECTORY = "src/voogasalad/view/authoringEnvironment/resources/images/";
    protected final ResourceBundle ENGLISH = ResourceBundle.getBundle("voogasalad/view/authoringEnvironment/resources/English");
    protected final ResourceBundle SPACING = ResourceBundle.getBundle("voogasalad/view/authoringEnvironment/resources/EditorSpacing");

    private ImageSelector myImageSelector;
    private EditorSelector myEditorSelector;

    /**
     * This class is the parent for all other editors in the program. An editor is a tab in the authoring environment, which
     * allows the backend game objects to be created and sets their parameters, while providing a visualization of it to the user.
     * It defines a set of useful functions for each of them.
     *
     */
    public Editor(String title, Engine engine){
        super(1000, 1000, Color.SEASHELL, title, engine);
    }

    protected abstract Pane setupPane();
    protected abstract Pane setupInputs();
    public abstract void clearInputs();
    public abstract void updateInputs(Collection<String> inputsAlphabetically);
    public abstract GridPane getPane();
    public abstract List<LabeledInput> getLabeledInputs();
    public abstract Editor getSubEditor();
    public abstract List<ChoiceBox> getChoiceBoxes();
    public abstract Map<String, Integer> getIDMap();

    /**
     * Provides a general editor with an editor selector if that editor needs it, so that it can later be grabbed by a
     * parent view, or controller to add entries to the editor selector.
     * @param editorSelector
     * @return editorselector
     */
    public EditorSelector createEditorSelector( EditorSelector editorSelector){
        myEditorSelector = editorSelector;
        return myEditorSelector;
    }

    /**
     * If an editor needs an image to be associated with it, it will need to create an imageselector, which allows the game
     * author to choose an image for the sprite they are creating.
     * @param vertical whether the scrollpane containing the images will be vertical or horizontal
     * @param pane_width how wide the scrollpane should be
     * @param pane_height how tall the scrollpane should be
     * @param image_width how wide each image should be
     * @param image_height how tall each image should be
     * @param path path for the image
     * @param target controller to define what happens to the image when it is clicked on
     * @return
     */
    private ImageSelector createClickableImageSelector(boolean vertical, int pane_width, int pane_height, int image_width, int image_height, String path, Controller target){
        return new ImageSelector(vertical, pane_width, pane_height, image_width, image_height, IMAGE_DIRECTORY+path, target);
    }

    public Pane setupImageSelector(int width, int height, int pic_width, int pic_height, String path) {
        GridPane imageSelector = new GridPane();
        Controller chooseImage = new ChooseImageController(super.getEngine());
        myImageSelector = createClickableImageSelector(true, width, height, pic_width, pic_height, path, chooseImage);
        imageSelector.add(myImageSelector.getPane(), 0, 0);
        ClickableButton addNewImageButton = new ClickableButton("Add New Image",
                new AddImageController(myImageSelector,super.getEngine()));
        imageSelector.add(addNewImageButton.getNode(), 0 ,1);
        return imageSelector;
    }

    public ImageSelector getImageSelector(){
        return myImageSelector;
    }


    public Node createSaveNCancel(int spacing, String saveName, Controller save, String toolSave, String deleteName,
                                  Controller delete, String toolDelete) {
        GridPane holder = new GridPane();
        holder.setHgap(10);
        // Save Button
        ClickableButton saveButton = new ClickableButton(saveName, save);
        Tooltip.install(saveButton.getNode(),new Tooltip(toolSave));
        // Cancel Button
        ClickableButton cancelButton = new ClickableButton(deleteName, delete);
        Tooltip.install(cancelButton.getNode(),new Tooltip(toolDelete));
        holder.add(saveButton.getNode(),1,0);
        holder.add(cancelButton.getNode(), spacing,0);

        return holder;
    }

    public Node instructionText(String instructionKey){
        Label instructions = new Label();
        instructions.setText(ENGLISH.getString(instructionKey));
        return instructions;
    }

    public void clearLabeledTexts(List<LabeledInput> inputs) {
        for (LabeledInput input: inputs) {
            input.setText("");
        }
    }

    /**
     * If the editor has an editor selector, it can get it via this method
     * @return
     */
    public EditorSelector getEditorSelector() {
        return myEditorSelector;
    }

    /**
     * Creates a standard sized vbox for all the editors to have the same spacing
     * @return vbox
     */
    public VBox createVBox() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        return vbox;
    }

    /**
     * Allows editors to return their save and cancel buttons so that their functionality can be changed to edit rather
     * than create
     * @return save and cancel buttons
     */
    public abstract Node getSaveNCancel();

    /**
     * Allows an editor with an editor selector (defense, projectile, enemy), or in theory anything in the editor,
     * to be placed into a border pane
     * @param inputPane the pane to be placed in the center
     * @param editorSelector the node to be placed in the bottom
     * @return save and cancel buttons
     */
    public Pane wrapInBorder(Pane inputPane, Node editorSelector) {
        BorderPane pane = new BorderPane();
        pane.setCenter(inputPane);
        pane.setBottom(editorSelector);
        return pane;
    }

}
