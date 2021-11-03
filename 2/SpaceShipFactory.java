import oop.ex2.*;

/**
 * This class has a single static method (createSpaceships(String[])),
 * which is currently empty. It is used by the supplied driver to create all the spaceship objects
 * according to the command line arguments.
 * @author  Natalia Shuklin
 */
public class SpaceShipFactory {
    public static SpaceShip[] createSpaceShips(String[] args) {
        SpaceShip [] newShips = new SpaceShip[args.length];
        for( int i =0; i< args.length; i++) {
            switch (args[i]) {
                case "h":
                    newShips[i] = new HumanControlledShip();
                    break;
                case "r":
                    newShips[i] = new RunnerShip();
                    break;
                case "b":
                    newShips[i] = new BasherShip();
                    break;
                case "a":
                    newShips[i] = new AggressiveShip();
                    break;
                case "d":
                    newShips[i] = new DrunkardShip();
                    break;
                case "s":
                    newShips[i] = new SpecialShip();
                    break;
            }
        }
        return newShips;
    }
}
