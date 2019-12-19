package voogasalad.gameEngine.components;

import voogasalad.gameEngine.exceptions.IncorrectVariableTypeException;

import java.util.Map;

/**
 * Component for holding info of how much damage an entity is capable of doing to another entity
 * @authors Milan Shah, Micheal Head, Lakshya Bakshi
 */
public class Damage extends Component{
    private static final String className = "Damage";

    private int inflictDamage;

    /**
     * Constructor for the Damage component. The Damage is a component that deals damage to another upon collision
     * @param damage
     */
    public Damage(String damage){
        super(className);
        inflictDamage = Integer.parseInt(damage);
    }

    public Damage(){
        this("0");
        inflictDamage=0;
    }

    public Damage(Map<String, String> params) {
        super(className);
        parseParameters(params);
    }

    public void parseParameters(Map<String, String> params) {
        try {
            this.inflictDamage=Integer.parseInt(params.get("damage"));
        } catch (Exception e) {
            throw new IncorrectVariableTypeException(className);
        }
    }

    /**
     * Returns the damage inflicted from the component
     * @return int value of inflicting damage
     */
    public int getDamageRating(){return inflictDamage;}

    /**
     * increases the damage an entity can do, for upgrades
     * @param amountToIncrement
     */
    public void incrementDamage(int amountToIncrement){
        inflictDamage += amountToIncrement;
    }

    @Override
    public Map<String, String> asMap() {
        return Map.of("damage", inflictDamage+"");
    }

    @Override
    public Component copy() {
        return new Damage(inflictDamage+"");
    }

    public void setDamage(int damage) {
        this.inflictDamage = damage;
    }
}
