package dhl.businessLogicTest.AgingTest;


import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.aging.Interface.IAgingSystem;
import dhl.businessLogic.aging.Interface.IInjurySystem;
import dhl.businessLogic.aging.Interface.IRetirementSystem;
import dhl.businessLogicTest.leagueModelTests.PlayerDBMock;
import dhl.database.interfaceDB.IPlayerDB;
import dhl.businessLogic.aging.Aging;
import dhl.businessLogic.aging.AgingSystem;
import dhl.businessLogic.aging.InjurySystem;
import dhl.businessLogic.aging.RetirementSystem;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


public class AgingTest {

    LeagueObjectModelMocks leagueMock;
    IGameConfig gameConfig;
    Aging aging;
    ILeagueObjectModel leagueObjectModel;
    IInjurySystem injurySystem;
    IRetirementSystem retirementSystem;

    @BeforeEach()
    public void initObject() {
        leagueMock = new LeagueObjectModelMocks();
        gameConfig = leagueMock.getGameConfig();
        IPlayerDB playerDB = new PlayerDBMock();
        leagueObjectModel = leagueMock.getLeagueObjectMock();
        IAgingSystem agingSystem = new AgingSystem(gameConfig);
        retirementSystem = new RetirementSystem(playerDB, leagueObjectModel);
        injurySystem = new InjurySystem();
        aging = new Aging(agingSystem, retirementSystem, injurySystem, leagueMock.getLeagueObjectMock(), 365, playerDB);

    }

    @Test
    public void ageAllPlayerTest() throws Exception {
        leagueObjectModel = aging.initiateAging();
        for (IConference conference : leagueObjectModel.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    List<IPlayer> players = team.getPlayers();
                    IPlayer player = players.get(0);
                    IPlayerStatistics playerStatistics = player.getPlayerStats();
                    Assertions.assertEquals(26, playerStatistics.getAge());

                }
            }
        }
    }

    @Test
    public void checkPlayerInjuryRecoveryTest() {
        for (IConference conference : leagueObjectModel.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    for (IPlayer player : team.getPlayers()) {
                        player.setPlayerInjuredDays(2);
                        injurySystem.healInjuredPlayers(player);
                        Assertions.assertEquals(1, player.getPlayerInjuredDays());
                    }
                }
            }
        }
    }

    @Test
    public void checkRetiredInjuryRecoveryTest() {
        for (IPlayer agent : leagueObjectModel.getFreeAgents()) {
            agent.setPlayerInjuredDays(20);
            injurySystem.healInjuredPlayers(agent);
            Assertions.assertEquals(19, agent.getPlayerInjuredDays());

        }
    }

    @AfterEach()
    public void destroyObject() {
        leagueMock = null;
        gameConfig = null;
        aging = null;
        injurySystem = null;
        retirementSystem = null;

    }

}
