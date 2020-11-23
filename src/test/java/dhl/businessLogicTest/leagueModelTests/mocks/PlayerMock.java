package dhl.businessLogicTest.leagueModelTests.mocks;

import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.factory.LeagueModelFactory;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;


public class PlayerMock {
    LeagueModelAbstractFactory factory;

    public PlayerMock(){
        LeagueModelAbstractFactory.setFactory(new LeagueModelFactory());
        factory = LeagueModelAbstractFactory.instance();
    }

    public IPlayer getPlayer(){
        IPlayerStatistics playerStatistics = getPlayerStats();
        return factory.createPlayer("Harry", "forward", false, playerStatistics);
    }

    public IPlayer getPlayerCaptain(){
        IPlayerStatistics playerStatistics = getPlayerStats();;
        return factory.createPlayer("Harry", "forward", true, playerStatistics);
    }

    public IPlayer getInvalidPlayerCaptain(){
        IPlayerStatistics playerStatistics = getPlayerStats();;
        return factory.createPlayer("Harry", "forward", null, playerStatistics);
    }

    public IPlayer getPlayerWithoutName(){
        IPlayerStatistics playerStatistics = getPlayerStats();
        return factory.createPlayer("", "forward", false, playerStatistics);
    }

    public IPlayer getPlayerInvalidPosition(){
        IPlayerStatistics playerStatistics = getPlayerStats();
        return factory.createPlayer("Harry", "Leg side", false, playerStatistics);
    }

    public IPlayerStatistics getPlayerStats(){
        return factory.createPlayerStatistics(20, 10, 10, 10, 10);
    }

    public IPlayerStatistics getInvalidPlayerStats(){
        return factory.createPlayerStatistics(20, 23, 10, 10, 10);
    }





}
