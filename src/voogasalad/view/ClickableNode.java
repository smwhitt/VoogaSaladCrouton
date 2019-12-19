package voogasalad.view;

import javafx.scene.Node;

import java.util.function.Consumer;

/**
 * Creates an abstract class that defines the steps for reflection for all interactable objects in the user interface
 *
 * @author Gabriela Rodriguez-Florido
 */
public abstract class ClickableNode {

    /**
     * Defines any behavior for a clickable node to occur based on a passed in controller's defined execute method
     * @param node node whose behavior needs to be defined
     * @param target controller defining behavior for node on action
     */
    protected void onClickAction(Node node, Controller target) { node.setOnMouseClicked(handler -> target.execute());}

    /**
     * Allows any clickable node to return its node to the enclosing view so that it can be displayed.
     * @return node to be displayed
     */
    public abstract Node getNode();

}
