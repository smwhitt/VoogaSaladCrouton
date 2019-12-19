package voogasalad.view.authoringEnvironment.controllers.enemy;

import voogasalad.gameEngine.Engine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import voogasalad.gameEngine.components.Resource;
import voogasalad.view.Controller;
import voogasalad.view.clickableobjects.ClickableEditorEntry;

import java.util.ResourceBundle;

public class AddEnemyToWave extends Controller {

    private final String LANGUAGE = "voogasalad/view/authoringEnvironment/resources/English";

    private ClickableEditorEntry myEntry;
    private String myInfo;
    private ObservableList<String> myLevels;
    private ResourceBundle myLanguage = ResourceBundle.getBundle(LANGUAGE);
    private String myLabel, myEnemyMessage, myFrequencyMessage;

    public AddEnemyToWave(String label, String enemyMessage, String frequencyMessage,
                          ClickableEditorEntry e, Engine engine) {
        super(engine);
        myEntry = e;
        myLevels = FXCollections.observableArrayList();
        myLabel = label;
        myEnemyMessage = enemyMessage;
        myFrequencyMessage = frequencyMessage;
    }

    @Override
    public void execute() {
        myEntry.addEntries(myLabel, myEnemyMessage, myFrequencyMessage);
//        myLevels.add(myInfo);
    }


}
