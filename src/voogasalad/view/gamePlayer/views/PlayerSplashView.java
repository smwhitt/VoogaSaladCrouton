package voogasalad.view.gamePlayer.views;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Controller;
import voogasalad.view.View;
import voogasalad.view.authoringEnvironment.controllers.BackController;
import voogasalad.view.clickableobjects.ClickableButton;
import voogasalad.view.gamePlayer.controllers.LoadGame;
import java.awt.*;


import java.util.ResourceBundle;

public class PlayerSplashView extends View {
    private BorderPane myPane;
    private GridPane myGrid;
    private ResourceBundle myLanguage, myBundle;
    private Stage myPrevStage;
    private final String resourcePath = "voogasalad/view/gamePlayer/resources/";
    private final String fileName = "PlayerView";

    public PlayerSplashView(Stage prevstage, Engine engine){
        super(Toolkit.getDefaultToolkit().getScreenSize().getWidth(),Toolkit.getDefaultToolkit().getScreenSize().getHeight(),
                Color.ROYALBLUE, "Game Player", engine);
        myBundle = ResourceBundle.getBundle(resourcePath + fileName);
        myLanguage = ResourceBundle.getBundle(languagePath + LANGUAGE);
        myPane = new BorderPane();
        myGrid = new GridPane();
        myPrevStage = prevstage;

    }

    @Override
    public Node startVisualization(){
        myPane.getChildren().add(setupSplash("temp", "supertemp"));

        return myPane;
    }

    public Node setupSplash(String title, String instructions){
        ClickableButton backButton = new ClickableButton(myLanguage.getString("back"), new BackController(myStage, myPrevStage, super.getEngine()));
        myGrid.add(backButton.getNode(), 0, 0);

        ClickableButton toGame = new ClickableButton("play game", new LoadGame(myStage,super.getEngine()));
        myGrid.add(toGame.getNode(), 0, 0);

        Label gameTitle = new Label(title);
        myGrid.add(gameTitle, 0, 2);

        Label gameInstr = new Label(instructions);
        myGrid.add(gameInstr, 0, 3);

        return myGrid;
    }

}
