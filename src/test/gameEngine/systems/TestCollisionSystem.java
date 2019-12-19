package test.gameEngine.systems;

import org.junit.jupiter.api.Test;
import voogasalad.gameEngine.components.*;
import voogasalad.gameEngine.systems.CollisionSystem;

import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class TestCollisionSystem {
    private Map<Integer, Location> locs = new HashMap<>();
    private Map<Integer, Collision> collisions = new HashMap<>();
    private Map<Integer, Health> healths = new HashMap<>();
    private Map<Integer, Damage> damage = new HashMap<>();
    private Map<Integer, Interaction> explodables = new HashMap<>();
    //CollisionSystem system = new CollisionSystem(locs, collisions, healths, damage,explodables);


    @Test
    public void testBasicCollision(){
        int id1 = 1;
        int id2 = 2;
        locs.put(id1, new Location("0", "0"));
        collisions.put(id1, new Collision("1", "1"));
        healths.put(id1, new Health("10"));
        locs.put(id2, new Location("0.5", "0.5"));
        collisions.put(id2, new Collision("1", "1"));
        damage.put(id2, new Damage("5"));

        //system.updateSystem(0.016);
        int expectedhealth = 5;
        assertEquals(expectedhealth, healths.get(id1).getHealth());
    }

}
