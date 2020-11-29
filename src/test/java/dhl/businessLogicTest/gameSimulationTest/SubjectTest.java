package dhl.businessLogicTest.gameSimulationTest;

import dhl.businessLogic.gameSimulation.GoalObserver;
import dhl.businessLogic.gameSimulation.PenaltyObserver;
import dhl.businessLogic.gameSimulation.Subject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Optional;

public class SubjectTest {
    Subject subject;
    HashMap<String, Integer> mapResult;

    @BeforeEach()
    public void initObject() {
        subject = new Subject();
        mapResult = new HashMap<>();
    }

    @Test
    public void updateTest(){
        mapResult.put("Penalties" , 10);
        mapResult.put("Shots" , 10);
        mapResult.put("Goals" , 10);
        mapResult.put("Saves", 10);
        mapResult.put("Winner", 10);
        Integer matchWith = 10;

        subject.setState(mapResult);
        Assertions.assertEquals(matchWith, subject.getPenalties());
        Assertions.assertEquals(matchWith, subject.getGoals());
        Assertions.assertEquals(matchWith, subject.getSaves());
        Assertions.assertEquals(matchWith, subject.getShots());
    }

    @Test
    public void attachTest(){
        GoalObserver goalObserver = new GoalObserver(subject);
        subject.attach(goalObserver);
        Assertions.assertNotNull(subject.getObservers());
    }

    @Test
    public void notifyAllObserversTest(){
        GoalObserver goalObserver = new GoalObserver(subject);
        subject.attach(goalObserver);
        subject.notifyAllObservers();
        Assertions.assertNotNull(goalObserver.getObserverGoals());
    }
}
