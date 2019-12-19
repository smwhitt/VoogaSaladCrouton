package voogasalad.view.gamePlayer.controllers;

import javafx.scene.image.ImageView;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;

import java.util.ArrayList;
import java.util.List;

/**
<<<<<<< HEAD
 * @author Angel Huizar
 * This class is responsible for allowing the user to upgrade towers when the click on them
=======
 * This class defines what happens when a user tried to upgrade a defense (e.g. when they click on a placed defense)
 *
 * @author Gabriela Rodriguez-Florido
>>>>>>> b0305f28cac292f150f9232bc20ef1470c378651
 */
public class UpgradeDefense extends Controller {

    private List<ImageView> myImageViews = new ArrayList<>();

    /**
<<<<<<< HEAD
     * This constructor just makes a call to super
     * @param engine the engine this class will work with
=======
     * Creates the upgrade defense controller with an instance of engine, which will be the overall shared instance
     * for the player.
     * @param engine
>>>>>>> b0305f28cac292f150f9232bc20ef1470c378651
     */
    public UpgradeDefense(Engine engine){
        super(engine);
    }

    /**
<<<<<<< HEAD
     * This method set the imageview that this class needs to upgrade when clicked
     * @param imageView the image of the defense (on the grid)
     * @param id the id of the defense on the grid
=======
     * Adds an imageview (or a placed defense) to the list of nodes that can be clicked on to upgrade.
     * A list is kept in case later we want to change the image when it is upgraded.
     * @param imageView
     * @param id
>>>>>>> b0305f28cac292f150f9232bc20ef1470c378651
     */
    public void addNode(ImageView imageView, int id){
        myImageViews.add(imageView);
        imageView.setOnMouseClicked(e -> {
            super.getEngine().upgrade(id);

        });
    }
}
