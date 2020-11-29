package dhl.businessLogicTest.tradeTest.factoryTest;

import dhl.businessLogic.trade.factory.TradeAbstractFactory;
import dhl.businessLogic.trade.factory.TradeConcreteFactory;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.tradeTest.mocks.factory.TradeMockAbstractFactory;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TradeAbstractFactoryTest {

    TradeAbstractFactory testClassObject;

    TradeMockAbstractFactory tradeMockFactory;
    LeagueModelMockAbstractFactory leagueMocks;

    @BeforeEach
    public void initObject(){
        tradeMockFactory = TradeMockAbstractFactory.instance();
        leagueMocks = LeagueModelMockAbstractFactory.instance();
    }

    @Test
    public void instanceTest(){
        testClassObject = TradeAbstractFactory.instance();
        Assertions.assertFalse(testClassObject == null);

        TradeAbstractFactory testClassObject2 = TradeAbstractFactory.instance();
        Assertions.assertEquals(testClassObject2, testClassObject);
    }

    @Test
    public void setFactoryTest(){
        testClassObject = TradeAbstractFactory.instance();

        TradeAbstractFactory oldObject = testClassObject;
        testClassObject.setFactory(new TradeConcreteFactory());
        TradeAbstractFactory testClassObject2 = TradeAbstractFactory.instance();

        Assertions.assertFalse(testClassObject2 == oldObject);
    }
}
