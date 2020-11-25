package dhl.businessLogicTest.tradeTest.mocks.factory;

import dhl.businessLogicTest.tradeTest.mocks.FreeAgentMockForTrade;
import dhl.businessLogicTest.tradeTest.mocks.GameConfigMockForTrading;
import dhl.businessLogicTest.tradeTest.mocks.PlayerMockForTrade;
import dhl.businessLogicTest.tradeTest.mocks.TeamMockForTrade;

public class TradeMockConcreteFactory extends TradeMockAbstractFactory {


    public TeamMockForTrade createTeamMockForTrade(){
        return new TeamMockForTrade();
    }

    public PlayerMockForTrade createPlayerMockForTrade(){
        return new PlayerMockForTrade();
    }

    public FreeAgentMockForTrade createFreeAgentMockForTrade() {
        return new FreeAgentMockForTrade();
    }

    public GameConfigMockForTrading createGameConfigMockForTrading(){
        return new GameConfigMockForTrading();
    }
}
