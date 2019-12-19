package voogasalad.gameEngine.components;

import voogasalad.gameEngine.exceptions.IncorrectVariableTypeException;

import java.util.Map;

/**
 * Location class: Extends components and manages the location component of an entity
 * @author Lakshya Bakshi, Alex Qiao
 */
public class Location extends Component{
    private static final String className = "Location";
    private double x, y;

    public Location (String x, String y) {
        super(className);
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
    }

    public Location() {
        this("0","0");
    }

    public Location(Map<String, String> params) {
        super(className);
        parseParameters(params);
    }

    public void parseParameters(Map<String, String> params) {
        try {
        this.x =Double.parseDouble(params.get("x"));
        this.y =Double.parseDouble(params.get("y"));
        } catch (Exception e) {
            throw new IncorrectVariableTypeException(className);
        }
    }

    /**
     * Sets the new x value of the location
     * @param dt new x position
     */
    public void updateXPos (double dt) {
        x = dt;
    }

    /**
     * Sets the new y position of the location
     * @param dt new y position
     */
    public void updateYPos (double dt) {
        y = dt;
    }

    /**
     * returns the current x value
     * @return x
     */
    public double getX(){
        return x;
    }

    /**
     * returns the current y value
     * @return y
     */
    public double getY(){
        return y;
    }

    @Override
    public Map<String, String> asMap() {
        return Map.of(
                "x", x +"",
                "y", y +""
        );
    }

    @Override
    public Component copy() {
        return new Location(Double.toString(x),Double.toString(y));
    }
}
