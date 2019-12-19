package voogasalad.gameEngine.components;

import voogasalad.gameEngine.exceptions.IncorrectVariableTypeException;

import java.util.Map;

/**
 * Interaction provides powerup abilities, either ICE, FIRE, or EXPLODABLE.
 * durationseconds parameter dictates how long the the power up is implemented for.
 * @authors Nishant Iyengar, Lakshya Bakshi
 */

public class Interaction extends Component {
	private static final String className = "Interaction";
	private int explodedWidth = 5;
	private int explodedHeight = 5;
	private int explodedDamage = 5;
	private boolean exploded;
	private String elementtype;
	private double durationseconds;


	private static final String TYPESTRING = "itype";
	private static final String DURATIONSECONDS = "durationseconds";
	private static final String INTERACTIONWIDTH = "interactionwidth";
	private static final String INTERACTIONHEIGHT = "interactionheight";
	private static final String INTERACTIONDAMAGE = "interactiondamage";


	public Interaction() {
		this("Ice","5");
	}


	public Interaction(String elementtype, String durationseconds) {
		super(className);
		this.elementtype = elementtype;
		this.durationseconds = Double.parseDouble(durationseconds);
	}

	public Interaction(Map<String,String> params) {
		super(className);
		parseParameters(params);
	}

	public boolean hasExploded() {
		return exploded;
	}

	public int getExplodedWidth() {
		return explodedWidth;
	}

	public int getExplodedHeight() {
		return explodedHeight;
	}

	public int getExplodedDamage() {
		return explodedDamage;
	}

	@Override
	public Map<String, String> asMap() {
		return Map.of(
				INTERACTIONWIDTH, explodedWidth+"",
				INTERACTIONHEIGHT, explodedHeight+"",
				INTERACTIONDAMAGE, explodedDamage+"",
				TYPESTRING, elementtype+"",
				DURATIONSECONDS,durationseconds+""
		);
	}

	@Override
	public Component copy() {
		return new Interaction(elementtype+"", durationseconds+"");
	}

	@Override
	public void parseParameters(Map<String, String> params) {
		try {
//		this.explodedWidth=Integer.parseInt(params.get(INTERACTIONWIDTH));
//		this.explodedHeight=Integer.parseInt(params.get(INTERACTIONHEIGHT));
//		this.explodedDamage=Integer.parseInt(params.get(INTERACTIONDAMAGE));
		this.explodedWidth=5;
		this.explodedHeight=5;
		this.explodedDamage=5;
		this.exploded=false;
		this.elementtype=params.get(TYPESTRING);
		this.durationseconds=Double.parseDouble(params.get(DURATIONSECONDS));
		} catch (Exception e) {
			throw new IncorrectVariableTypeException(className);
		}
	}

	public void setExploded(boolean exploded) {
		this.exploded=exploded;
	}
	public String getElementtype(){
		return elementtype;
	}
}
