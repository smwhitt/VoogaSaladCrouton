package voogasalad.gameEngine.components;

import voogasalad.gameEngine.exceptions.IncorrectVariableTypeException;

import java.util.Map;

/**
 * Health class: Extends components and manages the health component of an entity
 * @author Alex Qiao, Milan Shah, Lakshya Bakshi
 */
public class Health extends Component{
    private static final String className = "Health";
    private int health;

    public Health (String health) {
        super(className);
        this.health = Integer.parseInt(health);
    }

    public Health() {
        this("1");
    }

    public Health(Map<String, String> params) {
        super(className);
        parseParameters(params);
    }

    public void parseParameters(Map<String, String> params) {
        try { this.health=Integer.parseInt(params.get("health"));
        } catch (Exception e) {
            throw new IncorrectVariableTypeException(className);
        }
    }
    /**
     * Adjusts health points
     * @param healthPoints number of health points to add (positive) or remove (negative)
     */
    public void updateHealth (int healthPoints) {
        health += healthPoints;
    }

    @Override
    public Map<String, String> asMap() {
        return Map.of(
                "health", health+""
        );
    }

    /**
     * return this component's health value
     * @return health
     */
    public int getHealth(){
        return health;
    }



    @Override
    public Component copy() {
        return new Health(Integer.toString(health));
    }


}
