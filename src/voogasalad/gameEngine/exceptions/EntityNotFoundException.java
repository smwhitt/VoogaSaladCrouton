package voogasalad.gameEngine.exceptions;

import java.util.ResourceBundle;

/**
 * thrown when an entity matching a given ID cannot be found or does not exist,
 * or when a passed entity is null, when it probably shouldn't be.
 *
 * This exception would also be thrown if the entity with a given ID is deemed "dead" or "inactive"
 * by the system.
 *
 * If you want to know if a given ID has no entity, this exception is not the best way to do so.
 * Try Engine.getIDs.
 *
 * @author micheal head
 */
public class EntityNotFoundException extends PropertyExceptions {
	private static final long serialVersionUID = 1L;
	//private static final String MESSAGE = ERROR_RESOURCES.getString("EntityNotFound");
	private static final String MESSAGE = "EntityNotFound";

	/**
	 * throws the exception, whose message includes the ID not found.
	 * @param id ID without an associated entity.
	 */
	public EntityNotFoundException(int id) {
		super(MESSAGE, id);
	}

	public EntityNotFoundException() {
		super(MESSAGE, "<unknown>");
	}
}
