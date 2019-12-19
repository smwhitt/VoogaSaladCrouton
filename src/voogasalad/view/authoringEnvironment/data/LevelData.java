package voogasalad.view.authoringEnvironment.data;

import voogasalad.gameEngine.Engine;
import voogasalad.view.Data;
import voogasalad.view.authoringEnvironment.Editor;

/**
 * LevelData keeps track of all the defenses via its super class in handling the interaction with the backend
 * @author Gabriela Rodriguez-Florido, Samantha Whitt
 */
public class LevelData extends Data {
    public LevelData(Editor view, Engine engine) {
        super(view, engine);
    }

    /**
     * @param new_id
     */
    @Override
    public void updateUI(int new_id) {
        // NOT USED: no locally saving and re-editing a level
    }

    /**
     * @param id
     */
    @Override
    public void load(int id) {
        // NOT USED: no locally saving and re-editing a level
    }
}
