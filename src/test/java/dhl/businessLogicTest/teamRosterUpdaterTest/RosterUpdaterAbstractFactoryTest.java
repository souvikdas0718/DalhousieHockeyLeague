package dhl.businessLogicTest.teamRosterUpdaterTest;

import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.teamRosterUpdater.RosterUpdaterAbstractFactory;
import dhl.businessLogic.teamRosterUpdater.RosterUpdaterConcreteFactory;
import dhl.businessLogic.teamRosterUpdater.interfaces.ITeamRosterUpdater;
import dhl.businessLogic.trade.factory.TradeAbstractFactory;
import dhl.businessLogic.trade.factory.TradeConcreteFactory;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.tradeTest.mocks.factory.TradeMockAbstractFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RosterUpdaterAbstractFactoryTest {

    RosterUpdaterAbstractFactory testClassObject;

    @BeforeEach
    public void initObject(){
        testClassObject = RosterUpdaterAbstractFactory.instance();
    }

    @Test
    public void instanceTest(){
        testClassObject = RosterUpdaterAbstractFactory.instance();
        Assertions.assertFalse(testClassObject == null);
    }

    @Test
    public void setFactoryTest(){
        testClassObject = RosterUpdaterAbstractFactory.instance();

        RosterUpdaterAbstractFactory oldObject = testClassObject;
        testClassObject.setFactory(new RosterUpdaterConcreteFactory());
        RosterUpdaterAbstractFactory testClassObject2 = RosterUpdaterAbstractFactory.instance();

        Assertions.assertFalse(testClassObject2 == oldObject);
    }
}
