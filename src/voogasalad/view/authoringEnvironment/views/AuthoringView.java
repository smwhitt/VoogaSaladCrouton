package voogasalad.view.authoringEnvironment.views;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import voogasalad.gameEngine.Engine;
import voogasalad.view.View;
import voogasalad.view.authoringEnvironment.controllers.BackController;
import voogasalad.view.authoringEnvironment.editors.*;
import voogasalad.view.authoringEnvironment.views.grid.GridView;

import java.awt.*;
import java.util.ResourceBundle;

/**
 * Creates a view that contains all the elements of the authoring environment
 *
 * @author Gabriela Rodriguez-Florido, Angel Huizar, Samantha Whitt
 */
public class AuthoringView extends View {
    private final String RESOURCE_PATH = "voogasalad/view/authoringEnvironment/resources/";
    private final String LANGUAGE = "English";
    private final String DEFENSE = "DefenseTitle", ENEMY = "EnemyTitle", GAME = "GameTitle", LEVEL = "LevelTitle",
        PROJECTILE = "ProjectileTitle", WAVE = "WaveTitle";
    private final Color BACKGROUND_COLOR = Color.SEASHELL;
    private final ResourceBundle SPACING = ResourceBundle.getBundle("voogasalad/view/authoringEnvironment/resources/EditorSpacing");

    private BorderPane myPane;
    private ResourceBundle myLanguage;
    private Stage myPrevStage; //Used so we can go back to the main screen

    /**
     * This is the view that will show up when the user tries to author a game. It contains all the editors as tabs.
     * @param prevStage so that the back button can go back to the start screen
     * @param engine global instance of engine
     */
    public AuthoringView(Stage prevStage, Engine engine){
        super(Toolkit.getDefaultToolkit().getScreenSize().getWidth(),Toolkit.getDefaultToolkit().getScreenSize().getHeight(),
                Color.ROYALBLUE, "Authoring Environment", engine);
        myPrevStage = prevStage;
        myPane = new BorderPane();
        myPane.setPrefSize(myWidth,myHeight);
        myLanguage = ResourceBundle.getBundle(RESOURCE_PATH + LANGUAGE);
    }

    @Override
    public Pane startVisualization() {

        myPane.setCenter(makeTabPane());

        return myPane;
    }

    private Node makeTabPane(){
        TabPane tabPane = new TabPane();

        // Adding pane to show enemy editor
        EnemyEditor enemyEditor = new EnemyEditor(myLanguage.getString(ENEMY),super.getEngine());
        enemyEditor.getPane().add(super.createButton(myLanguage.getString("back"), myLanguage.getString("toolBack"),
                new BackController(myStage, myPrevStage, new Engine())),
                Integer.parseInt(SPACING.getString("backx")),
                Integer.parseInt(SPACING.getString("backy")));
        createTabbedView(enemyEditor,tabPane);

        // Adding pane to show projectile editor
        ProjectileEditor projectileEditor = new ProjectileEditor(myLanguage.getString(PROJECTILE), super.getEngine());
        projectileEditor.getPane().add(super.createButton(myLanguage.getString("back"), myLanguage.getString("toolBack"),
                new BackController(myStage, myPrevStage, new Engine())),
                Integer.parseInt(SPACING.getString("backx")),
                Integer.parseInt(SPACING.getString("backy")));
        createTabbedView(projectileEditor,tabPane);

        // Adding pane to show defense editor
        DefenseEditor defenseEditor = new DefenseEditor(projectileEditor, myLanguage.getString(DEFENSE), super.getEngine());
        defenseEditor.getPane().add(super.createButton(myLanguage.getString("back"), myLanguage.getString("toolBack"),
                new BackController(myStage, myPrevStage, new Engine())),
                Integer.parseInt(SPACING.getString("backx")),
                Integer.parseInt(SPACING.getString("backy")));
        createTabbedView(defenseEditor, tabPane);

        // Adding pane to show wave editor
        WaveEditor waveEditor = new WaveEditor(enemyEditor, myLanguage.getString(WAVE), super.getEngine());
        waveEditor.getPane().add(super.createButton(myLanguage.getString("back"), myLanguage.getString("toolBack"),
                new BackController(myStage, myPrevStage, new Engine())),
                Integer.parseInt(SPACING.getString("backx")),
                Integer.parseInt(SPACING.getString("backy")));
        createTabbedView(waveEditor, tabPane);

        // Adding pane to show grid editor
        ResourceBundle gridProperties = ResourceBundle.getBundle(RESOURCE_PATH + "Grid");
        double gridLength = Toolkit.getDefaultToolkit().getScreenSize().getHeight() * Double.parseDouble(gridProperties.getString("height"));
        GridView gridView = new GridView(10,10,gridLength,super.getEngine(),waveEditor.getData());
        ((BorderPane)gridView.getPane()).setBottom(super.createButton(myLanguage.getString("back"), myLanguage.getString("toolBack"),
                new BackController(myStage, myPrevStage, new Engine())));
        createTabbedView(gridView, tabPane);

        // Adding pane to show level editor
        LevelEditor levelEditor = new LevelEditor(gridView, waveEditor, myLanguage.getString(LEVEL), super.getEngine());
        levelEditor.setMapData(gridView.getMyData().getMapNames());
        levelEditor.getPane().add(super.createButton(myLanguage.getString("back"), myLanguage.getString("toolBack"),
                new BackController(myStage, myPrevStage, new Engine())),
                Integer.parseInt(SPACING.getString("backx")),
                Integer.parseInt(SPACING.getString("backy")));
        createTabbedView(levelEditor,tabPane);

        // Adding pane to show game editor
        GameEditor gameEditor = new GameEditor(levelEditor,"Put It All Together", super.getEngine());
        gameEditor.getPane().add(super.createButton(myLanguage.getString("back"), myLanguage.getString("toolBack"),
                new BackController(myStage, myPrevStage, new Engine())),
                Integer.parseInt(SPACING.getString("backx")),
                Integer.parseInt(SPACING.getString("backy")));
        createTabbedView(gameEditor,tabPane);

        return tabPane;
    }

    private Node createTabbedView(View v, TabPane t){
        GridPane g = createGridPane();
        g.add(v.startVisualization(), 0, 0);
        g.setBackground(new Background(new BackgroundFill(Color.SEASHELL, new CornerRadii(5), Insets.EMPTY)));
        v.placeInTab(g,t);

        return t;
    }
}
