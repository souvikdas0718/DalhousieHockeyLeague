package dhl.businessLogicTest.gameSimulationTest;

import dhl.businessLogic.gameSimulation.PenaltyObserver;
import dhl.businessLogic.gameSimulation.ShotObserver;
import dhl.businessLogic.gameSimulation.Subject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.HashMap;

public class PenaltyObserverTest {

    Subject subject;
    PenaltyObserver penalties;
    HashMap<String, Integer> mapResult;

    @BeforeEach()
    public void initObject() {
        subject = new Subject();
        penalties = new PenaltyObserver(subject);
        mapResult = new HashMap<>();
    }

    @Test
    public void updateTest(){
        mapResult.put("Penalties" , 10);
        subject.setState(mapResult);
        penalties.update();
        Integer penaltiesValue = penalties.getObserverPenalties().get(0);
        Assertions.assertEquals(Optional.of(10), Optional.of(penaltiesValue));
    }

    @Test
    public void printTest(){
        mapResult.put("Penalties" , 10);
        subject.setState(mapResult);
        penalties.update();
        Assertions.assertTrue(penalties.print()>0.0);
    }
}
