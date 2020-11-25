package dhl.businessLogicTest.tradeTest.mocks.factory;

import dhl.businessLogicTest.tradeTest.mocks.FreeAgentMockForTrade;
import dhl.businessLogicTest.tradeTest.mocks.GameConfigMockForTrading;
import dhl.businessLogicTest.tradeTest.mocks.PlayerMockForTrade;
import dhl.businessLogicTest.tradeTest.mocks.TeamMockForTrade;

public abstract class TradeMockAbstractFactory {
    private static TradeMockAbstractFactory uniqueInstance = null;

    protected TradeMockAbstractFactory() {

    }

    public static TradeMockAbstractFactory instance() {
        if (null == uniqueInstance)
        {
            uniqueInstance = new TradeMockConcreteFactory();
        }
        return uniqueInstance;
    }

    public static void setFactory(TradeMockAbstractFactory factory) {
        uniqueInstance = factory;
    }

    public abstract TeamMockForTrade createTeamMockForTrade();
    public abstract PlayerMockForTrade createPlayerMockForTrade();
    public abstract FreeAgentMockForTrade createFreeAgentMockForTrade();
    public abstract GameConfigMockForTrading createGameConfigMockForTrading();
}
