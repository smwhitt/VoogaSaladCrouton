package voogasalad.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import voogasalad.gameEngine.Engine;
import voogasalad.view.clickableobjects.ClickableButton;
import voogasalad.view.authoringEnvironment.controllers.startscreen.CreateGame;
import voogasalad.view.gamePlayer.controllers.LoadGame;

import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;

/**
 * This is the main view in the program that allows you to access the authoring environment and game player
 * @author Angel Huizar
 */

public class StartView extends View {

    public StartView(Engine engine){
        super(Toolkit.getDefaultToolkit().getScreenSize().getWidth(),Toolkit.getDefaultToolkit().getScreenSize().getHeight(), Color.WHEAT,"Start Screen",engine);
    }

    /**
     * This method will be called by all views to begin displaying their contents.
     *
     * @return a pane object to be used by a main view to display the contents of that view
     * @throws FileNotFoundException
     */
    @Override
    public Pane startVisualization() {
        GridPane pane = new GridPane();
        pane.setPrefSize(myWidth,myHeight);
        pane.setAlignment(Pos.CENTER);
        makePane(pane);
        return pane;
    }

    private void makePane(Pane pane) {
        ResourceBundle bundle = ResourceBundle.getBundle(languagePath + LANGUAGE);
        VBox box = new VBox(new Label(bundle.getString("Title")), new Label(bundle.getString("Description")));
        box.setSpacing(20.0);
        ((GridPane)pane).add(box,0,0);
        ((GridPane) pane).setVgap(20);
        ClickableButton create = new ClickableButton(bundle.getString("create"), new CreateGame(myStage,new Engine()));
        ClickableButton load = new ClickableButton(bundle.getString("load"), new LoadGame(myStage,new Engine()));
        HBox hBox = new HBox(load.getNode(),create.getNode());
        hBox.setSpacing(50.0);
        ((GridPane)pane).add(hBox,0,1);
    }
}
