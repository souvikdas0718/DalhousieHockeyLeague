package dhl.businessLogicTest.gameSimulationTest;

import dhl.businessLogic.gameSimulation.GoalObserver;
import dhl.businessLogic.gameSimulation.Subject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.HashMap;

public class GoalObserverTest {

    Subject subject;
    GoalObserver goals;
    HashMap<String, Integer> mapResult;

    @BeforeEach()
    public void initObject() {
        subject = new Subject();
        goals = new GoalObserver(subject);
        mapResult = new HashMap<>();

    }

    @Test
    public void updateTest(){
        mapResult.put("Goals" , 10);
        subject.setState(mapResult);
        goals.update();
        Integer goalsValue = goals.getObserverGoals().get(0);
        Assertions.assertEquals(Optional.of(10), Optional.of(goalsValue));
    }

    @Test
    public void printTest(){
        mapResult.put("Goals" , 10);
        subject.setState(mapResult);
        goals.update();
        Assertions.assertTrue(goals.print()>0.0);
    }
}
