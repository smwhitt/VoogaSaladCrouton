package voogasalad.gameEngine.components;

import voogasalad.gameEngine.exceptions.IncorrectVariableTypeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Component to hold pointers to general game info, like the resource component or the level series
 * @author Lakshya Bakshi
 */
public class Game extends Component {
	private static final String className = "Game";
	private boolean paused;
	private List<Integer> levelids;
	private int resourceid;

	public Game(){
		this(List.of(-1),-1);
	}

	public Game(List<Integer> levelids, int resourceid) {
		super(className);
		this.paused=true;
		this.levelids=levelids;
		this.resourceid=resourceid;
	}

	public Game(Map<String, String> params) {
		super(className);
		parseParameters(params);
	}

	/**
	 * gets the status of this game
	 * @return paused true or false
	 */
	public boolean status(){return paused;}
	public void parseParameters(Map<String, String> params) {
		try {
		this.paused=true;
		this.resourceid=Integer.parseInt(params.get("resourceid"));
		this.levelids= new ArrayList<>();
		Arrays.asList(params.get("levelids").split(",")).forEach(c->this.levelids.add(Integer.parseInt(c)));
		} catch (Exception e) {
			throw new IncorrectVariableTypeException(className);
		}
	}

	/**
	 * sets the game status to paused
	 */
	public void pause() {
		this.paused=true;
	}

	/**
	 * sets the game status to playing
	 */
	public void play() {
		this.paused=false;
	}

	/**
	 * returns the sequence of level entity ids for the game's progression
	 * @return
	 */
	public List<Integer> getLevelids() { return levelids; }

	/**
	 * returns the id of the resource entity for this game
	 * @return
	 */
	public int getResourceid(){return resourceid;}

	@Override
	public Map<String, String> asMap() {
		return Map.of("levelids",returnListParameters(levelids),
				"resourceid",resourceid+"");
	}
	@Override
	public Component copy() {
		return new Game(levelids,resourceid);
	}
}
