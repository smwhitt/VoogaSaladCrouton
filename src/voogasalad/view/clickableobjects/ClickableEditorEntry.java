package voogasalad.view.clickableobjects;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import voogasalad.view.ClickableNode;
import java.util.ArrayList;
import java.util.List;

/**
 * This creates a single entry that will be a part of a list of objects with a name and a drop down to associate that
 * name with a previously created object in the game.
 *
 * An example use would be to add waves to a level.
 *
 * @author Gabriela Rodriguez-Florido, Angel Huizar, Shreya Hurley
 */
public class ClickableEditorEntry extends ClickableNode {

    private VBox myColumn;
    private int myCurrEntry;
    private ObservableList<String> myDropDownEntries;
    private List<ChoiceBox> myChoiceBoxes = new ArrayList<>();
    private List<Entry> myEntries = new ArrayList<>();

    /**
     * @param label name for the entry that will tell the author what they are adding
     * @param waveNames list of created waves to display as choices for the user
     */
    public ClickableEditorEntry(String label, ObservableList<String> waveNames){
        myCurrEntry = 0;
        myDropDownEntries = waveNames;
        // Needs to be called after initializing above params
        myColumn = this.createChoiceBoxLabel(label);
    }

    /**
     *
     * @param label
     * @param numEnemiesMessage
     * @param spawnFrequencyMessage
     * @param enemyNames
     */
    public ClickableEditorEntry(String label, String numEnemiesMessage, String spawnFrequencyMessage,
                                ObservableList<String> enemyNames){
        myCurrEntry = 0;
        myDropDownEntries = enemyNames;
        // Needs to be called after initializing above params
        myColumn = this.createChoiceBoxLabel(label, numEnemiesMessage, spawnFrequencyMessage);
    }

    /**
     * Allows the displaying view to get the VBox that contains all the starting entries
     * @return vbox containing starting entries, will be expanded when the user adds more
     */
    @Override
    public Node getNode(){
        return myColumn;
    }

    /**
     * Allows a controller to extract the enemy count for a wave that they inputted in text user input 2
     * @return
     */
    public LabeledInput getEnemyCount(int i){
        return myEntries.get(i).getEnemyCount();
    }

    /**
     * Allows a controller to extract the enemy frequency that the user inputted in text user input 1
     * @return enemy frequency
     */
    public LabeledInput getEnemyFrequency(int i){
        return myEntries.get(i).getEnemyFrequency();
    }

    /**
     * Allows a controller to get the total number of entries currently added by the user, likely to iterate over
     * all of them ot extract all the values inputted by the user.
     * @return list of the entries created
     */
    public List<Entry> getEntries(){
        return myEntries;
    }

    /**
     * Allows the author to add entries to the list. This one is for if they want to add more levels to a game, which
     * has more fields than adding a wave to a level.
     * @param label
     * @param numEnemiesMessage
     * @param spawnFrequencyMessage
     */
    public void addEntries(String label, String numEnemiesMessage, String spawnFrequencyMessage){
        myCurrEntry++;
        myColumn.getChildren().add(createChoiceBoxLabel(label, numEnemiesMessage, spawnFrequencyMessage));
    }

    /**
     * Allows the author to add entries to the list. This one is for if they want to add more waves to a level, which
     * has more fields than adding a wave to a level.
     * @param label
     */
    public void addEntries(String label){
        myCurrEntry++;
        myColumn.getChildren().add(createChoiceBoxLabel(label));
    }

    /**
     * Allows the author to change their mind and remove a level or wave from the game/level.
     */
    public void deleteEntry(){
        if(myCurrEntry != 0){
            myColumn.getChildren().remove(myCurrEntry);
            myCurrEntry--;
        }
    }

    /**
     * Allows a controller to access what field the author selected for each choice box in an entry.
     * @return choiceboxes
     */
    public List<ChoiceBox> getChoiceBoxes(){
        return myChoiceBoxes;
    }

    private VBox createChoiceBoxLabel(String label, String promptEnemies, String promptFrequency){

        ChoiceBox<String> waveChoices = new ChoiceBox<>(myDropDownEntries);

        myChoiceBoxes.add(waveChoices);

        Entry myEntry = new Entry(myCurrEntry, label, promptEnemies, promptFrequency);
        myEntries.add(myEntry);

        Label levelLabel = myEntry.getLevelLabel();
        levelLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        GridPane holder = new GridPane();
        holder.setHgap(10);
        holder.add(levelLabel,0,0);
        holder.add(myEntry.getEntry(),1,0);
        holder.add(waveChoices,2,0);

        VBox myBox = new VBox(holder);

        return myBox;
    }

    private VBox createChoiceBoxLabel(String label){

        ChoiceBox<String> waveChoices = new ChoiceBox<>(myDropDownEntries);

        myChoiceBoxes.add(waveChoices);

        Entry myEntry = new Entry(myCurrEntry, label);
        myEntries.add(myEntry);

        // Passing entry in so controller can modify its values
        //super.onClickAction(editButton,new OpenLevelEditor(myEntry));
        //editButton.setText(myButtonLabel);

        Label levelLabel = myEntry.getLevelLabel();
        levelLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        GridPane holder = new GridPane();
        holder.setHgap(10);
        holder.add(levelLabel,1,0);
        holder.add(waveChoices,2,0);

        VBox myBox = new VBox(holder);

        return myBox;
    }

}
