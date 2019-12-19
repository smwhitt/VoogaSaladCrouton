package voogasalad.gameEngine.exceptions;

public class FileCouldNotConvertException extends PropertyExceptions {
    private static final String myMessage = "FileCouldNotConvert";
    public FileCouldNotConvertException(String errormessage) {
        super(myMessage);
    }
}
