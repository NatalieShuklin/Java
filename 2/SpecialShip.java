import java.util.Random;
import java.lang.Math;
/**
 * This class represents a special ship. This ship is trying to protect the "good" ships
 * of the game. It attempts to make justice in the game zone.
 * By attempting to kill the aggressive and basher ships, and attempting to protect the
 * other ships from possible kill.
 * @author Natalia Shuklin
 * @see SpaceShip
 */
public class SpecialShip extends SpaceShip{

    /**
     * represents a special ship type, string "id" for this ships type
     */
    protected static final String SPECIAL = "s";

    /*
     * decides when to shot at "air", for it's own protection
     */
    private static final int TAKE_SHOT = 79;

    /**
     * default constructor
     */
    public SpecialShip() {
        healthLvl = START_HEALTH;
        maxEnergyLvl = MAX_ENERGY;
        curEnergyLvl = CUR_ENERGY;
        shipType = SPECIAL;
        SpaceShip.shipCounter++;
        shipNumber = SpaceWars.SPECIAL_SHIP;
    }

    /**
     * performs ship's actions for the current round
     * this method is shipsapce's abstract method.
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        shieldsOn = false;
        SpaceShip closest = game.getClosestShipTo(this);
        double angle = closest.getPhysics().angleTo(this.getPhysics());
        if (closest.shipNumber == SpaceWars.BASHER_SHIP) {
            shieldOn();
            if(angle < 0) {
                this.getPhysics().move(true,RIGHT_TURN);
            }
            else {
                this.getPhysics().move(true,LEFT_TURN);
            }
            this.fire(game);
        }
        else if (closest.shipNumber == SpaceWars.AGGRESSIVE_SHIP) {
            shieldOn();
            if(angle < 0) {
                this.getPhysics().move(true,RIGHT_TURN);
            }
            else {
                this.getPhysics().move(true,LEFT_TURN);
            }
            this.fire(game);
        }
        else {
            if(angle < 0) {
                this.getPhysics().move(true,LEFT_TURN);
            }
            else if( angle > 0){
                this.getPhysics().move(true,RIGHT_TURN);
            }
            else {
                this.getPhysics().move(true,NO_TURN);
            }
            if((int)roundsCounter % TAKE_SHOT == 0)
            {
                shieldOn();
            }
        }
        energyPlusOne();
        countRounds();
    }
}
