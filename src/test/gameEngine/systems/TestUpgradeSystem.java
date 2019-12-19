//package test.gameEngine.systems;
//
//import org.junit.jupiter.api.Test;
//import voogasalad.gameEngine.components.*;
//import voogasalad.gameEngine.exceptions.NotEnoughMoneyException;
//import voogasalad.gameEngine.systems.UpgradeSystem;
//
//import java.util.HashMap;
//import java.util.Map;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class TestUpgradeSystem {
//
//    private Map<Integer, Resource> resource = new HashMap<>();
//    private Map<Integer, Upgrade> upgrade = new HashMap<>();
//    private Map<Integer, Cost> cost = new HashMap<>();
//    private Map<Integer, Damage> damage = new HashMap<>();
//
//    UpgradeSystem upgradeSystem = new UpgradeSystem(cost,resource,damage,upgrade);
//
//    @Test
//    public void testBasicUpgrade(){
//        int id1 = 1;
//        int id2 = 2;
//        upgrade.put(id1, new Upgrade("5", "10"));
//        cost.put(id1, new Cost("20"));
//        damage.put(id1, new Damage("15"));
//        resource.put(id2, new Resource("30"));
//
//
//        upgradeSystem.upgrade(id1,id2);
//        int expectedResource = 20;
//        int expectedDamage = 20;
//        //System.out.print(resource.get(id2).getValue());
//        assertEquals(expectedResource, resource.get(id2).getValue());
//        assertEquals(expectedDamage, damage.get(id1).getDamageRating());
//
//    }
//
//    @Test
//    public void testException(){
//        int id1 = 1;
//        int id2 = 2;
//        upgrade.put(id1, new Upgrade("17", "31"));
//        cost.put(id1, new Cost("20"));
//        damage.put(id1, new Damage("15"));
//        resource.put(id2, new Resource("30"));
//        upgradeSystem.upgrade(id1,id2);
//
//        assertThrows(NotEnoughMoneyException.class,() -> upgradeSystem.upgrade(id1,id2));
//
//    }
//
//
//
//}
