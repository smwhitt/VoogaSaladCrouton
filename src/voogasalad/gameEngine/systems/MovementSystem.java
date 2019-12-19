package voogasalad.gameEngine.systems;

import voogasalad.gameEngine.components.Angle;
import voogasalad.gameEngine.components.Location;
import voogasalad.gameEngine.components.Speed;

import java.util.Map;

/**
 * Movement system for moving an object based on its speed, location, and heading
 * @authors Milan Shah, Lakshya Bakshi
 */
public class MovementSystem extends ComponentSystem{
    private static final int phase = 90;
    private Map<Integer,Speed> mySpeeds;
    private Map<Integer, Location> myLocations;
    private Map<Integer, Angle> myDirections;

    /**
     * System for handling projectile movement and damage delivered
     * @param speeds : Speed components
     * @param locations : Location components
     */
    public MovementSystem(Map<Integer,Speed> speeds, Map<Integer, Location> locations, Map<Integer, Angle> directions){
        mySpeeds = speeds;
        myLocations = locations;
        myDirections = directions;
    }

    /**
     * Iterates through all Location components in the game and if the entity has a speed and direction, the next
     * location is updated through basic polar-rectangular calculation, with a phase to account for axes inconsistencies between
     * frontend and backend
     */
    @Override
    public void updateSystem(double dt) {
        //Loop through all entities and update next position based on speed, location, and direction
        for (int ID : myLocations.keySet()){
            Location currentLocation = myLocations.get(ID);
            double currentx = currentLocation.getX();
            double currenty = currentLocation.getY();
            if (mySpeeds.containsKey(ID) && myDirections.containsKey(ID)){
                double currentangle = myDirections.get(ID).getAngle();
                int currentspeed = mySpeeds.get(ID).getSpeed();
                double nextx = currentx + currentspeed*dt*Math.cos(Math.toRadians(currentangle-phase));
                double nexty = currenty + currentspeed*dt*Math.sin(Math.toRadians(currentangle-phase));
                myLocations.get(ID).updateXPos(nextx);
                myLocations.get(ID).updateYPos(nexty);
            }
        }
    }
}
