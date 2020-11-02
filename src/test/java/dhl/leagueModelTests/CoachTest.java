package dhl.leagueModelTests;

import dhl.leagueModel.Coach;
import dhl.leagueModel.CommonValidation;
import dhl.leagueModel.interfaceModel.ICoach;
import dhl.leagueModel.interfaceModel.IValidation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CoachTest {
    ICoach coachDefault;
    ICoach coach;
    IValidation validate;

    @BeforeEach()
    public void initObject() {
        validate = new CommonValidation();
        coachDefault = new Coach();
        coach = new Coach("Mary Smith", 0.1, 0.2, 0.5, 1.0);
    }

    @Test
    public void CoachDefaultConstructorTest() {
        String name = coachDefault.getCoachName();
        Assertions.assertTrue(name.length() == 0);
    }

    @Test
    void getCoachNameTest() {
        Assertions.assertEquals("Mary Smith", coach.getCoachName());
    }

    @Test
    void getSkatingTest() {
        Assertions.assertEquals(0.1, coach.getSkating());
    }

    @Test
    void getShootingTest() {
        Assertions.assertEquals(0.2, coach.getShooting());
    }

    @Test
    void getCheckingTest() {
        Assertions.assertEquals(0.5, coach.getChecking());
    }

    @Test
    void getSavingTest() {
        Assertions.assertEquals(1.0, coach.getSaving());
    }

    @Test
    void checkIfCoachValidTest() throws Exception {

        Assertions.assertTrue(coach.checkIfCoachValid(validate));
    }

    @Test
    void checkCoachStatisticsTest() throws Exception {
        ICoach coach = new Coach("Mary Smith", 0.1, 0, 3, 1);
        Exception errorMsg = Assertions.assertThrows(Exception.class, () -> {
            coach.checkIfCoachValid(validate);
        });
        Assertions.assertTrue(errorMsg.getMessage().contains("Coach statistics must be between 0 and 1"));
    }

    @Test
    void checkCoachStatisticsValidTest() throws Exception {
        Assertions.assertDoesNotThrow(() -> coach.checkIfCoachValid(validate));
    }

    @AfterEach()
    public void destroyObject() {
        coach = null;
    }
}
