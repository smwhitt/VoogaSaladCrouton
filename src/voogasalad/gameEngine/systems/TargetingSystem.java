package voogasalad.gameEngine.systems;


import voogasalad.gameEngine.components.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Targeting class: System class for defenses to target enemies
 *
 * @author Alex Qiao, Michael Head
 */
public class TargetingSystem extends ComponentSystem {

    private Map<Integer, Type> myTypes;
    private Map<Integer, Health> myHealths;
    private Map<Integer, Location> myLocations;
    private Map<Integer, Angle> myAngles;
    private Map<Integer, AI> myAIs;
    private int maxID=-1, minID=-1, closeID = -1, farID = -1;
    private int minHealth=999999999, maxHealth=0, closeDistance = 999999999, farDistance = 0;

    /**
     * System for handling projectile movement and damage delivered
     * @param types : SystemsManager manager
     * @param healths : Health components
     */

    public TargetingSystem(Map<Integer, Type> types,
                           Map<Integer, Health> healths,
                           Map<Integer, Location> locations,
                           Map<Integer, Angle> angles,
                           Map<Integer, AI> AIs) {
        myTypes = types;
        myHealths = healths;
        myLocations = locations;
        myAngles = angles;
        myAIs = AIs;
    }

    /**
     * Iterates through defenses and updates their angle to face the right target based on AI
     */

    @Override
    public void updateSystem(double dt) {
        targetWeakest();
        targetStrongest();
        targetClose();
        targetFar();
        updateAngles();

    }

    private Consumer<Location> targetFar() {
        return location -> {
            double x = location.getX();
            double y = location.getY();
            myLocations.values().stream()
                    .filter(t -> ! t.equals(location))
                    .max(Comparator.comparing(t -> Math.sqrt(Math.pow(t.getX()-x,2) + Math.pow(t.getY()-y,2))))
                    .ifPresentOrElse(t -> farID = t.owner(),() -> farID=-1);
        };
    }

    private Consumer<Location> targetClose() {
//        double mindist = 10000;
//        for (int id : myTypes.keySet()){
//            if (myTypes.get(id).getType().equalsIgnoreCase("minion") && myLocations.containsKey(id)){
//                double x = myLocations.get(id).getX();
//                double y = myLocations.get(id).getY();
//                for (int ID : myAIs.keySet()){
//                    if (myLocations.containsKey(ID)) {
//                        double dist = Math.sqrt(Math.pow(x - myLocations.get(ID).getX(), 2) + Math.pow(y - myLocations.get(ID).getY(),2));
//                        if(dist<mindist){
//                            closeID = id;
//                            mindist=dist;
//                        }
//                    }
//                }
//            }
//        }
        return location -> {
            double x = location.getX();
            double y = location.getY();
            myLocations.values().stream()
                    .filter(t -> ! t.equals(location))
                    .min(Comparator.comparing(t -> Math.sqrt(Math.pow(t.getX()-x,2) + Math.pow(t.getY()-y,2))))
                    .ifPresentOrElse(t -> closeID = t.owner(),() -> closeID=-1);
        };
    }

    private void targetStrongest() {
        myHealths.values().stream()
                .filter(t -> myLocations.containsKey(t.owner()))
                .filter(t -> myTypes.get(t.owner()).getType().equalsIgnoreCase("minion"))
                .max(Comparator.comparing(Health::getHealth))
                .ifPresentOrElse(t -> maxID = t.owner(),() -> maxID=-1);
    }

    private void targetWeakest() {
        myHealths.values().stream()
                .filter(t -> myLocations.containsKey(t.owner()))
                .filter(t -> myTypes.get(t.owner()).getType().equalsIgnoreCase("minion"))
                .min(Comparator.comparing(Health::getHealth))
                .ifPresentOrElse(t -> minID = t.owner(),() -> minID=-1);
    }


    private void updateAngles() {
        for (Angle a : myAngles.values()) {
            int ID = a.owner();
            if (myLocations.containsKey(ID)
                    && myAIs.containsKey(ID)
                    && myTypes.containsKey(ID)
                    && (myTypes.get(ID).getType().equalsIgnoreCase("defense")
                    || (myTypes.get(ID).getType().equalsIgnoreCase("projectiles")))) {

                Map<String,Integer> targets = Map.of("findstrong",maxID,"findweak",minID, "findfirst",closeID,"findlast",farID);

                String aiType = myAIs.get(ID).getAIType();
                Location minionLoc = myLocations.get(targets.get(aiType));
                Location defenseLoc = myLocations.get(ID);

                targetClose().accept(defenseLoc);
                targetFar().accept(defenseLoc);
                if (targets.get(aiType)==-1){continue;}
                if(minionLoc != null) {
                    double dx = minionLoc.getX() - defenseLoc.getX();
                    double dy = minionLoc.getY() - defenseLoc.getY();
                    double angle = Math.toDegrees(Math.atan2(dy,dx));
                    a.updateAngle(angle+90);
                }
            }
        }
    }

}
