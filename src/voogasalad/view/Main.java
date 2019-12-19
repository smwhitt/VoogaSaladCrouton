package voogasalad.view;

import javafx.application.Application;
import javafx.stage.Stage;
import voogasalad.gameEngine.Engine;


import java.io.FileNotFoundException;

/**
 * Main class that runs the entire program by creating an instance of the Starting View, which will allow the user to
 * display all of the views by interacting with it, and an instance of the engine, so that everything in the frontend
 * has the same instance of engine.
 *
 * @author Gabriela Rodriguez-Florido
 */
public class Main extends Application {

    private Engine myEngine = new Engine();

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Displays the primary view (which contains all subviews)
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        View view = new StartView(myEngine);

        view.display(view.startVisualization());
    }
}
