package voogasalad.gameEngine.systems;

import voogasalad.gameEngine.components.AI;

import java.util.Map;

public class VictorySystem extends ComponentSystem{
    private int minion;
    private int minionTracker = 0;
    private int older = 0;
    private int newer;
    private Map<Integer, AI> myAI;
    private boolean gameWon = false;



    /**
     * System for handling projectile movement and damage delivered
     * @param minionCount: total number of minions to kill to win game
     * @param myAIs: tells us how many minions are active at a given time
     */
    public VictorySystem(int minionCount, Map<Integer, AI> myAIs){
        minion = minionCount;
        myAI = myAIs;
        newer = myAI.size();
    }

    /**
     * checks to see if the number of minions killed equals or exceeds the predefined number of minions supposed to spawn per level, which means that the level has completed
     * @param dt
     */

    @Override
    public void updateSystem(double dt) {
        older = newer;
        newer = myAI.size();
        if (newer < older){
            minionTracker += (older-newer);
        }
        if (minionTracker >= minion){
            gameWon = true;
        }
    }

    private boolean getWinState() {
        return gameWon;
    }

    }
