package voogasalad.gameEngine.systems;

import voogasalad.gameEngine.components.*;

import java.util.*;

public class CollisionSystem extends ComponentSystem{

    private static final String FIRE = "Fire";
    private static final String ICE = "Ice";
    private static final String EXPLOSION = "Explosion";


    private static final String MINION = "Minion";
    private static final String PROJECTILE = "Projectile";
    private static final String TOWER = "Tower";


    private Map<Integer, Interaction> myInteractions;
    private Map<Integer, Location> myLocations;
    private Map<Integer, Collision> myBoundingBoxes;
    private Map<Integer, Health> myHealths;
    private Map<Integer, Damage> myDamage;
    private Map<Integer,Speed> mySpeeds;
    private Map<Integer, Type> myTypes;
    private Map<Integer,Affected> myAffected;


    //private HashSet<Pair<Integer,Integer>> pairs = new HashSet<>();

    private HashSet<Integer> entityOnFire = new HashSet<>();
    private HashSet<Integer> entityOnIce = new HashSet<>();

    private ArrayList<Integer> looperIce = new ArrayList<>();
    private ArrayList<Integer> looperFire = new ArrayList<>();

    /**
     * System for handling collisions between two entities, at least one of which deals damage. This system
     * will determine if a collision has occurred and will update the health of the appropriate entity.
     */

    public CollisionSystem(Map<Integer, Location> locations,
                           Map<Integer, Collision> collisionregions,
                           Map<Integer, Health> healths,
                           Map<Integer, Damage> damagers,
                           Map<Integer, Interaction> interactions, Map<Integer,Speed> speeds, Map<Integer,Type> types, Map<Integer,Affected> affected){

        myLocations = locations;
        myBoundingBoxes = collisionregions;
        myHealths = healths;
        myDamage = damagers;
        myInteractions =interactions;
        mySpeeds = speeds;
        myTypes = types;
        myAffected = affected;
    }

    // FIXME: 12/8/19 Find a better way to do temporary power effects

    /**
     * Checks intersection between two bounding boxes of entities and deals damage if one has Damager component and
     * other has Health component
     *
     * Additionally, the update system now also checks to see ICE and FIRE power ups and implements their abilities for a temporary period
     */
    @Override
    public void updateSystem(double dt) {

        looperIce = new ArrayList<>();
        looperFire = new ArrayList<>();


        for (Integer entityID : entityOnIce){
            if (myAffected.containsKey(entityID)){

                myAffected.get(entityID).decrement(dt);
                System.out.println("changed");
                if (myAffected.get(entityID).timeBelowZero()){
                    mySpeeds.get(entityID).setSpeed(myAffected.get(entityID).getOgSpeed());
                    //entityOnIce.remove(entityID);
                    looperIce.add(entityID);
                    myAffected.get(entityID).setIce(false);
                }

            }
            else{
                entityOnIce.remove(entityID);
                looperIce.add(entityID);
            }

        }

        for (Integer entityID : entityOnFire){

            if (myAffected.containsKey(entityID)){

                myAffected.get(entityID).decrement(dt);
                if (myAffected.get(entityID).timeBelowZero()){
                    //entityOnFire.remove(entityID);
                    looperFire.add(entityID);
                    myAffected.get(entityID).setFire(false);
                }
                myHealths.get(entityID).updateHealth(-2);

            } else {
                //entityOnFire.remove(entityID);
                looperFire.add(entityID);
            }

        }

      for (int i : looperFire){

          if (entityOnFire.contains(i)){
              entityOnFire.remove(i);
          }


      }

        for (int i : looperIce){

            if (entityOnIce.contains(i)){
                entityOnIce.remove(i);
            }

        }



//        System.out.println("MyAffected");
//        System.out.println(myAffected);
//        for (int i : myInteractions.keySet()){
//            System.out.println(myInteractions.get(i).getElementtype());
//            System.out.println(myTypes.get(i).getType());
//        }
//        System.out.println("MyInteractions");
//        System.out.println(myInteractions.size());




        for (int affected : myBoundingBoxes.keySet()) {
//            if (myLocations.containsKey(affected) && myHealths.containsKey(affected) && myAffected.containsKey(affected)) {
                if (myLocations.containsKey(affected) && myHealths.containsKey(affected)) {
                Map<String, Double> affectedBounds = getBounds(myBoundingBoxes, myLocations, affected);
                for (int killer : myBoundingBoxes.keySet()) {
                    if (isRealInteraction(affected,killer)){
                    if (myLocations.containsKey(killer) && myDamage.containsKey(killer)) {
                        Map<String, Double> killerBounds = getBounds(myBoundingBoxes, myLocations, killer);
                        if (isIntersection(affectedBounds, killerBounds)) {
                            if (killer == affected){continue;}

                            //Pair<Integer,Integer> mypair = new Pair(killer,affected);

//                            boolean affectedExplodes = myInteractions.containsKey(affected)
//                                    && ! myInteractions.get(affected).hasExploded();
                            boolean killerExplodes = myInteractions.containsKey(killer) && myInteractions.get(killer).getElementtype() == EXPLOSION
                                    && ! myInteractions.get(killer).hasExploded();


                            // another parameter that may be used && !pairs.contains(mypair)

                            if (myInteractions.containsKey(killer)){
                                System.out.println(myInteractions.get(killer).getElementtype());
                                if (myInteractions.get(killer).getElementtype().equals(ICE)){
                                    System.out.println("Ice");
                                    if (mySpeeds.containsKey(affected)){
                                        entityOnIce.add(affected);
                                        myAffected.get(affected).setOgSpeed(mySpeeds.get(affected).getSpeed());
                                        mySpeeds.get(affected).setSpeed((int)(mySpeeds.get(affected).getSpeed()/1.2));
                                        myAffected.get(affected).setIce(true);
                                    }
                                } else if (myInteractions.get(killer).getElementtype().equals(FIRE)){
                                    System.out.println("Fire");
                                    //myHealths.get(affected).updateHealth(-5);
                                    entityOnFire.add(affected);
                                    myAffected.get(affected).setFire(true);
                                }
                                myAffected.get(affected).resetTimer();
                            }

                            // FIXME: 12/8/19 Michael, can you make sure your logic is correct

                            if (killerExplodes) {

                                    Collision killerBox = myBoundingBoxes.get(killer);
                                    Damage killerDamage = myDamage.get(killer);
                                    Interaction killerInteraction = myInteractions.get(killer);

                                    killerBox.setWidth(killerInteraction.getExplodedWidth());
                                    killerBox.setHeight(killerInteraction.getExplodedHeight());
                                    killerDamage.setDamage(killerInteraction.getExplodedDamage());

                                    killerInteraction.setExploded(true);

                            } else {
                                Health affectedHealth = myHealths.get(affected);
                                int projectileDamage = myDamage.get(killer).getDamageRating();
                                affectedHealth.updateHealth(-projectileDamage);
                            }

                        }
                    }
                }
                }
            }
        }
    }

    private boolean isRealInteraction(int affected, int killer) {
        return (myTypes.get(affected).getType().equals(MINION) && myTypes.get(killer).getType().equals(PROJECTILE)) ||
                (myTypes.get(affected).getType().equals(TOWER) && myTypes.get(killer).getType().equals(MINION)) ||
                (myTypes.get(affected).getType().equals(PROJECTILE) && myTypes.get(killer).getType().equals(MINION));

    }

}
        /*for (int ID : myBoundingBoxes.keySet()){
            double width = myBoundingBoxes.get(ID).getWidth();
            double height = myBoundingBoxes.get(ID).getHeight();
            if (myLocations.containsKey(ID) && myHealths.containsKey(ID)){
                double minx = myLocations.get(ID).getX();
                double miny = myLocations.get(ID).getY();
                double maxx = myLocations.get(ID).getX()+width;
                double maxy = myLocations.get(ID).getY()+height;

                for (int id : myBoundingBoxes.keySet()){
                    double projwidth = myBoundingBoxes.get(id).getWidth();
                    double projheight = myBoundingBoxes.get(id).getHeight();
                    if (id==ID){continue;}
                    if (myLocations.containsKey(id) && myDamage.containsKey(id)){
                        double projminx = myLocations.get(id).getX();
                        double projminy = myLocations.get(id).getY();
                        double projmaxx = myLocations.get(id).getX() +projwidth;
                        double projmaxy = myLocations.get(id).getY()+projheight;
                        if ((projmaxx<maxx && projmaxx>minx && projmaxy <maxy && projmaxy>miny) || (projminx<maxx && projminx>minx && projminy <maxy && projminy>miny)) {
                            myHealths.get(ID).updateHealth(-myDamage.get(id).getDamageRating());
                        }
                    }
                }
            }
        }
    }
} */
