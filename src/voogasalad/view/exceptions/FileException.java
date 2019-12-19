package voogasalad.view.exceptions;

/**
 * Generates exceptions for file reading errors (did not want to use default java exception bc they are not runtime exceptions)
 *
 * @author Gabriela Rodriguez-Florido
 */
public class FileException extends RuntimeException {
    /**
     * Create an exception based on an issue in our code.
     */
    public FileException(String message, Object ... values) {
        super(String.format(message, values));
    }

    /**
     * Create an exception based on a caught exception with a different message.
     */
    public FileException(Throwable cause, String message, Object ... values) {
        super(String.format(message, values), cause);
    }

    /**
     * Create an exception based on a caught exception, with no additional message.
     */
    public FileException(Throwable cause) {
        super(cause);
    }
}
