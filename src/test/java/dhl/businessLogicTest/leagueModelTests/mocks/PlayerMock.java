package dhl.businessLogicTest.leagueModelTests.mocks;

import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.factory.LeagueModelFactory;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;

import java.time.LocalDate;


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
        IPlayerStatistics playerStatistics = factory.createPlayerStatistics( 10, 10, 10, 10);
        playerStatistics.setDateOfBirth(11,11,2000);
        playerStatistics.calculateCurrentAge(LocalDate.of(2020,11,14));
        return playerStatistics;
    }

    public IPlayerStatistics getInvalidPlayerStats(){
        IPlayerStatistics playerStatistics = factory.createPlayerStatistics(23, 10, 10, 10);
        playerStatistics.setAge(20);
        return playerStatistics;
    }

    public IPlayer getPlayerWithInvalidStats(){
        IPlayerStatistics playerStatistics = getInvalidPlayerStats();
        return factory.createPlayer("Harry", "forward", false, playerStatistics);
    }

}
