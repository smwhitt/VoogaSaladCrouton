package voogasalad.gameEngine.systems;

import voogasalad.gameEngine.components.Collision;
import voogasalad.gameEngine.components.Location;

import java.util.Map;
import java.util.TreeMap;

/**
 * abstract class for a component system with protected methods used by both pathing and collisions sytems
 * @authors Micheal Head, Milan Shah, Lakshya Bakshi
 */
public abstract class ComponentSystem {

    public abstract void updateSystem(double dt);

    Map<String, Double> getBounds(Map<Integer, Collision> boundingBoxes, Map<Integer, Location> locations, Integer id) {
        Map<String, Double> bounds = new TreeMap<>();
        Collision currentBox=boundingBoxes.get(id);
        Location currentLoc = locations.get(id);
        double x = currentLoc.getX();
        double y = currentLoc.getY();
        double width= currentBox.getWidth();
        double height = currentBox.getHeight();
        bounds.put("minx", x);
        bounds.put("miny", y);
        bounds.put("maxx", x+width);
        bounds.put("maxy", y+height);
        return bounds;
    }

    boolean isIntersection(Map<String, Double> map1, Map<String, Double> map2) {
        double midx1 = (map1.get("maxx") + map1.get("minx"))/2;
        double midy1 = (map1.get("maxy") + map1.get("miny"))/2;
        double minx1 = map1.get("minx");
        double miny1 = map1.get("miny");
        double maxx2 = map2.get("maxx");
        double maxy2 = map2.get("maxy");
        double minx2 = map2.get("minx");
        double miny2 = map2.get("miny");
        return (midx1 <= maxx2 && midx1 >= minx2 && midy1 <=maxy2 && midy1 >= miny2);
    }
}
