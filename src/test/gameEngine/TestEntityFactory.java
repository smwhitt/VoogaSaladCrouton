package test.gameEngine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import voogasalad.gameEngine.Entity;
import voogasalad.gameEngine.EntityFactory;
import voogasalad.gameEngine.EntityManager;
import voogasalad.gameEngine.components.Collision;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TestEntityFactory {
	private EntityFactory factory;
	private EntityManager manager;
	private Map<String,String> params;
	private Entity entity;

	@BeforeEach
	public void setup() {
		params = Map.ofEntries(
			Map.entry("x","45.0"),
			Map.entry("y","67.0"),
			Map.entry("angle","89.0"),
			Map.entry("image","something.png"),
			Map.entry("path","accessible"),
			Map.entry("width", "30.0"),
			Map.entry("height", "31.0"),
			Map.entry("damage","2"),
			Map.entry("speed", "4"),
			Map.entry("name", "bongo"),
			Map.entry("paths","1,4,6,3"),
			Map.entry("cost","32"),
			Map.entry("health","33"),
			Map.entry("type","oogleboogle"),
			Map.entry("towerid","1231235"),
			//Map.entry("numbertospawn","30"),
			Map.entry("value","40"),
			Map.entry("frequency","34.0"),
			Map.entry("spawnerlimit","7"),
			Map.entry("spawnentity","8"),
			Map.entry("activestatus","false"),
			Map.entry("upgradecost","500"),
			Map.entry("upgradequantity","35"),
			Map.entry("spawnpoints","7,2,95"),
			Map.entry("waves","3,234,28"),
			Map.entry("levelmap","9"),
				Map.entry("elementtype","fire"),
				Map.entry("levelids", "89,24,12"),
				Map.entry("resourceid", "9304")
		);

		factory = new EntityFactory();
		manager = new EntityManager();

		entity = manager.newEntity();
	}


	@Test
	public void testCorrectParams() {
		Set<String> expected = new HashSet<>();
		expected.addAll(params.keySet());

		factory.populate(entity,params);

		assertEquals(expected,entity.asMap().keySet());
	}

	@Test
	public void testNotEnoughParams() {
		Set<String> expected = new HashSet<>();
		expected.add("type");
		expected.add("activestatus");

		params = Map.of(
				"x","5"
		);

		factory = new EntityFactory();
		factory.populate(entity,params);

		assertEquals(expected, entity.asMap().keySet());
	}

	@Test
	public void testInvalidParam() {
		params = Map.of(
				"x","what's up dude",
				"y","5"
		);
		assertThrows(Exception.class, () -> (new EntityFactory()).populate(entity,params));
		// FIXME: SHOULD THROW CUSTOM CLASS.
	}
	@Test
	public void testComponentOutput() {
		List<String> expected = new ArrayList<>();
		expected.addAll(params.values());

		factory.populate(entity,params);
		Collections.sort(expected);
		List<String> outcome = new ArrayList<>(entity.asMap().values());
		Collections.sort(outcome);
		assertEquals(expected, outcome);
	}

}
