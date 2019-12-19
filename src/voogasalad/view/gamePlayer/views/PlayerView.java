package voogasalad.view.gamePlayer.views;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import voogasalad.controller.GameLoop;
import voogasalad.gameEngine.Engine;
import voogasalad.view.SoundEffect;
import voogasalad.view.View;
import voogasalad.view.authoringEnvironment.controllers.BackController;
import voogasalad.view.clickableobjects.ClickableButton;
import voogasalad.view.gamePlayer.controllers.PauseGameController;
import voogasalad.view.gamePlayer.controllers.ResumeGameController;
import voogasalad.view.gamePlayer.controllers.SaveCurrentGame;
import voogasalad.view.gamePlayer.controllers.StartGameController;

import java.awt.*;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Shows all of the components of a player environment
 * @author Gabriela Rodriguez-Florido, Angel Huizar
 */
public class PlayerView extends View {
    private BorderPane myPane;
    private ResourceBundle myLanguage,myBundle;
    private final String resourcePath = "voogasalad/view/gamePlayer/resources/";
    private final String fileName = "PlayerView";
    private Stage myPrevStage;
    private PlayerGrid myGrid;
    private EnemyManager myEnemyManager;
    private ProjectileManager myProjectileManager;
    private GameLoop myGameLoop;
    private final String backgroundMusic = "resources/PlayerBackgroundMust_Retro.wav";
    private SoundEffect myBackgroundMusic = new SoundEffect(backgroundMusic);


    /**
     * Sets up a pane for all the view elements to be added to, as well as resources, and a way to return to the previous stage
     * @param prevstage
     */
    public PlayerView(Stage prevstage, Engine engine){
        super(Toolkit.getDefaultToolkit().getScreenSize().getWidth(),Toolkit.getDefaultToolkit().getScreenSize().getHeight(), Color.ROYALBLUE, "Game Player", engine);
        myPane = new BorderPane();
        myPane.setPrefSize(myWidth,myHeight);
        myLanguage = ResourceBundle.getBundle(languagePath + LANGUAGE);
        myBundle = ResourceBundle.getBundle(resourcePath + fileName);
        myPrevStage = prevstage;
    }

    /**
     * Creates all the components of a player view to be displayed
     * @return
     */
    @Override
    public Node startVisualization() {
        double gridLength = Toolkit.getDefaultToolkit().getScreenSize().getHeight() * Double.parseDouble(myBundle.getString("height"));
        double defenseSize = Double.parseDouble(myBundle.getString("defenseSize"));
        double headsUpWidth = Double.parseDouble(myBundle.getString("headsUpWidth"));
        double headsUpHeight = Double.parseDouble(myBundle.getString("headsUpHeight"));
        myGrid = new PlayerGrid(gridLength,super.getEngine());
        myPane.setLeft(myGrid.startVisualization());
        myPane.setRight(new PlayerDefenses(defenseSize,defenseSize,super.getEngine()).startVisualization());
        PlayerHeadsUpDisplay display = new PlayerHeadsUpDisplay(headsUpHeight,headsUpWidth,super.getEngine(),myLanguage.getString("headsUpTitle"));
        myPane.setTop(display.startVisualization());
        BorderPane.setAlignment(myPane.getTop(), Pos.TOP_CENTER);
        myEnemyManager = new EnemyManager(super.getEngine(),myGrid.getMyData().getImageSize());
        myProjectileManager = new ProjectileManager(super.getEngine(),myGrid.getMyData().getImageSize(),gridLength);
        myGameLoop = new GameLoop(super.getEngine(), List.of(display,myEnemyManager,myProjectileManager));
        myPane.setCenter(createButtons());
        return myPane;
    }

    private Node createButtons() {
        GridPane toReturn = createGridPane();
        toReturn.setPrefSize(myWidth * Double.parseDouble(myBundle.getString("scaleCenter")),myHeight * Double.parseDouble(myBundle.getString("scaleCenter")));
        // Button to return to main screen
        ClickableButton button = new ClickableButton(myLanguage.getString("back"), new BackController(myStage,myPrevStage,new Engine(),myBackgroundMusic,myGameLoop));
        toReturn.add(button.getNode(),0,0);

        ClickableButton button1 = new ClickableButton(myLanguage.getString("pauseGame"),new PauseGameController(super.getEngine(),myGameLoop));
        toReturn.add(button1.getNode(),0,1);

        Button button2 = new Button(myLanguage.getString("startGame"));
        toReturn.add(button2,0,2);

        button2.setOnAction(event -> {
            new StartGameController(super.getEngine(),myGameLoop).execute();
            myBackgroundMusic.startLoop();
            addToGroup(myEnemyManager.startVisualization());
            addToGroup(myProjectileManager.startVisualization());
            ClickableButton button3 = new ClickableButton(myLanguage.getString("resumeGame"),new ResumeGameController(super.getEngine(),myGameLoop));
            toReturn.add(button3.getNode(),0,2);
        });

        ClickableButton button4 = new ClickableButton(myLanguage.getString("saveGame"),new SaveCurrentGame(super.getEngine()));
        toReturn.add(button4.getNode(),0,3);

        return toReturn;
    }
}
