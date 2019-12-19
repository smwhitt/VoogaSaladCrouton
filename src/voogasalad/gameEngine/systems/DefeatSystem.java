package voogasalad.gameEngine.systems;

import voogasalad.gameEngine.components.*;

import java.util.Map;

/**
 * System for determining whether the current tower has been destroyed, and if so, return the change in status
 */
public class DefeatSystem extends ComponentSystem{
    private static final String LEVEL = "level";
    private Map<Integer, Health> healthMap;
    private Game game;
    private Map<Integer, Type> typeMap;
    private Map<Integer, Level> levelMap;
    private Map<Integer, ActiveStatus> statusMap;
    private boolean gameLost;


    public DefeatSystem(Game game, Map<Integer, Health> healthMap, Map<Integer, Type> typeMap, Map<Integer, Level> levelMap, Map<Integer, ActiveStatus> activeStatusMap) {
        this.healthMap=healthMap;
        this.game=game;
        this.typeMap=typeMap;
        this.levelMap=levelMap;
        this.statusMap=activeStatusMap;
        gameLost=false;
    }
    @Override
    public void updateSystem(double dt) {
        int currLevelId= (int) statusMap.keySet().stream()
                .filter(c->statusMap.get(c).getStatus())
                .filter(c->typeMap.get(c).getType().equalsIgnoreCase(LEVEL))
                .toArray()[0];
        if (healthMap.get(levelMap.get(currLevelId).getTowerid()).getHealth()<1) {
            gameLost=true;
        }
    }

    /**
     * returns whether the current tower has been destroyed
     * @return gameLost
     */
    public boolean getGameLost() { return gameLost; }
}
