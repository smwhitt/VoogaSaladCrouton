package voogasalad.view.clickableobjects;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import voogasalad.view.ClickableNode;

/**
 * Defines a text input with a label indicating its purpose
 * @author Robert C. Duvall
 * @author Shreya Hurli
 * @author Gabriela Rodriguez-Florido
 */

public class LabeledInput extends ClickableNode{
    private TextField myTextField;
    private Label myLabel;
    private Node myNode;

    /**
     * constructor for a labeledinput
     * @param label
     */
    public LabeledInput(String label){
        myTextField = new TextField();
        myLabel = new Label(label);
        myNode = new HBox(myLabel,myTextField);
        //actionOnTextEntry();
    }

    /**
     * gets text from a textfield as a string
     * @return string contents of a textfield
     */
    public String getText(){
        return myTextField.getText();
    }

    /**
     * sets the prompt text for the textfield
     */
    public void setPromptText(String message){
        myTextField.setPromptText(message);
    }

    /**
     * sets text of textfield in labeled input
     * @param s
     */
    public void setText(String s){
        myTextField.setText(s);
    }

    /**
     * gets label of labeled input
     * @return
     */
    public Label getLabel(){
        System.out.println(myLabel);
        return myLabel;
    }

    /**
     * returns the node object of labeled input
     * @return
     */
    @Override
    public Node getNode() {
        return myNode;
    }


    private Node actionOnTextEntry(){
        myTextField = new TextField();
        return myTextField;
    }
}
