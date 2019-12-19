package voogasalad.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import voogasalad.gameEngine.Engine;
import voogasalad.view.View;

import java.util.List;

/**
 * GameLoop class: Runs the overall game timeline
 *
 * @author Nishant Iyengar, Alex Qiao
 */


public class GameLoop extends Application {

    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private Timeline animation;
    private Engine engine;
    private List<View> viewList;

    /**
     * GameLoop constructor: Replicate the engine used in environment so all systems are ready
     */
    public GameLoop(Engine myEngine, List<View> views){
        animation = new Timeline();
        engine = myEngine;
        viewList = views;
    }

    /**
     * Animates the game window
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

    }

    /**
     * Pauses the overall game loop
     */
    public void pause(){animation.pause();}

    /**
     * Resumes the overall game loop
     */
    public void resume(){
        animation.play();
        engine.playgame();
    }


    private void step(double elapsedTime) {
        engine.update(elapsedTime);
        viewList.forEach(View::startVisualization);
    }
}
