package voogasalad.gameEngine.systems;
import voogasalad.gameEngine.components.*;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Pathing system to move minions based on the flags and directions of the paths beneath them
 * @author Lakshya Bakshi
 */
public class PathingSystem extends ComponentSystem{
    private static final double turnThreshold = 0.5;
    private static final String INACCESSIBLE_FLAG="Inaccessible";
    private static final String ACCESSIBLE_FLAG="Accessible";
    private static final String START_FLAG = "Start";
    private static final String END_FLAG = "End";
    private Map<Integer, Location> locations;
    private Map<Integer, Collision> boundingBoxes;
    private Map<Integer, Path> paths;
    private Map<Integer, Angle> angles;
    private Map<Integer, Type> types;


    public PathingSystem(Map<Integer, Location> locations,
                         Map<Integer, Collision> boundingBoxes,
                         Map<Integer, Path> paths,
                         Map<Integer, Angle> angles,
                         Map<Integer, Type> types) {
        this.locations=locations;
        this.boundingBoxes=boundingBoxes;
        this.paths=paths;
        this.types = types;
        this.angles = angles;
    }

    @Override
    public void updateSystem(double dt) {
        Set<Integer> minionIDs = types.keySet().stream()
                .filter(c->locations.containsKey(c)&&locations.get(c).getX()>0&&types.get(c).getType().equalsIgnoreCase("minion"))
                .collect(Collectors.toSet());
//        System.out.println("Minion ids: "+minionIDs.size());
        for (Integer minionIDS: minionIDs) {
            for (Integer pathIDs: paths.keySet()) {
            Map<String, Double> pathBounds=getBounds(boundingBoxes, locations, pathIDs);
                if (isIntersection(getBounds(boundingBoxes, locations, minionIDS),pathBounds)) {
                    String pathType=paths.get(pathIDs).getPathType();
                    changeMinionAngle(pathType,minionIDS,pathIDs);
                    break;
                }
            }
        }
    }
    private void changeMinionAngle(String pathType, int minionID, int pathID) {
        Location minionLoc=locations.get(minionID);
        Double pathCenterX = locations.get(pathID).getX()+boundingBoxes.get(pathID).getWidth()/2;
        Double pathCenterY = locations.get(pathID).getY()+boundingBoxes.get(pathID).getWidth()/2;
        Double minionCenterX= locations.get(minionID).getX()+boundingBoxes.get(minionID).getWidth()/2;
        Double minionCenterY= locations.get(minionID).getY()+boundingBoxes.get(minionID).getWidth()/2;
        if (pathType.equalsIgnoreCase(START_FLAG)) {
            angles.get(minionID).updateAngle(angles.get(pathID).getAngle());
        }
        if (pathType.equalsIgnoreCase(END_FLAG)) {
            angles.get(minionID).updateAngle(vectorAngle(minionCenterX, minionCenterY, pathCenterX, pathCenterY));
        }
        if (pathType.equalsIgnoreCase(INACCESSIBLE_FLAG)){//||pathType.equalsIgnoreCase(END_FLAG)) {
            angles.get(minionID).updateAngle(-angles.get(minionID).getAngle());
        }
        if (pathType.equalsIgnoreCase(ACCESSIBLE_FLAG)) {
            angles.get(minionID).updateAngle(weightedAngle(angles.get(minionID).getAngle(),angles.get(pathID).getAngle(),minionCenterX, minionCenterY, pathCenterX, pathCenterY, locations.get(pathID).getX(), locations.get(pathID).getY()));
        }
    }
    private double vectorAngle(double minionx, double miniony, double pathCenterX, double pathCenterY) {
        return Math.atan2(pathCenterY-miniony, pathCenterX-minionx) - 90;
    }

    private double weightedAngle(double projTheta, double pathTheta, double minionx, double miniony, double pathCenterX, double pathCenterY, double pathX, double pathY) {
        double weight=distance(minionx,miniony,pathCenterX,pathCenterY)/distance(pathCenterX,pathCenterY, pathX, pathY);
        return (weight<turnThreshold)?pathTheta:projTheta;
    }

    private double distance(double minionx, double miniony, double pathx, double pathy) {
        return Math.sqrt(Math.pow(minionx-pathx,2)+Math.pow(miniony-pathy,2));
    }
}
