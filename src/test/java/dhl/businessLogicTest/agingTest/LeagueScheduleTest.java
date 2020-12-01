package dhl.businessLogicTest.agingTest;

import dhl.mocks.factory.MockAbstractFactory;
import dhl.businessLogic.aging.LeagueSchedule;
import dhl.businessLogic.aging.agingFactory.AgingAbstractFactory;
import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.aging.interfaceAging.IRetirement;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.LeagueMock;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


public class LeagueScheduleTest {
    LeagueModelMockAbstractFactory leagueMockFactory;
    LeagueMock leagueMock;
    MockAbstractFactory mockFactory;
    AgingAbstractFactory agingFactory;

    IGameConfig gameConfig;
    LeagueSchedule leagueSchedule;
    ILeagueObjectModel leagueObjectModel;
    IInjury injurySystem;
    IRetirement retirementSystem;

    @BeforeEach()
    public void initObject() {
        leagueMockFactory = LeagueModelMockAbstractFactory.instance();
        leagueMock = leagueMockFactory.createLeagueMock();
        mockFactory = MockAbstractFactory.instance();
        agingFactory = AgingAbstractFactory.instance();

        gameConfig = leagueMock.getGameplayConfig();
        leagueObjectModel = leagueMock.getLeagueObjectModel();

        ISerializeLeagueObjectModel serializeModel = mockFactory.getMockSerialize();
        retirementSystem = agingFactory.createRetirement(serializeModel);
        injurySystem = agingFactory.createInjury();
        leagueSchedule = (LeagueSchedule) agingFactory.createLeagueSchedule(leagueMock.getLeagueObjectModel());

    }

    @Test
    public void ageAllPlayerTest() throws IOException, ParseException {
        leagueObjectModel = leagueSchedule.initiateAging(365, LocalDate.of(2020, 11, 14));
        for (IConference conference : leagueObjectModel.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    List<IPlayer> players = team.getPlayers();
                    IPlayer player = players.get(0);
                    IPlayerStatistics playerStatistics = player.getPlayerStats();
                    Assertions.assertEquals(25, playerStatistics.getAge());

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
