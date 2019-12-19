package voogasalad.view;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import voogasalad.gameEngine.Engine;
import voogasalad.gameEngine.components.Resource;
import voogasalad.view.clickableobjects.ClickableButton;
import voogasalad.view.clickableobjects.ClickableEditorEntry;

import java.io.FileNotFoundException;
import java.util.ResourceBundle;

/**
 * Creates an abstract class that defines useful methods for all views
 *
 * @author Gabriela Rodriguez-Florido,  Angel Huizar, Shreya Hurli
 */
public abstract class View {

    protected double myWidth;
    protected double myHeight;
    protected Color myBackground;
    protected String myTitle;
    protected Stage myStage;
    private final String defaultCss = "Authoring.css";
    protected final String languagePath = "voogasalad/view/authoringEnvironment/resources/";
    protected final String LANGUAGE = "English";
    private boolean myTabOpen;
    private Tab myTab;
    private Engine myEngine;
    protected final String identifier = "type";
    private Group myGroup;

    /**
     * This class is the parent for all other views in the program. It defines a set of useful functions for each of them,
     * as well as a model they will all share. Only used for editors
     * @param width
     * @param height
     * @param background
     * @param title
     */
    public View(double width, double height, Color background, String title, Engine engine){
        myWidth = width;
        myHeight = height;
        myBackground = background;
        myTitle = title;
        myTabOpen = false;
        myTab = new Tab(title);
        myStage = new Stage();
        myEngine = engine;
        myGroup = new Group();
    }

    /**
     * This method will be called by all views to begin displaying their contents.
     * @return a pane object to be used by a main view to display the contents of that view
     * @throws FileNotFoundException
     */
    public abstract Node startVisualization();

    /**
     * This method allows a view to be displayed, using some CSS styling
     * @param node
     * @param CSS_STYLE
     */
    public void display(Node node, String CSS_STYLE){
        myGroup.getChildren().add(node);
        createScene(CSS_STYLE);
    }

    /**
     * This method allows any view to be placed in a tab, this allows for flexibility of design and allows anything to be tabbable
     * @param myGrid
     * @param tabPane
     * @return returns a tabpane with the pane created from the view in it
     */
    public TabPane placeInTab(Node myGrid, TabPane tabPane){
        myTab.setContent(myGrid);
        myTab.setOnClosed(event -> myTabOpen = false);
        if(! myTabOpen){
            tabPane.getTabs().add(myTab);
            myTabOpen = true;
        }
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        return tabPane;
    }

    /**
     * Allows any view to be closed
     */
    public void closeScene(){
        myStage.close();
    }

    /**
     * Allows all views to create a (back) button (could be any other button),
     * whose behavior when clicked depeneds on the controller, without repeating
     * this code in each view
     * @param backButtonName
     * @param toolBack
     * @param back
     * @return back button
     */
    public Node createButton(String backButtonName, String toolBack, Controller back){
        GridPane backButtonHolder = new GridPane();
        //backButtonHolder.setHgap(10);

        ClickableButton backButton = new ClickableButton(backButtonName, back);
        Tooltip.install(backButton.getNode(), new Tooltip(toolBack));

        backButtonHolder.add(backButton.getNode(), 0, 2);

        return backButtonHolder;
    }

    /**
     * Allows all views to access the engine
     * @return engine
     */
    public Engine getEngine(){
        return myEngine;
    }

    /**
     * Allows any nodes to be added to the view before it is displayed
     * @param node
     */
    public void addToGroup(Node node){
        myGroup.getChildren().add(node);
    }

    public void removeFromGroup(Node node){
        myGroup.getChildren().remove(node);
    }

    /**
     * Creates a gridpane for each view to use using some default spacing for consistency throughout the views
     * @return gridpane for placing view contents into
     */
    protected GridPane createGridPane(){
        GridPane inputHolder = new GridPane();
        //inputHolder.setGridLinesVisible(true);
        inputHolder.setPadding(new Insets(10,10,10,10));
        inputHolder.setHgap(myWidth/100);
        inputHolder.setVgap(myHeight/70);
        return inputHolder;
    }

    /**
     * Creates a scroll pane to be used by all views if needed
     * @return scrollpane
     */
    public ScrollPane createScrollPane(ClickableEditorEntry entry, int width){
        ScrollPane scroll = new ScrollPane(entry.getNode());
        scroll.setMaxHeight(200);
        scroll.setMinHeight(200);
        scroll.setPrefWidth(width);
        scroll.setBackground(new Background(new BackgroundFill(Color.SEASHELL, new CornerRadii(5), Insets.EMPTY)));
        return scroll;
    }

    /**
     * This method allows a view to be displayed, using default CSS styling as set by
     * (Usually called with start visualization, which is a method that is implemented by each view, since it will contain
     * all of the nodes that comprise the view. For example, display(myView.startVisualization).
     * @param node
     */
    public void display(Node node){
        myGroup.getChildren().add(node);
        createScene(defaultCss);
    }

    private void createScene(String styleFile){
//        myStage.initModality(Modality.APPLICATION_MODAL); //Means that if we create pop-ups, disables all other views until user deals with pop-up
        Scene scene = new Scene(myGroup,myWidth,myHeight,myBackground);
        scene.getStylesheets().add(styleFile);
        myStage.setScene(scene);
        myStage.setTitle(myTitle);
        myStage.setResizable(false);
        myStage.show();
    }

}
