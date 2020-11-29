package dhl.businessLogicTest.gameSimulationTest;

import dhl.businessLogic.gameSimulation.ShotObserver;
import dhl.businessLogic.gameSimulation.Subject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.HashMap;

public class ShotObserverTest {

    Subject subject;
    ShotObserver shots;
    HashMap<String, Integer> mapResult;

    @BeforeEach()
    public void initObject() {
        subject = new Subject();
        shots = new ShotObserver(subject);
        mapResult = new HashMap<>();
    }

    @Test
    public void updateTest(){
        mapResult.put("Shots" , 10);
        subject.setState(mapResult);
        shots.update();
        Integer shotsValue = shots.getObserverShots().get(0);
        Assertions.assertEquals(Optional.of(10), Optional.of(shotsValue));
    }

    @Test
    public void printTest(){
        mapResult.put("Shots" , 10);
        subject.setState(mapResult);
        shots.update();
        Assertions.assertTrue(shots.print()>0.0);
    }
}
