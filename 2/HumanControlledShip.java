/**
 * A class that represents a ship that is human controlled
 * the ship is controlled by the user. The ship has control keys
 * which the user can use in order to perform different actions.
 * @author Natalia Shuklin
 * @see SpaceShip
 */
public class HumanControlledShip extends SpaceShip{
    /**
     * represents a human control ship type, string "id" for this ships type
     */
    protected static final String HUMAN_CONTROL = "h";

    /**
     * default constructor for the Human Controlled ship
     */
    public HumanControlledShip(){
        healthLvl = START_HEALTH;
        maxEnergyLvl = MAX_ENERGY;
        curEnergyLvl = CUR_ENERGY;
        shipType = HUMAN_CONTROL;
        SpaceShip.shipCounter++;
        shipNumber = SpaceWars.HUMAN_CONTROLLED_SHIP;
    }

    /**
     * performs the actions for the human controlled ship
     * called once per round by SpaceWars game driver, by defined order
     * through this function, abstract method from SpaceShip.
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        shieldsOn = false;
        if(game.getGUI().isTeleportPressed()) {
            this.teleport();
        }
        if(game.getGUI().isUpPressed()) {
            if(game.getGUI().isLeftPressed() && game.getGUI().isRightPressed()) {
                shipPhysics.move(true, NO_TURN);
            }
            else if (game.getGUI().isLeftPressed()) {
                shipPhysics.move(true, LEFT_TURN);
            } else {
                if (game.getGUI().isRightPressed()) {
                    shipPhysics.move(true, RIGHT_TURN);
                } else {
                    shipPhysics.move(true, NO_TURN);
                }
            }
        }
        else if(game.getGUI().isLeftPressed() && game.getGUI().isRightPressed()) {
            shipPhysics.move(false, NO_TURN);
        }
        else if( game.getGUI().isRightPressed()) {
            shipPhysics.move(false, RIGHT_TURN);
        }
        else if( game.getGUI().isLeftPressed()) {
            shipPhysics.move(false, LEFT_TURN);
        }
        else {
            shipPhysics.move(false,NO_TURN); //always keep moving
        }
        if(game.getGUI().isShieldsPressed()) {
                this.shieldOn();
        }
        if(game.getGUI().isShotPressed()) {
                this.fire(game);
        }
        energyPlusOne();
        countRounds();
    }
}
