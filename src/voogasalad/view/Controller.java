package voogasalad.view;

import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import voogasalad.gameEngine.Engine;
import voogasalad.view.authoringEnvironment.Editor;
import voogasalad.view.authoringEnvironment.editors.DefenseEditor;
import voogasalad.view.authoringEnvironment.editors.ProjectileEditor;
import voogasalad.view.clickableobjects.LabeledInput;

import java.util.*;

/**
 * Defines a general controller, which will define the actions that occur when a Node is interacted with via what is
 * specified in the execute method. Also defines any interfacing with the backend (engine) and any parameters that would
 * be passed will be passed through a controller.
 *
 * @author Gabriela Rodriguez-Florido
 * @author Angel Huizar
 * @author Samantha Whitt
 */

public abstract class Controller {
    private final String soundOnButtonPress = "resources/ButtonPress.wav";
    private Engine myEngine;
    protected SoundEffect mySoundEffect;

    public Controller(Engine engine){
        myEngine = engine;
        mySoundEffect = new SoundEffect(soundOnButtonPress);
    }

    /**
     * Defines the action that will occur for when a Node is interacted with (clickableNode)
     * Will be passed in to each ClickableNode to define its behavior on action
     */
    public void execute(){
    }

    /**
     * Creates a map to allow all controllers to format the inputs received in their constructors into a format that will
     * be accepted by the backend.
     * @param paramNames the names of the components to add
     * @param paramVals the values of the components to add
     * @return the components mapped to their values
     */
    public Map createMap(Enumeration<String> paramNames, List<String> paramVals){
        Map parameters = new HashMap();
        List<String> paramNameList = new ArrayList<>();

        // These don't come in a set order so need to sort
        while(paramNames.asIterator().hasNext()) {
            paramNameList.add(paramNames.nextElement());
        }
        Collections.sort(paramNameList);

        // Assume that paramVals are passed in alphabetically from controller
        for(int i=0; i<paramNameList.size(); i++) {
            if(i<=paramVals.size()-1){
                parameters.put(paramNameList.get(i), paramVals.get(i));
            } else{
                System.out.println("Missing paramater: " + paramNameList.get(i));
            }
        }

        return parameters;
    }

    /**
     * Extracts, as a string, all of the text from the labeled input fields of an editor or view.
     * @param editor
     * @return list of strings of the content of the labeled inputs
     */
    public List<String> getInputs(Editor editor){
        List<LabeledInput> inputs = editor.getLabeledInputs();
        List<String> stringInputs = new ArrayList<>();

        for (int i=0; i<inputs.size(); i++) {
            System.out.println("String inputs from controller: " +  stringInputs);
            stringInputs.add(inputs.get(i).getText());
        }

        return stringInputs;
    }

    /**
     * Allows each editor controller (e.g. EdtiDefense) to find the button that was added in order to edit,
     * rather than create, in order to remove it and later replace the one that saves
     * @return the node to be removed
     */
    public Node findNewButton(Editor editor){
        for(Node n: editor.getPane().getChildren()){
            if(n.getId()!=null&& n.getId().equals("newbutton")){
                return n;
            }
        }
        return null;
    }

    public List<String> choiceBoxValueExtraction(Editor editor){
        List<String> values = new ArrayList<>();

        List<Integer> choiceIds = getSelectedIDsFromChoiceBox(editor);

        for(int i = 0; i < choiceIds.size(); i++){
            values.add(Integer.toString(choiceIds.get(i)));
        }

        return values;
    }

    public List<Integer> getSelectedIDsFromChoiceBox(Editor e) {

        Editor editor = (e.getSubEditor() != null) ? e.getSubEditor() : e ;

        Map<String, Integer> editorIDs = editor.getIDMap();
        List<Integer> choiceIDs = new ArrayList<>();
        List<ChoiceBox> choiceBoxes = editor.getChoiceBoxes();

        for (ChoiceBox choiceBox: choiceBoxes) {
            String chosenValue = (String) choiceBox.getValue();
            choiceIDs.add(editorIDs.get(chosenValue));
        }
        return choiceIDs;
    }

    /**
     * This method allows subclasses to grab the same instance of the engine as its parent
     * so that all classes are working with the same engine
     * @return the instance of the engine the parent class is currently using
     */
    public Engine getEngine() {
        return myEngine;
    }
}
