package voogasalad.gameEngine.exceptions;

public class CouldNotFindFileException extends PropertyExceptions {
    private static final String myMessage = "CouldNotFindFile";
    public CouldNotFindFileException(String errormessage) {
        super(myMessage);
    }
}
