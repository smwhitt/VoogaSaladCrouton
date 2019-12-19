package voogasalad.gameEngine.systems;

import voogasalad.gameEngine.SystemsManager;
import voogasalad.gameEngine.components.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

/**
 * System to start and transistion between levels, as well as giving spawners to relevant entities in need
 * @author Milan Shah
 */
public class ProgressionSystem extends ComponentSystem {

    private Map<Integer, Level> myLevels;
    private Map<Integer, Wave> myWaves;
    private Map<Integer, ActiveStatus> myStatuses;
    private Map<Integer, Type> myTypes;
    private Map<Integer, SpawnerLimit> myLimits;
    private Map<Integer, GameMap> myMaps;
    private Game game;
    private Map<Integer, Location> locations;
    private SystemsManager manager;
    private Map<Integer, Path> paths;

    private List<Integer> wavelist;
    private int currentwave;
    private int currentlevelid;
    private int currentlevel=0;
    private List<Integer> currentspawns;
    private int waveindex = 0;
    private double tilewidth;


    /**
     * The Progression System handles which levels, waves, and spawnpoints are active. It requires the maps of these components in addition
     * to ActiveStatus map and AI.java map so that it can check if the next wave/level are to be activated and the current wave/level deactivated
     * @param levels : Map of entity IDS to Level components
     * @param waves : Map of IDS to Wave components
     * @param types
     * @param stats
     * @param limits
     */
    public ProgressionSystem(Map<Integer, Level> levels, Map<Integer,Wave> waves, Map<Integer, Type> types, Map<Integer, ActiveStatus> stats, Map<Integer, SpawnerLimit> limits,
                             Game game, Map<Integer, GameMap> maps, SystemsManager manager, Map<Integer, Location> locs, Map<Integer, Path> paths,double tilewidth){
        myLevels=levels;
        myWaves=waves;
        myStatuses = stats;
        myTypes = types;
        myLimits = limits;
        myMaps = maps;
        this.game = game;
        this.manager = manager;
        locations = locs;
        this.paths = paths;
        this.tilewidth = tilewidth;
        //FIXME: NEED TO MAKE SURE FIRST LEVEL IS SET AS ACTIVE

    }

    public void initGame(Game game){
        this.game = game;
        currentlevelid = this.game.getLevelids().get(currentlevel);
        myStatuses.get(currentlevelid).setStatus(true);
        wavelist = myLevels.get(currentlevelid).getWaves();
        currentwave = wavelist.get(waveindex);
        myStatuses.get(currentwave).setStatus(true);
        currentspawns= myWaves.get(currentwave).getSpawns();
        for (int id : currentspawns){
            myStatuses.get(id).setStatus(true);
        }
        setupCurrentMap();
    }

    /**
     * Checks if the current wave is still spawning enemies. If this is the case, the wave will not progress to the next one.
     * If there are no more enemies being spawned or active enemies, the system updates the wave to the next and activates
     * the corresponding spawnpoints
     * @param dt : Elapsed time
     */
    @Override
    public void updateSystem(double dt) {
        boolean triggernext = true;
        //Sets next to FALSE only if there are no enemies AND there are no spawnpoints left that are active
        for (int id: myLimits.keySet()){
            for (int ID : myTypes.keySet()){
                if (myStatuses.get(id).getStatus() || (myTypes.get(ID).getType().equalsIgnoreCase("minion") && locations.containsKey(ID))){
                    triggernext = false;
                }
            }
        }

        if (triggernext){
            waveindex++;
            game.pause();
            // Checks if the completed wave is the last wave in a level. If so the level changes
            if (waveindex==wavelist.size()){
                waveindex=0;
                myStatuses.get(currentlevelid).setStatus(false);
                currentlevel++;
                currentlevelid = game.getLevelids().get(currentlevel);
                //currentlevelid = myLevels.get(currentlevelid).getNextLevelID();
                wavelist = myLevels.get(currentlevelid).getWaves();
                setupCurrentMap();
            }
            //Sets the current wave to the next wave and activates all the spawnpoints for that wave
            myStatuses.get(currentwave).setStatus(false);
            currentwave = wavelist.get(waveindex);
            myStatuses.get(currentwave).setStatus(true);
            currentspawns = myWaves.get(currentwave).getSpawns();
            for(int id:currentspawns){
                myStatuses.get(id).setStatus(true);
            }
        }
    }

    private void setupCurrentMap(){
        for (int pathid : myMaps.get(myLevels.get(currentlevelid).getLevelMap()).getPathIDs()){
            myStatuses.get(pathid).setStatus(true);
        }
        Location towerplace = null;
        for (int pathid : myMaps.get(myLevels.get(currentlevelid).getLevelMap()).getPathIDs()){
            if (paths.get(pathid).getPathType().equalsIgnoreCase("end")){
                towerplace = new Location(locations.get(pathid).getX()+"", locations.get(pathid).getY()+"");
            }
        }
        double pathdim = tilewidth;
        int towerid = myLevels.get(currentlevelid).getTowerid();
        manager.addComponent(new Collision(tilewidth+"", tilewidth+""), towerid);
        manager.addComponent(towerplace, towerid);
    }


}
