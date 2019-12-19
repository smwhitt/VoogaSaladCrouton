package test.gameEngine.systems;

import org.junit.jupiter.api.Test;
import voogasalad.gameEngine.Entity;
import voogasalad.gameEngine.components.*;
import voogasalad.gameEngine.systems.TargetingSystem;

import java.util.Map;

public class TestTargetingSystem{
    @Test
    public void testTargetStrong() {
        Entity e1 = new Entity(0);
        Entity e2 = new Entity(1);
        Entity e3 = new Entity(2);

        Health h1 = new Health("100"); h1.setOwner(e1);
        Health h2 = new Health("200"); h2.setOwner(e2);
        Health h3 = new Health("198"); h3.setOwner(e3);

        Location l1 = new Location("0","0"); l1.setOwner(e1);
        Location l2 = new Location("2","2"); l2.setOwner(e2);
        Location l3 = new Location("1000", "-500"); l3.setOwner(e3);

        Type t1 = new Type("defense"); t1.setOwner(e1);
        Type t2 = new Type("minion"); t2.setOwner(e2);
        Type t3 = new Type("minion"); t3.setOwner(e3);

        Angle a1 = new Angle("0"); a1.setOwner(e1);

        AI ai = new AI("findstrong"); ai.setOwner(e1);

        Map<Integer,Health> healths = Map.of(0,h1,1,h2,2,h3);
        Map<Integer,Location> locations = Map.of(0,l1,1,l2,2,l3);
        Map<Integer, Type> types = Map.of(0,t1,1,t2,2,t3);
        Map<Integer, Angle> angles = Map.of(0,a1);
        Map<Integer, AI> ais = Map.of(0, ai);

        TargetingSystem sys = new TargetingSystem(types,healths,locations,angles,ais);

        sys.updateSystem(0.016);
        System.out.println(angles.toString());
    }
}
