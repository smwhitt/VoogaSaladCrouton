package voogasalad.gameEngine.components;

import voogasalad.gameEngine.exceptions.IncorrectVariableTypeException;

import java.util.Map;

/**
 * Speed class: Extends components and manages the speed component of an entity
 * @author Milan Shah, Lakshya Bakshi, Alex Qiao
 */
public class Speed extends Component{
    private static final String className = "Speed";
    private int speed;
    public Speed(String speed) {
        super(className);
        this.speed = Integer.parseInt(speed);
    }

    public Speed() {
        this("0");
    }

    public Speed(Map<String, String> params) {
        super(className);
        parseParameters(params);
    }

    public void parseParameters(Map<String, String> params) {
        try {
        this.speed =Integer.parseInt(params.get("speed"));
        } catch (Exception e) {
            throw new IncorrectVariableTypeException(className);
        }
    }

    /**
     * Sets a multiplier to the speed
     * @param multiplier
     */
    public void modifySpeed(int multiplier) {
        speed *= multiplier;
    }

    /**
     * sets the speed of the component to the given value
     * @param newSpeed
     */
    public void setSpeed(int newSpeed) { speed = newSpeed; }

    /**
     * returns the speed value of this component
     * @return
     */
    public int getSpeed(){
        return speed;
    }

    @Override
    public Map<String, String> asMap() {
        return Map.of(
                "speed", speed +""
        );
    }

    @Override
    public Component copy() {
        return new Speed(speed +"");
    }
}
