package csci5308.nationalHockeyLeague.group2.projectSetup;

import csci5308.nationalHockeyLeague.database.group2.projectSetup.HelloWorld;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloWorldTest {

    @Test
    public void projectSetupTest(){
        assertEquals("HelloWorld", HelloWorld.message());
    }
}
