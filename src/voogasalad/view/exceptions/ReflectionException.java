package voogasalad.view.exceptions;

/**
 * Generates exceptions for reflection mapping errors
 *
 * @author Gabriela Rodriguez-Florido
 */
public class ReflectionException extends RuntimeException {

    /**
     * Create an exception based on an issue in our code.
     */
    public ReflectionException(String message, Object ... values) {
        super(String.format(message, values));
    }

    /**
     * Create an exception based on a caught exception with a different message.
     */
    public ReflectionException(Throwable cause, String message, Object ... values) {
        super(String.format(message, values), cause);
    }

    /**
     * Create an exception based on a caught exception, with no additional message.
     */
    public ReflectionException(Throwable cause) {
        super(cause);
    }
}
