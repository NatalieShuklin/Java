import java.awt.Image;
import oop.ex2.*;

/**
 * An abstract class that represents Space Ship Object
 *  supports methods that can be performed on the ship for SpaceWars game
 *  can be used as "father" class of different ships types
 *  that support same methods
 * @author cs user: natashashuklin ,name: Natalia Shuklin
 * @see GameContracts
 */
public abstract class SpaceShip implements GameContracts {

    /**
     * Represents 1 (whole) round in the game, used for calculating rounds integer rounds
     */
    protected static final int ONE_ROUND = 1;
    /**
     * represents the right turn command number for move function in SHIP PHYSICS
     */
    protected static final int RIGHT_TURN = -1;
    /**
     * represents the left turn command number for move function in SHIP PHYSICS
     */
    protected static final int LEFT_TURN = 1;
    /**
     * represents the no turn command number for move function in SHIP PHYSICS
     */
    protected static final int NO_TURN = 0;
    /**
     * Counting the rounds in the game until game ends - resets to 0
     */
    protected static double roundsCounter = 1.0;
    //game attributes
    protected int maxEnergyLvl;
    protected int curEnergyLvl;
    protected int healthLvl;
    protected SpaceShipPhysics shipPhysics =  new SpaceShipPhysics();
    protected boolean shieldsOn ;
    //the minimum allowed round number (excluding) to take the next shot for the ship
    protected double allowedShotRound = 0.0;
    //the round when last time ship performed shot
    protected double roundShotAt = 0.0;
    //string type
    protected String shipType;
    //constant type given by SpaceWars class
    protected int shipNumber;

    /**
     * counts the number of ships created for the game
     */
    protected static int shipCounter = 0;

    /**
     * Does the actions of this ship for this round. 
     * This is called once per round by the SpaceWars game driver.
     * 
     * @param game the game object to which this ship belongs.
     */
    public abstract void doAction(SpaceWars game);

    /**
     * adding one point to current energy of ship, per each round
     */
    protected void energyPlusOne() {
        if (curEnergyLvl < MAX_ENERGY) {
            curEnergyLvl++;
        }
    }

    /**
     * check if there's enough current energy to perform the action wanted
     * @param pointsToReduce amount of energy points the action "costs"
     * @return true if there's enough of current energy to perform the action,
     * otherwise return false
     */
    protected boolean isEnoughCurEnergy(int pointsToReduce) {
        if(curEnergyLvl - pointsToReduce >= 0) {
            return true;
        }
        return false;
    }

    /**
     * performs round counting of the game, called from do Action of ship
     * it is called number of ship times per round
     * by adding 1/shipsCount, the round updated to next one, only after
     * current round finished == after all ships performed their actions for this round.
     */
    protected void countRounds() {
        roundsCounter += (double)ONE_ROUND/(double) shipCounter;
    }

    /**
     * This method is called every time a collision with this ship occurs 
     */
    public void collidedWithAnotherShip(){
        if(shieldsOn) {
            maxEnergyLvl += BASH_ENERGIES_UP;
            curEnergyLvl += BASH_ENERGIES_UP;
        }
        else {
            if(maxEnergyLvl - COLLIDE_ENRGY >= 0) {
                maxEnergyLvl -= COLLIDE_ENRGY;
            }
            else {
                maxEnergyLvl = 0;
            }
            if(curEnergyLvl > maxEnergyLvl) {
                curEnergyLvl = maxEnergyLvl;
            }
            healthLvl -= COLLIDE_HEALTH;
        }
    }

    /** 
     * This method is called whenever a ship has died. It resets the ship's 
     * attributes, and starts it at a new random position.
     */
    public void reset(){
        shipPhysics = new SpaceShipPhysics();
        curEnergyLvl = CUR_ENERGY;
        maxEnergyLvl = MAX_ENERGY;
        healthLvl = START_HEALTH;
        allowedShotRound = 0.0;
        roundShotAt = 0.0 ;
        shieldsOn = false;
    }

    /**
     * Checks if this ship is dead.
     * 
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        return healthLvl == 0;
    }

    /**
     * Gets the physics object that controls this ship.
     * 
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return shipPhysics;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {
        if(!shieldsOn) {
            if(maxEnergyLvl - SHOT_HIT_ENRGY >= 0) {
                maxEnergyLvl -= SHOT_HIT_ENRGY;
            }
            else {
                maxEnergyLvl = 0;
            }
           if(curEnergyLvl > maxEnergyLvl) {
               curEnergyLvl = maxEnergyLvl;
           }
           if( healthLvl > 0) {
               healthLvl -= SHOT_HEALTH;
           }
        }
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     * 
     * @return the image of this ship.
     */
    public Image getImage(){
        Image ship = null;
        switch (this.shipType) {
            case "h":
                if(this.shieldsOn) {
                    ship = GameGUI.SPACESHIP_IMAGE_SHIELD;
                }
                else {
                    ship = GameGUI.SPACESHIP_IMAGE;
                }
                break;
            case "r":
            case "a":
            case "b":
            case "d":
            case "s":
                if(this.shieldsOn) {
                    ship = GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
                }
                else {
                    ship = GameGUI.ENEMY_SPACESHIP_IMAGE;
                }
                break;
        }
        return ship;
    }

    /**
     * Attempts to fire a shot.
     * 
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
        if(isEnoughCurEnergy(REDUCE_ENRGY_FIRING) && allowedShotRound < roundsCounter) {
            game.addShot(shipPhysics);
            roundShotAt = roundsCounter;
            allowedShotRound = roundShotAt + ADD_FIRE_ROUND;
            curEnergyLvl -= REDUCE_ENRGY_FIRING;
        }
    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        if(isEnoughCurEnergy(REDUCE_ENRGY_SHIELD)) {
            shieldsOn = true;
            curEnergyLvl -= REDUCE_ENRGY_SHIELD;
        }
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
        if(isEnoughCurEnergy(TELEPORT_ENERGY)) {
            SpaceShipPhysics teleport_random = new SpaceShipPhysics();
            this.shipPhysics = null;
            this.shipPhysics = teleport_random;
            curEnergyLvl -= TELEPORT_ENERGY;
        }
    }
}
