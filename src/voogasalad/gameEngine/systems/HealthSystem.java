package voogasalad.gameEngine.systems;


import voogasalad.gameEngine.SystemsManager;
import voogasalad.gameEngine.components.Game;
import voogasalad.gameEngine.components.Health;
import voogasalad.gameEngine.components.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HealthSystem class: System class managing the removal of entities should an entity be considered "dead"
 *
 * @author Alex Qiao
 */
public class HealthSystem extends ComponentSystem {

    private Map<Integer, Health> myHealths;
    private SystemsManager myManager;
    private Map<Integer, Resource> resources;
    private Game game;


    /**
     * System for handling projectile movement and damage delivered
     * @param manager : SystemsManager manager
     * @param healths : Health components
     */

    public HealthSystem(SystemsManager manager, Map<Integer, Health> healths, Map<Integer, Resource> resources, Game game) {
        myHealths = healths;
        myManager = manager;
        this.resources = resources;
        this.game = game;
    }

    /**
     * Iterates through all health components in the game and if the entity has a health component, then its health
     * is checked to see if it is still alive or dead and updates accordingly.
     */

    @Override
    public void updateSystem(double dt) {
        List<Integer> todelete = new ArrayList<>();
        for (int ID : myHealths.keySet()){
            if (myHealths.get(ID).getHealth() < 1) {
                todelete.add(ID);
            }
        }
//        for (int i : todelete){
//            resources.get(game.getResourceid()).gain(resources.get(i).getValue());
//        }
        todelete.forEach(id -> myManager.deleteEntity(id));

    }
    
}
