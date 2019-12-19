package voogasalad.view.clickableobjects;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Holds the values for a clickable editor entry, so that they can be grabbed by a controller to save objects to
 * the backend with these values as their parameters.
 *
 * @author Gabriela Rodriguez-Florido
 */
public class Entry {
    private Label myLevelLabel;
    private LabeledInput myEnemyFrequency;
    private LabeledInput myEnemyCount;
    private Node myRow;

    /**
     * @param currLevel to increment each time to show the user the order of their game
     * @param nameLabel name for the entry
     */
    public Entry(int currLevel, String nameLabel){
        myLevelLabel = new Label(nameLabel+" " + currLevel);
        myRow = myLevelLabel;
    }

    /**
     *
     * @param currLevel to increment each time to show the user the order of their game
     * @param nameLabel name for the entry
     * @param message1 information for user input 1
     * @param message2 information for user input 2
     */
    public Entry(int currLevel, String nameLabel, String message1, String message2){

        myLevelLabel = new Label(nameLabel+" " + currLevel);

        // Create input 1
        myEnemyCount = new LabeledInput(""); // no label
        myEnemyCount.setPromptText(message1);

        // Create input 2
        myEnemyFrequency = new LabeledInput("");
        myEnemyFrequency.setPromptText(message2);

        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.add(myLevelLabel, 0 , 0);
        pane.add(myEnemyCount.getNode(), 1 ,0);
        pane.add(myEnemyFrequency.getNode(), 2, 0);

        myRow = pane;
    }

    /**
     * Allows a controller to extract the enemy frequency that the user inputted in text user input 1
     * @return enemy frequency
     */
    public LabeledInput getEnemyFrequency(){
        return myEnemyFrequency;
    }

    /**
     * Allows a controller to extract the enemy count for a wave that they inputted in text user input 2
     * @return
     */
    public LabeledInput getEnemyCount(){
        return myEnemyCount;
    }

    /**
     * Allows the name label for each level to be changed
     * @param buttonLabel
     */
    public void changeLevelLabel(String buttonLabel){
        myLevelLabel.setText(buttonLabel);
    }

    /**
     * Allows a controller to get the entry in order to extract the user input level
     * @return
     */
    public Node getEntry(){
        return myRow;
    }

    /**
     * Allows a collection of entries to use the same name label for each level
     * @return
     */
    public Label getLevelLabel(){
        return myLevelLabel;
    }

}
