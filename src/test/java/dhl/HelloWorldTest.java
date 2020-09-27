package dhl;

import dhl.HelloWorld;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloWorldTest {

    @Test
    public void projectSetupTest(){
        assertEquals("Hello World", HelloWorld.message());
    }
}
