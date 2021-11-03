/**
 * this class represents a basher ship, which attempts to collide with other ships.
 * It will always accelerate, and will
 * constantly turn towards the closest ship.
 * @author Natalia Shuklin
 * @see SpaceShip
 */
public class BasherShip extends SpaceShip {
    /**
     * represents a basher ship type, string "id" for this ships type
     */
    protected static final String BASHER = "b";

    /*
     * the distance needed to be between the ship and closes ship
     * in order to perform action
     */
    private static final double DISTANCE = 0.19;

    /**
     * default constructor
     */
    public BasherShip() {
        healthLvl = START_HEALTH;
        maxEnergyLvl = MAX_ENERGY;
        curEnergyLvl = CUR_ENERGY;
        shipType = BASHER;
        SpaceShip.shipCounter++;
        shipNumber = SpaceWars.BASHER_SHIP;
    }

    /**
     * performs the actions of the ship for this round of game
     * this is an abstract methos of SpaceShip
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        shieldsOn = false;
        SpaceShip closest = game.getClosestShipTo(this);
        double angle = shipPhysics.angleTo(closest.getPhysics());

        if (closest != null) {
            if (shipPhysics.distanceFrom(closest.getPhysics()) <= DISTANCE) {
                this.shieldOn();
                if (angle < 0) {
                    shipPhysics.move(true, RIGHT_TURN);
                } else if (angle > 0) {
                    shipPhysics.move(true, LEFT_TURN);
                } else {
                    shipPhysics.move(true, NO_TURN);
                }
            }
            else {
                if (angle < 0) {
                    shipPhysics.move(true, RIGHT_TURN);
                } else if (angle > 0) {
                    shipPhysics.move(true, LEFT_TURN);
                } else {
                    shipPhysics.move(true, NO_TURN);
                }
            }
        } else {
            shipPhysics.move(true, NO_TURN);
        }
        energyPlusOne();
        countRounds();
    }
}
