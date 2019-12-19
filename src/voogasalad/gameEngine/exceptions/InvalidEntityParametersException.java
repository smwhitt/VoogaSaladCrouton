package voogasalad.gameEngine.exceptions;

import voogasalad.gameEngine.Entity;

import java.util.Map;

/**
 * thrown by EntityFactory, when one or more of the parameters provided to the factory is not
 * allowed for the given particular entity type (if an entity type is provided at all).
 * @author Lakshya Bakshi
 */
public class InvalidEntityParametersException extends PropertyExceptions {
	//private static final String MESSAGE = ERROR_RESOURCES.getString("InvalidEntityParameters");
	private static final String MESSAGE = "InvalidEntityParameters";

	public InvalidEntityParametersException(int id, String type) {
		super(MESSAGE, id, type);
	}
}
