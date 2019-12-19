package voogasalad.gameEngine.exceptions;

public class CannotLoadXMLException extends PropertyExceptions {
    private static final String myMessage = "CannotLoadXML";
    public CannotLoadXMLException(String errormessage) {
        super(myMessage);
    }
}
