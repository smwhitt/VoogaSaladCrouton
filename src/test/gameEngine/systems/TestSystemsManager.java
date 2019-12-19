package test.gameEngine.systems;

import org.junit.jupiter.api.Test;
import voogasalad.gameEngine.Entity;
import voogasalad.gameEngine.EntityManager;
import voogasalad.gameEngine.SystemsManager;
import voogasalad.gameEngine.components.Health;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSystemsManager {
    private EntityManager myEntityManager = new EntityManager();
    private SystemsManager mySystemsManager = new SystemsManager(myEntityManager);

    @Test
    public void testDeleteEntity(){
        Entity e = myEntityManager.newEntity();
        e.addComponent(new Health("-1000"));
        mySystemsManager.playgame();
        mySystemsManager.addEntity(e);
        mySystemsManager.update(0.016);
        System.out.println(myEntityManager.getEntities());
        assertEquals(0, myEntityManager.getEntities().size(), "should delete entity with <1 health");
    }

    @Test
    public void testAddEntity() {
        Entity entity = myEntityManager.newEntity();
        entity.addComponent(new Health());
        mySystemsManager.addEntity(entity);
        mySystemsManager.update(0.016);
        assertEquals(1, myEntityManager.getEntities().size(), "should add an entity");
    }
}
