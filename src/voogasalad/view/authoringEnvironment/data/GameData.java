package voogasalad.view.authoringEnvironment.data;

import voogasalad.gameEngine.Engine;
import voogasalad.view.Data;
import voogasalad.view.authoringEnvironment.Editor;

/**
 * GameData keeps track of all the defenses via its super class in handling the interaction with the backend
 * @author Samantha Whitt, Gabriela Rodriguez-Florido
 */
public class GameData extends Data {
    public GameData(Editor view, Engine engine) {
        super(view, engine);
    }

    /**
     * @param new_id
     */
    @Override
    public void updateUI(int new_id) {
        // NOT USED: no locally saving and re-editing a game
    }

    /**
     * @param id
     */
    @Override
    public void load(int id) {
        // NOT USED: no locally saving and re-editing a game
    }
}
