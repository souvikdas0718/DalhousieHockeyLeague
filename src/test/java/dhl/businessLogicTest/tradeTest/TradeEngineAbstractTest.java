package dhl.businessLogicTest.tradeTest;

import dhl.mocks.GameConfigMock;
import dhl.mocks.LeagueObjectModelMocks;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.trade.TradingEngine;
import dhl.businessLogic.trade.TradeEngineAbstract;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TradeEngineAbstractTest {

    TradeEngineAbstract testClassObject;

    ITeam userTeam;
    ILeagueObjectModel leagueMock;
    GameConfigMock gameConfigMock;
    LeagueObjectModelMocks leagueObjectModelMocks;
    IGameConfig ourGameConfig;

    @BeforeEach
    public void initObject() {
        leagueObjectModelMocks = new LeagueObjectModelMocks();
        leagueMock = leagueObjectModelMocks.getLeagueObjectMock();
        gameConfigMock = new GameConfigMock();
        ourGameConfig = gameConfigMock.getGameConfigMock();
        testClassObject = TradeEngineAbstract.instance(ourGameConfig, leagueMock, userTeam);
    }

    @Test
    public void instanceTest(){
        TradeEngineAbstract tradingEngine = TradeEngineAbstract.instance(ourGameConfig, leagueMock, userTeam);
        Assertions.assertTrue(tradingEngine == testClassObject);
    }

    @Test
    public void setFactoryTest(){
        TradeEngineAbstract tradingEngine = new TradingEngine(ourGameConfig, leagueMock, userTeam);
        testClassObject.setFactory(tradingEngine);

        Assertions.assertFalse(testClassObject == tradingEngine);
    }
}
