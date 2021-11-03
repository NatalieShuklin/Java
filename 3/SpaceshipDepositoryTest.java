import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import oop.ex3.spaceship.*;

/**
 * test suite class - runs all objects tests
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        LockerTest.class,
        LongTermTest.class,
        SpaceshipTest.class
})
public class SpaceshipDepositoryTest {

}
