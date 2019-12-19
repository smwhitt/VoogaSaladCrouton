package voogasalad.gameEngine.components;

import voogasalad.gameEngine.exceptions.IncorrectVariableTypeException;

import java.util.List;
import java.util.Map;

/**
 * component to hold the title string of an entity, like a dart or minion
 * @author Lakshya Bakshi
 */
public class Title extends Component {
	private static final String className = "Title";
	private String title;

	public Title(String title) {
		super(className);
		this.title=title;
	}

	public Title() {
		this("");
	}

	public Title(Map<String, String> params) {
		super(className);
		parseParameters(params);
	}

	public void parseParameters(Map<String, String> params) {
		try { this.title=params.get("name");
		} catch (Exception e) {
			throw new IncorrectVariableTypeException(className);
		}
	}

	@Override
	public Map<String, String> asMap() {
		return Map.of("name",title+"");
	}

	@Override
	public Component copy() {
		return new Title(title);
	}
}
