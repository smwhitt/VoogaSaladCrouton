package voogasalad.DataLoadingAPI;

import java.util.List;

public interface pathCreation {

    /**
     * The pathPoints object (list of list of booleans) defines where the path lies, and where minions are allowed to travel
     * Path points marked with True at specific locations can
     * @param pathPoints
     */

    void addPath(List<List<Boolean>> pathPoints);



}
