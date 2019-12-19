package voogasalad.gameEngine.exceptions;

public class FileNotCreatedException extends PropertyExceptions {
    private static final String myMessage = "FileNotCreated";
    public FileNotCreatedException(String errormessage) {
        super(myMessage);
    }
}
