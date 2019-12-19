# BUG 1
## Description

Minions currently destroy themselves in game
## Expected Behavior

Minions should not destroy themselves in game
## Current Behavior

Minions currently destroy themselves in game
## Steps to Reproduce

 1. Create game with enemy, spawnpoint, wave, level, and map.
 2. Load XML and run game


## Fix for Bug

Test: Using spawningsystem, spawn a minion and see if it is killed after two update calls.
Fix: Add a check in CollisionSystem.java to ignore dealing damage if two IDs are the same


# BUG 2
## Description

HealthSystem is throwing ConcurrentModificationException when running
## Expected Behavior

HealthSystem should be deleting entities that have health less than 1.
## Current Behavior

HealthSystem does not work due to exception thrown.

## Steps to Reproduce

Provide detailed steps for reproducing the issue.

 1. Run a game where a projectile intersects with a minion
 2. Exception will be thrown if projectile damage is greater than minion health

## Failure Logs

ConcurrentModificationException
## Fix for Bug

Test: Current SystemsManager test to delete entity will suffice
Fix: Loop twice; once over Health components and second time through all IDs flagged by first loop as being delete-ready