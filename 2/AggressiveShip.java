import javax.swing.*;

/**
 * This class represents an Aggressive Ship, which pursues other ships and tries to fire at them. It will
 * always accelerate, and turn towards the nearest ship.
 * @author Natalia Shuklin
 * @see SpaceShip
 */
public class AggressiveShip extends SpaceShip {
    /**
     * represents an aggressive  ship type, string "id" for this ships type
     */
    protected static final String AGGRESSIVE = "a";

    /*
     * angle from nearest ship condition, in order to perform action
     */
    private static final double ANGLE = 0.21;

    /**
     * default constructor
     */
    public AggressiveShip() {
        healthLvl = START_HEALTH;
        maxEnergyLvl = MAX_ENERGY;
        curEnergyLvl = CUR_ENERGY;
        shipType = AGGRESSIVE;
        SpaceShip.shipCounter++;
        shipNumber = SpaceWars.AGGRESSIVE_SHIP;
    }

    /**
     * perform the actions for the ship for current round.
     * an abstract method from SpaceShip
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        shieldsOn = false;
        SpaceShip closest = game.getClosestShipTo(this);
        double angle;
        double pos_angle;
        if (closest != null) {
            angle = shipPhysics.angleTo(closest.getPhysics());
            pos_angle = Math.abs(angle);
            if (pos_angle < ANGLE) {
                this.fire(game);
                if (angle < 0) {
                    shipPhysics.move(true, RIGHT_TURN);
                } else if (angle > 0) {
                    shipPhysics.move(true, LEFT_TURN);
                } else {
                    shipPhysics.move(true, NO_TURN);
                }
            }
            else if (angle < 0) {
                 shipPhysics.move(true, RIGHT_TURN);
             } else if (angle > 0) {
                 shipPhysics.move(true, LEFT_TURN);
             } else {
                 shipPhysics.move(true, NO_TURN);
             }

        } else {
            shipPhysics.move(true, NO_TURN);
        }
        energyPlusOne();
        countRounds();
    }
}
