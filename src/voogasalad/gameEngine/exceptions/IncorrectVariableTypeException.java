package voogasalad.gameEngine.exceptions;

public class IncorrectVariableTypeException extends PropertyExceptions {
    private static final String message = "IncorrectVariableType";
    public IncorrectVariableTypeException(String ComponentType) { super(message, ComponentType); }
}
