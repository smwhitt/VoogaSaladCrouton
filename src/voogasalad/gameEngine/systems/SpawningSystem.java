package voogasalad.gameEngine.systems;

import voogasalad.gameEngine.Entity;
import voogasalad.gameEngine.SystemsManager;
import voogasalad.gameEngine.components.*;

import java.util.HashMap;
import java.util.Map;

public class SpawningSystem extends ComponentSystem {
    private Map<Integer, Location> myLocations;
    private Map<Integer, Spawner> mySpawners;
    private Map<Integer, Angle> myAngles;
    private Map<Integer, Double> myElapsedTime;
    private Map<Integer, ActiveStatus> myStatuses;
    private Map<Integer, SpawnerLimit> myLimits;
    private double time;
    private SystemsManager myManager;
    private Collision box;

    /**
     * SpawningSystem is used to create new Entity instances based on the available Spawners in the game. It requires access to the manager since
     * Entity instances are being created and destroyed. The SpawningSystem also keeps track of time in game and determines when to spawn based on a Spawner's frequency
     * and SpawnerLimit value.
     * @author Milan Shah
     * @param manager
     * @param locs
     * @param spawns
     * @param dirs
     * @param stats
     * @param limits
     * @param box
     */
    public SpawningSystem(SystemsManager manager, Map<Integer, Location> locs, Map<Integer, Spawner> spawns, Map<Integer, Angle> dirs, Map<Integer, ActiveStatus> stats, Map<Integer, SpawnerLimit> limits, Collision box){
        myLocations=locs;
        mySpawners=spawns;
        myStatuses = stats;
        myLimits=limits;
        myAngles=dirs;
        myManager = manager;
        this.box = box;
        myElapsedTime = new HashMap<>();
        time = myManager.getElapsedTime();
        mySpawners.keySet().forEach(c -> myElapsedTime.put(c, time));
    }

    /**
     * Checks if proper amount of time has elapsed for an entity to spawn from a spawner
     * @param dt : Elapsed time
     */
    @Override
    public void updateSystem(double dt) {
        time+=dt;
        for (int ID : mySpawners.keySet()){
            myElapsedTime.putIfAbsent(ID, time);
            if (myStatuses.get(ID).getStatus()){
                double period = 1/mySpawners.get(ID).getFrequency();
                double difference = time - myElapsedTime.get(ID);
                if(difference>period){
                    // Checks if the spawner has a limit and if the limit is met.
                    myElapsedTime.put(ID, time);
                    if (myLimits.containsKey(ID)){
                        if (myLimits.get(ID).getNumberToSpawn()>0){myLimits.get(ID).reduceSpawnable();}
                        else if (myLimits.get(ID).getNumberToSpawn()==0){
                            myStatuses.get(ID).setStatus(false);
                            continue;
                        }else{continue;}
                    }

                    Entity instance = myManager.copyEntity(mySpawners.get(ID).getEntityToSpawn());
                    instance.addComponent(new Location(myLocations.get(ID).getX()+"", myLocations.get(ID).getY()+""));
                    instance.addComponent(new Collision(box.getWidth()+"", box.getHeight()+""));
                    instance.addComponent(new Affected());
                    //instance.addComponent(new Interaction());
                    if (myAngles.containsKey(ID)){instance.addComponent(
                            new Angle(myAngles.get(ID).getAngle()+"")
                    );}
                    else{instance.addComponent(new Angle("0.0"));}
                    myManager.addEntity(instance);

                }
            }
        }

    }
}
