/**
 * This interface is the "contract" of the Ship Wars game
 * Keeping the attributes of each ship: energy, health, reduction points
 * it decides on initial state of ship, and the rules of amount of points to reduce
 * or add if some action happened during the game
 * @author Natalia Shuklin
 */
public interface GameContracts {

    /**
     * The initial health value of a ship at game start or ship rebirth, health is between 0 and 22
     */
    static final int START_HEALTH = 22;
    /**
     * the maximum energy of a ship at game start or ship rebirth
     */
    static final int MAX_ENERGY = 210;
    /**
     * The current energy - starting value at game start or ship rebirth
     */
    static final int CUR_ENERGY = 190;
    /**
     * Number of points to reduce from current energy of ship when performed teleport action
     */
    static final int TELEPORT_ENERGY = 140;
    /**
     * Number of points to reduce from current energy of ship when performed "Shield on" action
     */
    static final int REDUCE_ENRGY_SHIELD = 3;
    /**
     * Number of points to reduce from current energy of ship when performed "fire" action
     */
    static final int REDUCE_ENRGY_FIRING = 19;
    /**
     *Number of points to add to: Maximum energy level and current energy lvl of the ship, when its bashing
     */
    static final int BASH_ENERGIES_UP = 18;
    /**
     * Number of points to reduce from maximum energy of ship when being collided and shields are down
     */
    static final int COLLIDE_ENRGY = 10;
    /**
     * Number of points to reduce from maximum energy of ship when ship got shot
     */
    static final int SHOT_HIT_ENRGY = 10;
    /**
     * Number of rounds to wait until allowed to ship to fire a shot again
     */
    static final int ADD_FIRE_ROUND = 7;
    /**
     * Number of points to reduce from ship's health when it was shot
     */
    static final int SHOT_HEALTH = 1;
    /**
     * Number of points to reduce from ship's health if it collided with another ship
     */
    static final int COLLIDE_HEALTH = 1;


}
