import java.lang.Math;

/**
 * This class represents a runner ship, which  attempts to run away from the fight. It will always accelerate,
 * and will constantly turn away from the closest ship.
 *
 * @author Natalia Shuklin
 * @see SpaceShip
 */
public class RunnerShip extends SpaceShip{
    /**
     * represents a runner ship type, string "id" for this ships type
     */
    protected static final String RUNNER = "r";

    /*
     * needed distance between ship and closest ship inorder to perform action
     */
    private static final double DISTANCE = 0.25;

    /*
     * needed angle between ship and closest ship inorder to perform action
     */
    private static final double ANGLE = 0.23;

    /**
     * Default constructor
     */
    public RunnerShip() {
        healthLvl = START_HEALTH;
        maxEnergyLvl = MAX_ENERGY;
        curEnergyLvl = CUR_ENERGY;
        shipType = RUNNER;
        SpaceShip.shipCounter++;
        shipNumber = SpaceWars.RUNNER_SHIP;
    }

    /**
     * Performs the action for the ship in the game by the game rules
     * abstract method from SpaceShip.
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        shieldsOn = false;
        SpaceShip closest = game.getClosestShipTo(this);
        double angle = closest.getPhysics().angleTo(shipPhysics);
        double posAngle = Math.abs(angle);
        if (closest != null) {
            if (closest.getPhysics().distanceFrom(shipPhysics) < DISTANCE && posAngle < ANGLE) {
                this.teleport();
                shipPhysics.move(true, NO_TURN);
            } else {
                if (angle < 0) {
                    shipPhysics.move(true, LEFT_TURN);
                } else if (angle > 0) {
                    shipPhysics.move(true, RIGHT_TURN);
                } else {
                    shipPhysics.move(true, NO_TURN);
                }
            }
        }
        else {
            shipPhysics.move(true, NO_TURN);
        }
        energyPlusOne();
        countRounds();
    }
}
