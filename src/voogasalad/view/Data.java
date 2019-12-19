package voogasalad.view;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import voogasalad.gameEngine.Engine;
import voogasalad.view.authoringEnvironment.Editor;
import voogasalad.view.authoringEnvironment.controllers.defenses.EditDefense;
import voogasalad.view.authoringEnvironment.editors.DefenseEditor;
import voogasalad.view.clickableobjects.LabeledInput;

import java.util.*;

/**
 * This class holds the mapping of the backend id for each object to the frontend representation
 * It also interfaces with the backend to save and edit the backend objects created by interacting with the authoring
 * environment.
 *
 * This class extends controller because it defines the behavior for when a user clicks on a save button, or edit
 * functionality.
 *
 * @author Samantha Whitt, Gabriela Rodriguez-Florido
 */
public abstract class Data extends Controller {

    protected final ResourceBundle ENGLISH = ResourceBundle.getBundle("voogasalad/view/authoringEnvironment/resources/English");
    protected List<String> existingNames = new ArrayList<>();
    protected List<Integer> ids = new ArrayList<>();
    protected Map<String, Integer> idsWithNames = new HashMap<>();
    protected Editor myView;
    protected Text myErrorMessage;
    protected Text mySaveMessage;
    private FadeTransition myTransition;

    public Data(Editor view, Engine engine) {
        super(engine);
        myView = view;
        myErrorMessage = new Text("Can't make new object with the same name!");
        myErrorMessage.setFill(Color.RED);
        mySaveMessage = new Text("Saved!");
        mySaveMessage.setFill(Color.GREEN);
        myTransition= new FadeTransition(new Duration(1500), mySaveMessage);
        myTransition.setFromValue(1);
        myTransition.setToValue(0);
    }

    /**
     * Should take care of create and updating entities (ENGINE.create and ENGINE.edit), which are backend objects
     * @param frontend_name
     * @param backend_name
     * @param properties
     */
    public boolean save(String frontend_name, String backend_name, Map<String, String> properties){
        boolean saved = false;

        if(!existingNames.contains(frontend_name)) {
            try{
                int new_id = super.getEngine().create(backend_name, properties);
                existingNames.add(frontend_name);
                ids.add(new_id);
                myView.getPane().getChildren().remove(myErrorMessage);
                idsWithNames.put(frontend_name,new_id);
                if(mySaveMessage!=null){
                    myView.getPane().getChildren().remove(mySaveMessage);
                }
                myView.getPane().add(mySaveMessage,2,6);
                myTransition.play();
                // update varies depending on the kind of view
                updateUI(new_id);
                saved = true;
            } catch (Exception e){
                myErrorMessage.setText(e.getMessage());
                myView.getPane().getChildren().remove(myErrorMessage);
                myView.getPane().add(myErrorMessage, 2,6);
                saved = false;
            }
        } else {
            if(!myView.getPane().getChildren().contains(myErrorMessage)){
                // throw error
                myErrorMessage.setText("Can't make new object with the same name!");
                myView.getPane().getChildren().remove(myErrorMessage);
                myView.getPane().add(myErrorMessage, 2,6);
                saved = false;
            }
        }

        for (String key: idsWithNames.keySet()) {
            ids.add(idsWithNames.get(key));
        }

        return saved;
    }

    /**
     * Allows each editor to specify what needs to update in the UI for each save, for enemy for example its the image for the
     * created enemy that needs to display on the editorselector.
     * @param new_id
     */
    public abstract void updateUI(int new_id);

    /**
     * Defines the behavior when a user clicks on a created object image to edit it
     * @param id
     */
    public abstract void load(int id);

    /**
     * Returns the frontend name to the backend id mapping to allow for the updating of the correct backend object on
     * edit
     * @return id to name map
     */
    public Map<String, Integer> getIDNameMap(){
        return idsWithNames;
    }

    /**
     * Allows the load method to get back the engine params in the appropriate order
     * @param id
     * @return
     */
    public Collection<String> getEngineParamsSortedByKey(int id) {
        // TreeMap to store values of HashMap
        TreeMap<String, String> sorted = new TreeMap<>();

        // Copy all data from hashMap into TreeMap
        sorted.putAll(super.getEngine().getEntity(id));

        Collection<String> desiredVals = new ArrayList<>();

        // Display the TreeMap which is naturally sorted
        for (Map.Entry<String, String> entry : sorted.entrySet()) {
            if (entry.getKey().equals("type") || entry.getKey().equals("activestatus")) {
                continue;
            } else {
                desiredVals.add(entry.getValue());
            }
        }
        return desiredVals;
    }

}
