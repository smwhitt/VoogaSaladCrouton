package voogasalad.gameEngine.systems;

import voogasalad.gameEngine.components.*;
import voogasalad.gameEngine.exceptions.*;

import java.util.Map;

/**
 * UpgradeSystem class: System class managing the upgrades of various entities
 *
 * @author Alex Qiao
 */
public class UpgradeSystem extends ComponentSystem{

    private Map<Integer, Cost> myCosts;
    private Map<Integer, Resource> myResources;
    private Map<Integer, Damage> myDamages;
    private Map<Integer, Upgrade> myUpgrades;
    private Game myGame;


    public UpgradeSystem(Map<Integer, Cost> costs, Map<Integer, Resource> resources, Map<Integer, Damage> damages, Map<Integer, Upgrade> upgrades, Game game) {
        myCosts = costs;
        myResources = resources;
        myDamages = damages;
        myUpgrades = upgrades;
        myGame = game;
    }

    /**
     * method that takes the id of the item to be upgraded and the id of the player calling the upgrade
     * the method checks to see if the item can be upgraded and if the specific player has enough money to upgrade the object
     * if both conditions are met, the player upgrades the object and loses some money - otherwise, exceptions are thrown
     * @param entityIDOfUpgradedObject
     * @throws NotEnoughMoneyException
     * @throws ItemNotUpgradableException
     */

    public void upgrade(int entityIDOfUpgradedObject) throws NotEnoughMoneyException, ItemNotUpgradableException {

        //&& myDamages.containsKey(entityIDOfUpgradedObject)
        if (myCosts.containsKey(entityIDOfUpgradedObject) && myUpgrades.containsKey(entityIDOfUpgradedObject)){


            // FIXME: 12/7/19 We can potentially delete the cost map, because that is only used for the first purchase

            int entityIDofUpgrader = myGame.getResourceid();
            var userUpgrading = myResources.get(entityIDofUpgrader);
            System.out.println("Size of myResources" + myResources.size());
            System.out.println("entityIDofUPgrader" + entityIDofUpgrader);
            var upgradedObject = myUpgrades.get(entityIDOfUpgradedObject);

            //int price = myCosts.get(entityIDOfUpgradedObject).getCost();
            int price = upgradedObject.getUpgradecost();
            System.out.println(price);
            System.out.println(userUpgrading.getValue());
            if (userUpgrading.canBuy(price)){
                myDamages.get(entityIDOfUpgradedObject).incrementDamage(upgradedObject.getUpgradeincrement());
                userUpgrading.buy(price);
            } else {

                throw (new NotEnoughMoneyException(" "));

            }

        }

        else {
            throw (new ItemNotUpgradableException(" "));
        }
    }


    @Override
    public void updateSystem(double dt) {

    }
}
