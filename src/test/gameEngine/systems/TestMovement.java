package test.gameEngine.systems;

import org.junit.jupiter.api.Test;
import voogasalad.gameEngine.components.Angle;
import voogasalad.gameEngine.components.Location;
import voogasalad.gameEngine.components.Speed;
import voogasalad.gameEngine.systems.MovementSystem;

import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class TestMovement {
    Map<Integer, Speed> speeds = new HashMap<>();
    Map<Integer, Location> locs = new HashMap<>();
    Map<Integer, Angle> dirs = new HashMap<>();

    MovementSystem system = new MovementSystem(speeds, locs, dirs);
    @Test
    public void testBasicMovement(){
        speeds.put(1, new Speed("1"));
        locs.put(1, new Location("0", "0"));
        dirs.put(1, new Angle("0.0"));
        double expectedx = 1;
        double expectedy = 0;
        system.updateSystem(1);
        assertEquals(expectedx, locs.get(1).getX());
        assertEquals(expectedy, locs.get(1).getY());
    }

    @Test
    public void testNegativeMovement(){
        int id = 2;
        speeds.put(id, new Speed("1"));
        locs.put(id, new Location("0","0"));
        dirs.put(id, new Angle("270.0"));
        double expectedx = 0;
        double expectedy = -1;
        system.updateSystem(1);
        assertEquals((int)expectedx, (int)locs.get(id).getX());
        assertEquals((int)expectedy, (int)locs.get(id).getY());
    }

}
