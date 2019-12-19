package test.gameEngine.systems;

import org.junit.jupiter.api.Test;
import voogasalad.gameEngine.Entity;
import voogasalad.gameEngine.EntityManager;
import voogasalad.gameEngine.SystemsManager;
import voogasalad.gameEngine.components.*;
import voogasalad.gameEngine.systems.CollisionSystem;
import voogasalad.gameEngine.systems.SpawningSystem;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestSpawningSystem {
    private Map<Integer, Location> locs = new HashMap<>();
    private Map<Integer, Spawner> spawns = new HashMap<>();
    private Map<Integer, Angle> dirs = new HashMap<>();
    private Map<Integer, ActiveStatus> stats = new HashMap<>();
    private Map<Integer, SpawnerLimit> lims = new HashMap<>();
    private EntityManager ent = new EntityManager();
    private SystemsManager manager = new SystemsManager(ent);
    private SpawningSystem system = new SpawningSystem(manager, locs, spawns, dirs, stats, lims, new Collision());

    @Test
    public void testBasicSpawn(){
        Entity main = ent.newEntity();
        int id1 = main.id();
        Entity tospawn = ent.newEntity();
        int id2 = tospawn.id();
        locs.put(id1, new Location("1", "0"));
        stats.put(id1, new ActiveStatus("true"));
        spawns.put(id1, new Spawner("1", id2+""));
        dirs.put(id1, new Angle("90"));
        lims.put(id1, new SpawnerLimit("6"));

        system.updateSystem(1.1);

        System.out.println(ent.getEntity(2).toString());
    }

    @Test
    public void testSpawningLimit(){
        Entity main = ent.newEntity();
        int id1 = main.id();
        Entity tospawn = ent.newEntity();
        int id2 = tospawn.id();
        locs.put(id1, new Location("1", "0"));
        stats.put(id1, new ActiveStatus("true"));
        spawns.put(id1, new Spawner("1", id2+""));
        dirs.put(id1, new Angle("90"));
        lims.put(id1, new SpawnerLimit("2"));

        system.updateSystem(1.1);
        system.updateSystem(1.1);
        system.updateSystem(1.1);

        assertFalse(stats.get(id1).getStatus());
    }
}
