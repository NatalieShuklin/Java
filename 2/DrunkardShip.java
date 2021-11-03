
import java.util.Random;
/**
 * This class represents a drunkard ship, it moves in circles all the time, like it has no control
 * also, it might shoot you in surprise if you get too close. "close" in this ship meaning
 * because it's sight is blurry :)
 * @author  Natalia Shuklin
 * @see SpaceShip
 */
public class DrunkardShip extends SpaceShip {

    /**
     * represents the random range from which the random number is chosen, which decides
     * what action will the ship perform.
     */
    private static final int RANDOM_BOUND = 100;

    /**
     * The number that if diving it the random num chosen, decided to which action to procced for
     * this round in the game, if modulo division is 0 or else.
     */
    private static final int RANDOM_DIVISON = 49;

    /**
     * represents a drunkard ship type, string "id" for this ships type
     */
    protected static final String DRUNKARD = "d";

    /**
     * default constructor
     */
    public DrunkardShip() {
        healthLvl = START_HEALTH;
        maxEnergyLvl = MAX_ENERGY;
        curEnergyLvl = CUR_ENERGY;
        shipType = DRUNKARD;
        SpaceShip.shipCounter++;
        shipNumber = SpaceWars.DRUNKARD_SHIP;
    }

    /**
     * The program performs the action of the ship for the current round
     * this is spaceship's abstract method.
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        shieldsOn = false;
        Random random = new Random();
        int intRand = 0;
        double randomDistance;
        intRand = random.nextInt(RANDOM_BOUND);

        if (intRand % RANDOM_DIVISON != 0) {
            shipPhysics.move(true, RIGHT_TURN);
        }
        else {
            shipPhysics.move(true, RIGHT_TURN);
            randomDistance = random.nextDouble();
            if(this.getPhysics().distanceFrom(game.getClosestShipTo(this).getPhysics()) < randomDistance) {
                this.fire(game);
            }
        }
        energyPlusOne();
        countRounds();
    }
}
