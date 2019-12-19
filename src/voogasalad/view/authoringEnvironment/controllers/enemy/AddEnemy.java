package voogasalad.view.authoringEnvironment.controllers.enemy;

import javafx.collections.ObservableList;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;
import voogasalad.view.Data;
import voogasalad.view.authoringEnvironment.Editor;
import voogasalad.view.authoringEnvironment.ImageSelector;
import voogasalad.view.clickableobjects.LabeledInput;

import java.util.List;
import java.util.ResourceBundle;

/**
 * Called in the authoring environment when a user clicks "save" and creates a new type of enemy
 * @author Samantha Whitt, Gabriela Rodriguez-Florido
 */
public class AddEnemy extends Controller {

    private final String PATH = "voogasalad/properties/Minion";
    private static final String OBJECT_NAME = "Minion";

    private Data myEnemyData;
    private Editor myEditor;
    private LabeledInput myDamage, myHealth, myResource,mySpeed, myName;
    private ImageSelector myImageSelector;
    private ObservableList<String> myEnemyNames;

    public AddEnemy(Data data, Editor editor, LabeledInput damage, LabeledInput health, ImageSelector imageSelector,
                    LabeledInput title, LabeledInput speed, LabeledInput resource, ObservableList<String> enemyNames,
                    Engine engine) {
        super(engine);
        myEnemyData = data;
        myEditor = editor;
        myDamage = damage;
        myHealth = health;
        myImageSelector = imageSelector;
        myResource = resource;
        mySpeed = speed;
        myName = title;
        myEnemyNames = enemyNames;
    }


    /**
     * Calls data.save() to create a new object in the backend given the input fields in the frontend then clears the screen
     * The inputs must be in alphabetical order according to the corresponding backend properties file
     */
    @Override
    public void execute() {
        ResourceBundle bundle = ResourceBundle.getBundle(PATH);

        if(myEnemyData.save(myName.getText(), OBJECT_NAME, super.createMap(bundle.getKeys(),
                List.of(
                        myDamage.getText(),
                        myHealth.getText(),
                        myImageSelector.getFileName(),
                        myName.getText(),
                        mySpeed.getText(),
                        myResource.getText()
                )))){
            myEnemyNames.add(myName.getText());
            myEditor.clearInputs();
        }
    }
}
