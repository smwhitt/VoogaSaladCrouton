package test.gameEngine;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import voogasalad.gameEngine.Engine;
import voogasalad.gameEngine.Entity;
import voogasalad.gameEngine.exceptions.FileCouldNotConvertException;
import voogasalad.gameEngine.exceptions.FileNotCreatedException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class TestEngine {

    Engine testEngine = new Engine();
    @Test
    public void testSaveMethod() throws IOException, FileNotCreatedException {
        // create an entity list to serialize
        // call the save method on the entity list (or whatever you want to serialize
        // see if it works
        testEngine.save("TestGame");
        String pathToFile = System.getProperty("user.dir") + "/data/" + "TestGame";
        //final Path path = Files.createTempFile(pathToFile,  ".xml");
        //assertTrue(Files.exists(path));
        File tempFile = new File(pathToFile+".xml");
        assertTrue(tempFile.exists());
    }

    @Test
    public void deserializingXML() throws IOException, FileNotCreatedException, FileCouldNotConvertException {
        // create an entity list to serialize
        // call the save method on the entity list (or whatever you want to serialize
        // see if it works
        String nameOfGame = "TwoGame";
        testEngine.save(nameOfGame);
        String pathToFile = System.getProperty("user.dir") + "/data/" + nameOfGame;
        //final Path path = Files.createTempFile(pathToFile,  ".xml");
        //assertTrue(Files.exists(path));
        File tempFile = new File(pathToFile+".xml");
        assertTrue(tempFile.exists());
        var data = testEngine.loadData(tempFile);
        assertEquals(0,data.size());
    }

    @Test
    public void testCreateMethod(){
        Map<String,String> params = new HashMap<>();
        params.put("x", "0");
        params.put("y", "0");
        String type = "Tower";
        testEngine.create(type, params);
        HashMap<String,String> e = (HashMap) testEngine.getEntity(0);
        assertEquals(e.get("x"), "0.0");

    }

    @Test
    public void testMenuGetter(){
        testEngine.loadData(new File(System.getProperty("user.dir") + "/data/" + "EntityMap" + ".xml"));
        var menu = testEngine.getMenuEntities();
        System.out.println(menu);
    }

    @Test
    public void testMapGetter() {
        testEngine.loadData(new File(System.getProperty("user.dir") + "/data/" + "EntityMap" + ".xml"));
        var menu = testEngine.getMapEntities();
        System.out.println(menu);
    }

    @Test
    public void testMetaData() {
        testEngine.loadData(new File(System.getProperty("user.dir") + "/data/" + "EntityMap" + ".xml"));
        var data = testEngine.getMetaData();
        System.out.println(data);
    }
}
