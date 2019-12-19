package voogasalad.view.authoringEnvironment.controllers.levels;

import voogasalad.gameEngine.Engine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import voogasalad.gameEngine.components.Resource;
import voogasalad.view.Controller;
import voogasalad.view.clickableobjects.ClickableEditorEntry;

import java.util.ResourceBundle;

public class AddWaveToLevel extends Controller {

    private final String LANGUAGE = "voogasalad/view/authoringEnvironment/resources/English";

    private ClickableEditorEntry myEntry;
    private String myInfo;
    private ObservableList<String> myLevels;
    private ResourceBundle myLanguage = ResourceBundle.getBundle(LANGUAGE);
    private String myLabel;

    public AddWaveToLevel(String label, ClickableEditorEntry e, Engine engine) {
        super(engine);
        myEntry = e;
        myLevels = FXCollections.observableArrayList();
        myLabel = label;
    }

    public AddWaveToLevel(ClickableEditorEntry e, ObservableList<String> levels, Engine engine) {
        super(engine);
        myEntry = e;
        myLevels = levels;
    }

    @Override
    public void execute() {
        myEntry.addEntries(myLabel);
//        myLevels.add(myInfo);
    }


}
