package voogasalad.gameEngine.components;

import voogasalad.gameEngine.exceptions.IncorrectVariableTypeException;

import java.util.*;

/**
 * Component to hold pointers for current level info, like which map or tower is relevant
 * @authors Lakshya Bakshi
 */
public class Level extends Component {
    private static final String className = "Level";

    private List<Integer> waves;
    private int levelmap;
    private int towerid;

    public Level(String wavelist, String levelmapid, String towerid) {
        super(className);
        waves = new ArrayList<>();
        for (String s : wavelist.split(",")){
            waves.add(Integer.parseInt(s));
        }
        levelmap = Integer.parseInt(levelmapid);
        this.towerid=Integer.parseInt(towerid);
    }

    public Level() {
        this("-1","-1","-1");
    }

    public Level(Map<String, String> params) {
        super(className);
        parseParameters(params);
    }

    @Override
    public void parseParameters(Map<String, String> params) {
        try {
        waves=new ArrayList<>();
        Arrays.asList(params.get("waves").split(",")).forEach(c->waves.add(Integer.parseInt(c)));
        levelmap=Integer.parseInt(params.get("levelmap"));
        this.towerid=Integer.parseInt(params.get("towerid"));
        } catch (Exception e) {
            throw new IncorrectVariableTypeException(className);
        }
    }

    public List<Integer> getWaves(){return Collections.unmodifiableList(waves);
    }
    public int getLevelMap(){return levelmap;}

    public int getTowerid(){return towerid; }

    @Override
    public Map<String, String> asMap() {
        return Map.of("waves", returnListParameters(waves),
                "levelmap", levelmap+"",
                "towerid",towerid+"");
    }

    @Override
    public Component copy() {
        return new Level(returnListParameters(waves), levelmap+"", towerid+"");
    }
}
