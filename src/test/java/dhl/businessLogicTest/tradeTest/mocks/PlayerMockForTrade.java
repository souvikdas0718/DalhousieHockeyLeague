package dhl.businessLogicTest.tradeTest.mocks;

import dhl.businessLogic.leagueModel.Player;
import dhl.businessLogic.leagueModel.PlayerPosition;
import dhl.businessLogic.leagueModel.PlayerStatistics;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.factory.LeagueModelFactory;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;

public class PlayerMockForTrade {

    LeagueModelAbstractFactory factory;

    public PlayerMockForTrade(){
        LeagueModelAbstractFactory.setFactory(new LeagueModelFactory());
        factory = LeagueModelAbstractFactory.instance();
    }

    public IPlayer getStrongPlayer(String playerName, String position) {
        IPlayer player = factory.createPlayer(playerName, position, false,
                new PlayerStatistics( 10, 10, 10, 0));

        return player;
    }

    public IPlayer getWeakPlayer(String playerName, String position) {
        IPlayer player = factory.createPlayer(playerName, position, false,
                new PlayerStatistics( 1, 1, 1, 0));
        return player;
    }
}
