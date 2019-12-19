package voogasalad.gameEngine.exceptions;

public class UnableToBuildException extends PropertyExceptions {
    private static final String myMessage = "UnableToBuild";
    public UnableToBuildException(String errormessage) {
        super(myMessage);
    }
}
