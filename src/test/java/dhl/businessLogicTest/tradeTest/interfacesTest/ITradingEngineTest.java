package dhl.businessLogicTest.tradeTest.interfacesTest;

import dhl.Mocks.GameConfigMock;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.trade.TradingEngine;
import dhl.businessLogic.trade.interfaces.ITradingEngine;
import dhl.inputOutput.ui.IUserInputOutput;
import dhl.inputOutput.ui.UserInputOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ITradingEngineTest {

    ITradingEngine testClassObject;

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
        testClassObject = ITradingEngine.instance(ourGameConfig, leagueMock, userTeam);
    }

    @Test
    public void instanceTest(){
        ITradingEngine tradingEngine = ITradingEngine.instance(ourGameConfig, leagueMock, userTeam);
        Assertions.assertTrue(tradingEngine == testClassObject);
    }

    @Test
    public void setFactoryTest(){
        ITradingEngine tradingEngine = new TradingEngine(ourGameConfig, leagueMock, userTeam);
        testClassObject.setFactory(tradingEngine);

        Assertions.assertFalse(testClassObject == tradingEngine);
    }
}
