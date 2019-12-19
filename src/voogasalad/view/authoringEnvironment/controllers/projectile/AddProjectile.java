package voogasalad.view.authoringEnvironment.controllers.projectile;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;
import voogasalad.view.Data;
import voogasalad.view.authoringEnvironment.Editor;
import voogasalad.view.authoringEnvironment.ImageSelector;
import voogasalad.view.clickableobjects.LabeledInput;

import java.util.List;
import java.util.ResourceBundle;

public class AddProjectile extends Controller {

    private LabeledInput mySpeed, myDamage, myName, myHealth, myDuration;
    private ImageSelector myImageSelector;
    private Data myProjectileData;
    private Engine myEngine;
    private ObservableList<String> myProjectileNames;
    private Editor myEditor;
    private ChoiceBox myCollision, myAI;

    private final String PATH = "voogasalad/properties/Projectile";
    private static final String OBJECT_NAME = "Projectile";


    public AddProjectile(Data projectileData, Editor editor, LabeledInput speed, LabeledInput damage, LabeledInput duration,
                         ImageSelector imageSelector, LabeledInput name, LabeledInput health,
                         ObservableList<String> projectileNames, ChoiceBox collision,
                         ChoiceBox AI, Engine engine){
        super(engine);
        mySpeed = speed;
        myDamage = damage;
        myImageSelector = imageSelector;
        myProjectileData = projectileData;
        myHealth = health;
        myName = name;
        myEngine = engine;
        myProjectileNames = projectileNames;
        myEditor = editor;
        myCollision = collision;
        myDuration = duration;
        myAI = AI;
    }

    @Override
    public void execute() {
        ResourceBundle bundle = ResourceBundle.getBundle(PATH);

        System.out.println("Set selected collision: " + myCollision.getValue());

        if(myProjectileData.save(myName.getText(), OBJECT_NAME, super.createMap(bundle.getKeys(),
                List.of(
                        (String) myAI.getValue(),
                        myDamage.getText(),
                        myDuration.getText(),
                        myHealth.getText(),
                        myImageSelector.getFileName(),
                        (String) myCollision.getValue(),
                        myName.getText(),
                        mySpeed.getText()
                )))){
            myProjectileNames.add(myName.getText()); // only adds name to drop down if the object has actually beed saved
            myEditor.clearInputs();
        }

        System.out.println("Saving projectile data... ");
    }
}
