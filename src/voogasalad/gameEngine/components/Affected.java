package voogasalad.gameEngine.components;

import voogasalad.gameEngine.exceptions.IncorrectVariableTypeException;

import java.util.Map;

/**
 * Affected component is added to all things that spawn, such as minions and projectiles.
 * Keeps track of how long the parent entity has been affected by another entity with the Interaction Component
 *
 * @author Nishant Iyengar, Lakshya Bakshi
 */

public class Affected extends Component {

    private static final String className = "Affected";

    private boolean isAffected;
    private double timeRemaining;

    private boolean iceBool = false;
    private boolean fireBool = false;

    private int ogSpeed;


    public Affected(){
        this("false","5");

    }

    public Affected(String myboolstate, String timeremaining){
        super(className);
        isAffected = Boolean.parseBoolean(myboolstate);
        timeRemaining = Double.parseDouble(timeremaining);
    }

    public Affected(Map<String, String> params) {
        super(className);
        parseParameters(params);
    }

    /**
     * set Fire status to True
     * @param mybool
     */
    public void setFire(boolean mybool){
        fireBool = mybool;
    }

    /**
     * set Ice status to True
     * @param mybool
     */
    public void setIce(boolean mybool){
        iceBool = mybool;
    }

    /**
     * decrement the amount of time left with the effect of attack by dt
     * @param dt
     */

    public void decrement(double dt){
        timeRemaining -= dt;
    }

    /**
     * reset the timer back to 5 seconds
     */

    public void resetTimer(){
        timeRemaining = 5;
    }

    /**
     * set back to the original speed before it was hit with an ice powerup
     * @param speed
     */

    public void setOgSpeed(int speed){
        ogSpeed = speed;
    }

    /**
     * get the speed of the spawned entity before it was affected by an ice powerup
     * @return
     */
    public int getOgSpeed(){
        return ogSpeed;
    }

    /**
     * check when the timer hits 0
     * @return
     */
    public boolean timeBelowZero(){
        if (timeRemaining <= 0){
            return true;
        }
        return false;
    }


    @Override
    public Map<String, String> asMap() {
        return Map.of("isaffected", isAffected+"", "timeremaining",timeRemaining+"");
    }

    @Override
    public Component copy() {
        return new Affected(isAffected+"", timeRemaining+"");
    }

    @Override
    public void parseParameters(Map<String, String> params) {
        try {
            this.isAffected = Boolean.parseBoolean(params.get("isaffected"));
            this.timeRemaining = Double.parseDouble(params.get("timeremaining"));
        }
        catch (Exception e) {
            throw new IncorrectVariableTypeException(className);
        }
    }
}
