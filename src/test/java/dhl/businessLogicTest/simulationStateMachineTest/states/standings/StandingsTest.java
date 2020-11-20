package dhl.businessLogicTest.simulationStateMachineTest.states.standings;

import dhl.Mocks.LeagueObjectModel20TeamMocks;
import dhl.Mocks.RegularSeasonStandingListMocks;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.simulationStateMachine.interfaces.IStandingSystem;
import dhl.businessLogic.simulationStateMachine.interfaces.IStandings;
import dhl.businessLogic.simulationStateMachine.states.standings.StandingSystem;
import dhl.businessLogic.simulationStateMachine.states.standings.Standings;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StandingsTest {

    LeagueObjectModel20TeamMocks model20TeamMocks;
    ILeagueObjectModel league;
    IStandings standings;
    IStandingSystem iStandingSystem;
    RegularSeasonStandingListMocks regularStandings;

    @BeforeEach
    public void initObject() {
        model20TeamMocks = new LeagueObjectModel20TeamMocks();
        model20TeamMocks.leagueModel20TeamGeneralStandings();
        league = model20TeamMocks.getLeagueData();
        standings = new Standings();
        regularStandings = new RegularSeasonStandingListMocks();
        iStandingSystem = new StandingSystem();
        iStandingSystem.setStandingsList(regularStandings.generalSeasonStandings());
    }

    @Test
    public void getTeamConferenceTest() {
        standings.setTeamConference(league.getConferences().get(0));
        Assertions.assertNotNull(standings.getTeamConference());
    }

    @Test
    public void setTeamConferenceTest() {
        standings.setTeamConference(league.getConferences().get(0));
        Assertions.assertNotNull(standings.getTeamConference());
    }

    @Test
    public void getTeamDivisionTest() {
        standings.setTeamDivision(league.getConferences().get(0).getDivisions().get(0));
        Assertions.assertEquals("Atlantic Division", standings.getTeamDivision().getDivisionName());
    }

    @Test
    public void setTeamDivisionTest() {
        standings.setTeamDivision(league.getConferences().get(0).getDivisions().get(0));
        Assertions.assertEquals("Atlantic Division", standings.getTeamDivision().getDivisionName());
    }

    @Test
    public void getTeamTest() {
        standings.setTeam(league.getConferences().get(0).getDivisions().get(0).getTeams().get(1));
        Assertions.assertEquals("Lightning", standings.getTeam().getTeamName());
    }

    @Test
    public void setTeamTest() {
        standings.setTeam(league.getConferences().get(0).getDivisions().get(0).getTeams().get(1));
        Assertions.assertEquals("Lightning", standings.getTeam().getTeamName());
    }

    @Test
    public void getGamesPlayedTest() {
        for (IStandings standing : iStandingSystem.getStandingsList()) {
            if (standing.getTeam().getTeamName().equals(iStandingSystem.getStandingsList().get(2).getTeam().getTeamName())) {
                standing.setGamesPlayed(standing.getGamesPlayed() + 1);
                Assert.assertEquals(standing.getGamesPlayed(), 10);
            }
        }
    }

    @Test
    public void setGamesPlayedTest() {
        for (IStandings standing : iStandingSystem.getStandingsList()) {
            if (standing.getTeam().getTeamName().equals(iStandingSystem.getStandingsList().get(1).getTeam().getTeamName())) {
                standing.setGamesPlayed(standing.getGamesPlayed() + 1);
                Assert.assertEquals(standing.getGamesPlayed(), 11);
            }
        }
    }

    @Test
    public void getWinsTest() {
        for (IStandings standing : iStandingSystem.getStandingsList()) {
            if (standing.getTeam().getTeamName().equals(iStandingSystem.getStandingsList().get(2).getTeam().getTeamName())) {
                standing.setWins(standing.getWins() + 1);
                Assert.assertEquals(standing.getWins(), 5);
            }
        }
    }

    @Test
    public void setWinsTest() {
        for (IStandings standing : iStandingSystem.getStandingsList()) {
            if (standing.getTeam().getTeamName().equals(iStandingSystem.getStandingsList().get(1).getTeam().getTeamName())) {
                standing.setWins(standing.getWins() + 1);
                Assert.assertEquals(standing.getWins(), 6);
            }
        }
    }

    @Test
    public void getLossTest() {
        for (IStandings standing : iStandingSystem.getStandingsList()) {
            if (standing.getTeam().getTeamName().equals(iStandingSystem.getStandingsList().get(2).getTeam().getTeamName())) {
                standing.setLoss(standing.getLoss() + 1);
                Assert.assertEquals(standing.getLoss(), 6);
            }
        }
    }

    @Test
    public void setLossTest() {
        for (IStandings standing : iStandingSystem.getStandingsList()) {
            if (standing.getTeam().getTeamName().equals(iStandingSystem.getStandingsList().get(1).getTeam().getTeamName())) {
                standing.setLoss(standing.getLoss() + 1);
                Assert.assertEquals(standing.getLoss(), 6);
            }
        }
    }

    @Test
    public void getPointsTest() {
        for (IStandings standing : iStandingSystem.getStandingsList()) {
            if (standing.getTeam().getTeamName().equals(iStandingSystem.getStandingsList().get(2).getTeam().getTeamName())) {
                standing.setPoints(standing.getPoints() + 2);
                Assert.assertEquals(standing.getPoints(), 10);
            }
        }
    }

    @Test
    public void setPointsTest() {
        for (IStandings standing : iStandingSystem.getStandingsList()) {
            if (standing.getTeam().getTeamName().equals(iStandingSystem.getStandingsList().get(1).getTeam().getTeamName())) {
                standing.setPoints(standing.getPoints() + 2);
                Assert.assertEquals(standing.getPoints(), 12);
            }
        }
    }
}
