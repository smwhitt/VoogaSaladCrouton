package voogasalad.gameEngine.exceptions;

public class TileImageNotFoundException extends PropertyExceptions {
    private static final String myMessage = "TileImageNotFound";
    public TileImageNotFoundException(String errormessage) {
        super(myMessage);
    }
}
