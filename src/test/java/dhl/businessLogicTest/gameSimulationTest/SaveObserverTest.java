package dhl.businessLogicTest.gameSimulationTest;

import dhl.businessLogic.gameSimulation.SaveObserver;
import dhl.businessLogic.gameSimulation.Subject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.HashMap;

public class SaveObserverTest {

    Subject subject;
    SaveObserver saves;
    HashMap<String, Integer> mapResult;

    @BeforeEach()
    public void initObject() {
        subject = new Subject();
        saves = new SaveObserver(subject);
        mapResult = new HashMap<>();
    }

    @Test
    public void updateTest(){
        mapResult.put("Saves" , 10);
        subject.setState(mapResult);
        saves.update();
        Integer savesValue = saves.getObserverSaves().get(0);
        Assertions.assertEquals(Optional.of(10), Optional.of(savesValue));
    }

    @Test
    public void printTest(){
        mapResult.put("Saves" , 10);
        subject.setState(mapResult);
        saves.update();
        Assertions.assertTrue(saves.print()>0.0);
    }
}
