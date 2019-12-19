package voogasalad.gameEngine.exceptions;

public class MissingGameComponentsException extends PropertyExceptions {
    private static final String myMessage = "MissingGameComponent";
    public MissingGameComponentsException(String errormessage) {
        super(myMessage);
    }
}
