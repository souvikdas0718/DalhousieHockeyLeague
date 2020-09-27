package csci5308.nationalHockeyLeague.group2.projectSetup;

import csci5308.nationalHockeyLeague.database.group2.projectSetup.HelloWorld;
import org.junit.Assert;
import org.junit.Test;
public class HelloWorldTest {

    @Test
    public void projectSetupTest(){
        Assert.assertEquals("Hello World", HelloWorld.message());
    }
}
