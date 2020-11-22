package dhl.businessLogicTest.leagueModelTests;

import dhl.businessLogic.leagueModel.Coach;
import dhl.businessLogic.leagueModel.CommonValidation;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.ICoach;
import dhl.businessLogic.leagueModel.interfaceModel.IValidation;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.CoachMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CoachTest {
    LeagueModelMockAbstractFactory mockFactory;
    LeagueModelAbstractFactory factory;
    CoachMock coachMock;
    IValidation validate;
    ICoach coachDefault;
    ICoach coach;

    @BeforeEach()
    public void initObject() {
        mockFactory = LeagueModelMockAbstractFactory.instance();
        coachMock = mockFactory.createCoachMock();
        factory = LeagueModelAbstractFactory.instance();
        validate = factory.createCommonValidation();
        coachDefault = factory.createCoachDefault();
        coach = coachMock.getCoach();
    }

    @Test
    public void CoachDefaultConstructorTest() {
        String name = coachDefault.getCoachName();
        Assertions.assertTrue(name.length() == 0);
    }

    @Test
    void getCoachNameTest() {
        Assertions.assertEquals("Todd McLellan", coach.getCoachName());
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
        ICoach coach = coachMock.getCoachInvalidStat();
        Exception errorMsg = Assertions.assertThrows(Exception.class, () -> {
            coach.checkIfCoachValid(validate);
        });
        Assertions.assertTrue(errorMsg.getMessage().contains("Coach statistics must be between 0 and 1"));
    }

    @Test
    void checkCoachStatisticsValidTest()  {
        Assertions.assertDoesNotThrow(() -> coach.checkIfCoachValid(validate));
    }

    @AfterEach()
    public void destroyObject() {
        coach = null;
    }
}
