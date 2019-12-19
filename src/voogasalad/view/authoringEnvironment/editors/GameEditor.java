package voogasalad.view.authoringEnvironment.editors;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import voogasalad.gameEngine.Engine;
import voogasalad.view.Data;
import voogasalad.view.authoringEnvironment.Editor;
import voogasalad.view.authoringEnvironment.controllers.SaveGameToXML;
import voogasalad.view.authoringEnvironment.controllers.levels.AddWaveToLevel;
import voogasalad.view.authoringEnvironment.controllers.levels.DeleteWaveFromLevel;
import voogasalad.view.authoringEnvironment.data.GameData;
import voogasalad.view.authoringEnvironment.data.LevelData;
import voogasalad.view.clickableobjects.ClickableButton;
import voogasalad.view.clickableobjects.ClickableEditorEntry;
import voogasalad.view.clickableobjects.LabeledInput;

import java.io.FileNotFoundException;
import java.util.*;

public class GameEditor extends Editor {
    private LevelEditor myLevelEditor;
    private GridPane gamePane;
    private LabeledInput gameName, startResource;
    private List<ChoiceBox> mySelectedLevels = new ArrayList<>();
    private List<LabeledInput> gameInputs = new ArrayList<>();
    private Data gameData;

    /**
     * This class is the parent for all other views in the program. It defines a set of useful functions for each of them,
     * as well as a model they will all share.
     *
     */
    public GameEditor(LevelEditor levelEditor, String title, Engine engine) {
        super(title, engine);
        myLevelEditor = levelEditor;
        gameData = new GameData(this, engine);
        gamePane = super.createGridPane();
    }

    @Override
    protected Pane setupPane() {
        // Create ScrollPane of levels
        ClickableEditorEntry row = myLevelEditor.createClickableEditorEntries();
        mySelectedLevels = row.getChoiceBoxes();

        gamePane.add(new Label(super.ENGLISH.getString("SelectLevels")), 0, 1);
        gamePane.add(new Label(super.ENGLISH.getString("GameSpecs")), 2, 1);
        gamePane.add(super.createScrollPane(row,200), 0,2);

        gamePane.add(setupInputs(), 2, 2);

        Node addNCancel = super.createSaveNCancel(2, super.ENGLISH.getString("AddLevel"),
                new AddWaveToLevel(super.ENGLISH.getString("level"),
                        row, super.getEngine()),
                super.ENGLISH.getString("toolAdd"),
                super.ENGLISH.getString("DeleteLevel"), new DeleteWaveFromLevel(row,super.getEngine()),
                super.ENGLISH.getString("toolDelete"));
        gamePane.add(addNCancel, 0,3);

        // Create button to save to xml
        ClickableButton saveToXml = new ClickableButton(super.ENGLISH.getString("saveXmlPrompt"),
                new SaveGameToXML(this,gameName,startResource,super.getEngine(), mySelectedLevels));
        gamePane.add(saveToXml.getNode(),0,6);

        gamePane.add(super.instructionText("GameInstructions"), 5, 2);

        return gamePane;
    }

    @Override
    protected Pane setupInputs() {
        VBox inputs = super.createVBox();

        gameName = new LabeledInput(super.ENGLISH.getString("titlePrompt"));
        inputs.getChildren().add(gameName.getNode());

        startResource = new LabeledInput(super.ENGLISH.getString("resourcePrompt"));
        inputs.getChildren().add(startResource.getNode());

        gameInputs.add(gameName);
        gameInputs.add(startResource);

        return inputs;
    }

    @Override
    public void clearInputs() {
        super.clearLabeledTexts(gameInputs);
    }

    @Override
    public void updateInputs(Collection<String> inputsAlphabetically) {

    }

    @Override
    public GridPane getPane() {
        return gamePane;
    }

    @Override
    public List<LabeledInput> getLabeledInputs() {
        return gameInputs;
    }

    @Override
    public Editor getSubEditor() {
        return myLevelEditor;
    }

    @Override
    public List<ChoiceBox> getChoiceBoxes() {
        return mySelectedLevels;
    }

    @Override
    public Map<String, Integer> getIDMap() {
        return gameData.getIDNameMap();
    }

    /**
     * Allows editors to return their save and cancel buttons so that their functionality can be changed to edit rather
     * than create
     * @return save and cancel buttons
     */
    @Override
    public Node getSaveNCancel() {
        return null;
    }

    /**
     * This method will be called by all views to begin displaying their contents.
     *
     * @return a pane object to be used by a main view to display the contents of that view
     * @throws FileNotFoundException
     */
    @Override
    public Node startVisualization() {
        return setupPane();
    }


}

