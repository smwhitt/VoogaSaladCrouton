package test.gameEngine;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.junit.jupiter.api.Test;
import voogasalad.gameEngine.Engine;
import voogasalad.gameEngine.Entity;
import voogasalad.gameEngine.EntityManager;
import voogasalad.gameEngine.components.Health;
import voogasalad.gameEngine.components.Speed;
import voogasalad.gameEngine.components.Title;
import voogasalad.gameEngine.exceptions.FileCouldNotConvertException;
import voogasalad.gameEngine.exceptions.FileNotCreatedException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSerialization {

    EntityManager myManager = new EntityManager();
    Engine myEngine = new Engine();
    Map<String,String> defense = Map.of("name", "blah",
            "image", "pic",
            "cost", "11",
            "frequency", "2",
            "spawnentity", "4");
    Map<String, String> path = Map.of("angle", "90",
            "image", "bleh",
            "x", "10",
            "y", "30",
            "path", "1",
            "width", "2",
            "height", "3");
    Map<String, String> tower = Map.of("health", "1000");
    Map<String, String> resources = Map.of("value", "300");
    Map<String, String> levels = Map.of("name", "hi");

    TestSerialization() {
        myEngine.create("Defense", defense);
        myEngine.create("Path", path);
        myEngine.create("Tower", tower);
        myEngine.create("Resources", resources);
        myEngine.create("Levels", levels);


//        for (int i = 0; i < 12; i++) {
//
//            myManager.newEntity();
//            myManager.getEntities().get(i).addComponent(new Health());
//            myManager.getEntities().get(i).addComponent(new Speed());
//            myManager.getEntities().get(i).addComponent(new Title());
//        }
    }
    @Test
    public void testSave() throws FileNotCreatedException {
        myEngine.save("EntityMap");
    }

    @Test
    public void testLoad() throws FileCouldNotConvertException {
        Boolean identical = true;
        File tempFile = new File(System.getProperty("user.dir") + "/data/" + "EntityMap" + ".xml");
        //Engine testEngine = new Engine();
        Map<Integer, Entity> testMap = myEngine.loadData(tempFile);
        for (int ID: testMap.keySet()) {
            if (!testMap.get(ID).equals(myManager.getEntities().get(ID))) {
                identical = false;
            }
        }
        //System.out.println(testMap);
        assertEquals(myEngine.getEntityMap().toString(), testMap.toString());

        //MapContainer testMap = (MapContainer) mySerializer.fromXML(System.getProperty("user.dir") + "/data/" + "EntityMap.xml");

    }
}
