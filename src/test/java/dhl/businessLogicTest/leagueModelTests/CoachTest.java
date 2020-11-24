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
    void checkCoachStatisticsTest()  {
        ICoach coach = coachMock.getCoachInvalidStat();
        Assertions.assertTrue(coach.isCoachStatInvalid(2));
    }

    @Test
    void checkCoachStatisticsValidTest() {
        ICoach coach = coachMock.getCoachInvalidStat();
        Assertions.assertFalse(coach.isCoachStatInvalid(0.2));
    }


    @AfterEach()
    public void destroyObject() {
        coach = null;
    }
}
