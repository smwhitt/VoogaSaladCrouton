package voogasalad.gameEngine.components;

import voogasalad.gameEngine.exceptions.IncorrectVariableTypeException;

import java.util.List;
import java.util.Map;

/**
 * Component to hold current resources value
 * @author Lakshya Bakshi
 */
public class Resource extends Component {
	private static final String className = "Resource";
	private int value;

	public Resource(String value) {
		super(className);
		this.value=Integer.parseInt(value);
	}

	public Resource() {
		this("0");
	}

	public Resource(Map<String, String> params) {
		super(className);
		parseParameters(params);
	}

	public void parseParameters(Map<String, String> params) {
		try { this.value=Integer.parseInt(params.get("value"));
		} catch (Exception e) {
			throw new IncorrectVariableTypeException(className);
		}
	}

	/**
	 * returns whether or not there is enough value in this resource component to allow a certain purchase
	 * @param price of feature to be bought
	 * @return whether or not there is enough resource for that price
	 */
	public boolean canBuy(int price){

		if (price <= value){
			return true;
		}
		return false;
	}

	/**
	 * returns the value of this component
	 * @return value
	 */

	public int getValue(){

		return value;

	}

	public void gain(int amount){value +=amount;}

	/**
	 * deducts value from the resource component for the purchase of a feature
	 * @param price
	 */
	public void buy(int price){
		value -= price;
	}

	@Override
	public Map<String, String> asMap() {
		return Map.of(
				"value",value+""
		);
	}

	@Override
	public Component copy() {
		return new Resource(value+"");
	}
}
