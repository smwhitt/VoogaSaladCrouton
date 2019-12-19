package voogasalad.DataLoadingAPI;

public interface movementCreation {


    /**
     * This method will create a list of points that an object will try to traverse through over the period of the game.
     * An opportunity for the use of an AI, which can decide optimal paths for the object to traverse
     */
    void defineMovement();

}
