package voogasalad.gameEngine.components;

import voogasalad.gameEngine.exceptions.IncorrectVariableTypeException;

import java.util.List;
import java.util.Map;

/**
 * Component to hold info of upgrading, like cost and the degree by which the upgrade impacts the game
 * @authors Lakshya Bakshi
 */
public class Upgrade extends Component {
    private static final String className = "Upgrade";
    private static final List<String> fields = List.of("upgradecost","upgradequantity");
    private int upgradecost;
    private int upgradeincrement;

    public Upgrade(String myupgradeincrement,String upgradecost) {
        super(className);
        this.upgradecost = Integer.parseInt(upgradecost);
        upgradeincrement = Integer.parseInt(myupgradeincrement);
    }

    public Upgrade(Map<String, String> params) {
        super(className);
        parseParameters(params);
    }

    @Override
    public void parseParameters(Map<String, String> params) {
        try {
        upgradeincrement=Integer.parseInt(params.get("upgradequantity"));
        upgradecost=Integer.parseInt(params.get("upgradecost"));
        } catch (Exception e) {
            throw new IncorrectVariableTypeException(className);
        }
    }

    public Upgrade() {
        this("5","50");
    }

    /**
     * returns the upgrade's cost
     * @return upgrade cost
     */
    public int getUpgradecost() {
        return upgradecost;
    }

    /**
     * returns the degree by which the upgrade increments some parameter
     * @return upgradeincrement
     */
    public int getUpgradeincrement() {
        return upgradeincrement;
    }

    @Override
    public Map<String, String> asMap() {
        return Map.of(
                "upgradequantity",  upgradeincrement+"",
                "upgradecost",upgradecost+""
        );
    }

    @Override
    public Component copy() {
        return new Upgrade(upgradeincrement+"",upgradecost+"");
    }
}
