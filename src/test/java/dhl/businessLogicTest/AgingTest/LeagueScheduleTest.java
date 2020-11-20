package dhl.businessLogicTest.AgingTest;


import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.InputOutput.importJson.Interface.ISerializeLeagueObjectModel;
import dhl.InputOutput.importJson.SerializeLeagueObjectModel;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.aging.interfaceAging.IAging;
import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.aging.interfaceAging.IRetirement;
import dhl.businessLogicTest.leagueModelTests.PlayerDBMock;
import dhl.database.interfaceDB.IPlayerDB;
import dhl.businessLogic.aging.LeagueSchedule;
import dhl.businessLogic.aging.Aging;
import dhl.businessLogic.aging.Injury;
import dhl.businessLogic.aging.Retirement;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


public class LeagueScheduleTest {

    LeagueObjectModelMocks leagueMock;
    IGameConfig gameConfig;
    LeagueSchedule leagueSchedule;
    ILeagueObjectModel leagueObjectModel;
    IInjury injurySystem;
    IRetirement retirementSystem;

    @BeforeEach()
    public void initObject() {
        leagueMock = new LeagueObjectModelMocks();
        gameConfig = leagueMock.getGameConfig();
        ISerializeLeagueObjectModel serializeModel = new SerializeLeagueObjectModel();
        leagueObjectModel = leagueMock.getLeagueObjectMock();
        IAging agingSystem = new Aging(gameConfig);
        retirementSystem = new Retirement(serializeModel, leagueObjectModel);
        injurySystem = new Injury();
        leagueSchedule = new LeagueSchedule(agingSystem, retirementSystem, injurySystem, leagueMock.getLeagueObjectMock(), 365);

    }

    @Test
    public void ageAllPlayerTest() throws Exception {
        leagueObjectModel = leagueSchedule.initiateAging();
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
        leagueSchedule = null;
        injurySystem = null;
        retirementSystem = null;

    }

}
