package voogasalad.view.gamePlayer.controllers;

import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;
import voogasalad.view.SoundEffect;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Angel Huizar
 * This class is responsible for handling the drag portion of the drag and drop style of placing defenses
 */
public class DragDropDefenses extends Controller {
    private Map<ImageView,String> myMappedImages;
    private Map<ImageView,Integer> myImagesMappedToIDs;
    private final String soundLocation = "resources/PlaceDefense.wav";
    private SoundEffect mySoundEffect;

    /**
     * This constructor tells the class what engine it will be working with
     * @param engine the engine this class will be working with
     */
    public DragDropDefenses(Engine engine){
        super(engine);
        myMappedImages = new HashMap<>();
        myImagesMappedToIDs = new HashMap<>();
        mySoundEffect = new SoundEffect(soundLocation);
    }

    /**
     * This method actually implements the drag portion of the drag and drop style of placing defenses
     * @param test the node for which we want to add this functionality to
     * @param path the file path of the image of this node
     * @param textOnHover text to display when the user hovers over this node
     * @param id the id of this entity from the engine
     */
    public void addNode(Node test, String path, String textOnHover, int id) {
        myMappedImages.put((ImageView) test,path);
        myImagesMappedToIDs.put((ImageView) test,id);
        Tooltip.install(test,new Tooltip(textOnHover));
        test.setOnDragDetected(event -> {
            Dragboard db = test.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            String toPass = id + "," + path;
            content.putString(toPass);
            db.setContent(content);
            event.consume();
        });
        test.setOnDragDone(event -> {
            if(event.getTransferMode() == TransferMode.MOVE) {
                mySoundEffect.play();
            }
            event.consume();
        });
    }
}
