package voogasalad.gameEngine.components;

import voogasalad.gameEngine.exceptions.IncorrectVariableTypeException;

import java.util.Map;

/**
 * Angle class: Extends components and manages the angular component of an entity
 * @author Lakshya Bakshi, Milan Shah
 */
public class Angle extends Component{
    private static final String className = "Angle";
    private double angle;

    public Angle(String angle) {
        super(className);
        this.angle = Double.parseDouble(angle);
    }

    public Angle() {
        this("0");
    }

    public Angle(Map<String, String> params) {
        super(className);
        parseParameters(params);
    }

    public void parseParameters(Map<String, String> params) {
        try {
            this.angle=Double.parseDouble(params.get("angle"));
        } catch (Exception e) {
            throw new IncorrectVariableTypeException(className);
        }
    }

    /**
     * Modifies angular value for rotation
     */
    public void updateAngle(double theta) {
//        while(theta>180) {
//            theta-=360;
//        }
//        while(theta<-180) {
//            theta+=360;
//        }
        angle = theta;
    }

    /**
     * returns the angle value
     * @return the angle value
     */
    public double getAngle() {
        return this.angle;
    }

    @Override
    public Map<String, String> asMap() {
        return Map.of(
                "angle", angle +""
        );
    }

    @Override
    public Component copy() {
        return new Angle(angle+"");
    }
}
