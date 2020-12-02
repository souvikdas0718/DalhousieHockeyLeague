package dhl.businessLogicTest.teamRosterUpdaterTest;

import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.PlayerPosition;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.teamRosterUpdater.RosterUpdaterAbstractFactory;
import dhl.businessLogic.teamRosterUpdater.UpdateUserTeamRoster;
import dhl.businessLogic.teamRosterUpdater.interfaces.ITeamRosterUpdater;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.tradeTest.mocks.factory.TradeMockAbstractFactory;
import dhl.importJsonTest.mocks.MockUserInputOutput;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class UpdateUserTeamRosterTest {

    ITeamRosterUpdater testClassObject;
    RosterUpdaterAbstractFactory rosterUpdaterFactory;
    LeagueModelAbstractFactory leagueFactory;

    TradeMockAbstractFactory tradeMockFactory;
    LeagueModelMockAbstractFactory leagueMockFactory;

    IUserInputOutput ioObjectMock;

    @BeforeEach
    public void initObject(){

        IUserInputOutput.setFactory(MockUserInputOutput.instance());
        ioObjectMock =IUserInputOutput.getInstance();

        rosterUpdaterFactory = RosterUpdaterAbstractFactory.instance();
        testClassObject = rosterUpdaterFactory.createUpdateUserTeamRoster(ioObjectMock);
        leagueFactory = LeagueModelAbstractFactory.instance();

        tradeMockFactory = TradeMockAbstractFactory.instance();
        leagueMockFactory = LeagueModelMockAbstractFactory.instance();
    }

    @Test
    public void validateTeamRosterTest(){
        ArrayList<IPlayer> freeAgents = tradeMockFactory.createFreeAgentMockForTrade().getListOfFreeAgents();
        LeagueObjectModel league = (LeagueObjectModel) leagueMockFactory.createLeagueMock().getLeagueObjectModel();
        league.freeAgents = freeAgents;
        ITeam team = tradeMockFactory.createTeamMockForTrade().getTeamWithGoodPlayer();

        IPlayerStatistics playerStatistics = leagueFactory.createPlayerStatistics( 10, 10, 10, 10);
        playerStatistics.setAge(25);
        IPlayer player = leagueFactory.createPlayer("player1", "goalie", false, playerStatistics);
        team.getPlayers().add(player);

        ((MockUserInputOutput)ioObjectMock).setMockOutput("1");
        testClassObject.validateTeamRoster(team, league);
        team.setRoster();
        Assertions.assertTrue(team.checkTeamPlayersCount());
    }

    @Test
    public void updatePlayersTest() {

        ArrayList<IPlayer> freeAgents = tradeMockFactory.createFreeAgentMockForTrade().getListOfFreeAgents();
        LeagueObjectModel league = (LeagueObjectModel) leagueMockFactory.createLeagueMock().getLeagueObjectModel();
        league.freeAgents = freeAgents;
        ITeam team = tradeMockFactory.createTeamMockForTrade().getTeamWithGoodPlayer();
        ((MockUserInputOutput)ioObjectMock).setMockOutput("1");
        int countDefence = 0;
        for(IPlayer p : team.getPlayers()){
            if (p.getPosition().equals(PlayerPosition.DEFENSE.toString())){
                countDefence = countDefence + 1;
            }
        }
        ((UpdateUserTeamRoster)testClassObject).updatePlayers(countDefence, PlayerPosition.DEFENSE.toString(),10, team, league);

        countDefence = 0;
        for(IPlayer p : team.getPlayers()){
            if (p.getPosition().equals(PlayerPosition.DEFENSE.toString())){
                countDefence = countDefence + 1;
            }
        }
        Assertions.assertEquals(10,countDefence );

        ((UpdateUserTeamRoster)testClassObject).updatePlayers(countDefence, PlayerPosition.DEFENSE.toString(),5, team, league);
        countDefence = 0;
        for(IPlayer p : team.getPlayers()){
            if (p.getPosition().equals(PlayerPosition.DEFENSE.toString())){
                countDefence = countDefence + 1;
            }
        }
        Assertions.assertEquals(5,countDefence);

    }

    @Test
    public void dropPlayerTest(){

        ILeagueObjectModel league = leagueMockFactory.createLeagueMock().getLeagueObjectModel();
        league.setFreeAgents(tradeMockFactory.createFreeAgentMockForTrade().getListOfFreeAgents());

        ((MockUserInputOutput)ioObjectMock).setMockOutput("1");
        ITeam team = tradeMockFactory.createTeamMockForTrade().getTeamWithGoodPlayer();

        int teamSizeBeforeDrop = team.getPlayers().size();
        testClassObject.dropPlayer(PlayerPosition.DEFENSE.toString() , team, league);
        int teamSizeAfterDrop = team.getPlayers().size();

        Assertions.assertTrue(teamSizeBeforeDrop > teamSizeAfterDrop);

    }

    @Test
    public void addPlayerTest(){
        ILeagueObjectModel league = leagueMockFactory.createLeagueMock().getLeagueObjectModel();
        league.setFreeAgents(tradeMockFactory.createFreeAgentMockForTrade().getListOfFreeAgents());

        ((MockUserInputOutput)ioObjectMock).setMockOutput("1");
        ITeam team = tradeMockFactory.createTeamMockForTrade().getTeamWithGoodPlayer();

        int teamSizeBeforeAdd = team.getPlayers().size();
        testClassObject.addPlayer(PlayerPosition.DEFENSE.toString() , team, league);
        int teamSizeAfterAdd = team.getPlayers().size();

        Assertions.assertTrue(teamSizeAfterAdd > teamSizeBeforeAdd);
    }
}
