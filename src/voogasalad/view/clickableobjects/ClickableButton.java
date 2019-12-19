package voogasalad.view.clickableobjects;

import javafx.scene.Node;
import javafx.scene.control.Button;
import voogasalad.view.ClickableNode;
import voogasalad.view.Controller;

/**
 * Creates a button with a defined behavior on click and some text for its label
 *
 * @author Gabriela Rodriguez-Florido
 */
public class ClickableButton extends ClickableNode {

    Button myButton;

    /**
     * Creates the button with a lavel and an action
     * @param buttonLabel is the label for the button
     * @param target controller that defines the action that occurs when the button is clicked
     */
    public ClickableButton(String buttonLabel, Controller target){
        myButton = createButton(buttonLabel,target);
    }

    /**
     * Allows the class that created the button to get it in order to display it
     * @return button
     */
    @Override
    public Node getNode() {
        return myButton;
    }

    private Button createButton(String buttonLabel, Controller target){
        Button button = new Button();
        button.setText(buttonLabel);
        super.onClickAction(button,target);
        return button;
    }



}
