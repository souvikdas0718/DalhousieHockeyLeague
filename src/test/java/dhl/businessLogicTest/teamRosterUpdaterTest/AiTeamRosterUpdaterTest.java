package dhl.businessLogicTest.teamRosterUpdaterTest;

import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.PlayerPosition;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.teamRosterUpdater.AiTeamRosterUpdater;
import dhl.businessLogic.teamRosterUpdater.RosterUpdaterAbstractFactory;
import dhl.businessLogic.teamRosterUpdater.interfaces.ITeamRosterUpdater;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.tradeTest.mocks.factory.TradeMockAbstractFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class AiTeamRosterUpdaterTest {

    ITeamRosterUpdater testClassObject;
    RosterUpdaterAbstractFactory rosterUpdaterFactory;
    LeagueModelAbstractFactory leagueFactory;

    TradeMockAbstractFactory tradeMockFactory;
    LeagueModelMockAbstractFactory leagueMockFactory;

    @BeforeEach
    public void initObject(){
        rosterUpdaterFactory = RosterUpdaterAbstractFactory.instance();
        testClassObject = rosterUpdaterFactory.createAiTeamRosterUpdater();
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

        int countDefence = 0;
        for(IPlayer p : team.getPlayers()){
            if (p.getPosition().equals(PlayerPosition.DEFENSE.toString())){
                countDefence = countDefence + 1;
            }
        }
        ((AiTeamRosterUpdater)testClassObject).updatePlayers(countDefence, PlayerPosition.DEFENSE.toString(),10, team, league);

        countDefence = 0;
        for(IPlayer p : team.getPlayers()){
            if (p.getPosition().equals(PlayerPosition.DEFENSE.toString())){
                countDefence = countDefence + 1;
            }
        }
        Assertions.assertEquals(countDefence, 10);

        ((AiTeamRosterUpdater)testClassObject).updatePlayers(countDefence, PlayerPosition.DEFENSE.toString(),5, team, league);
        countDefence = 0;
        for(IPlayer p : team.getPlayers()){
            if (p.getPosition().equals(PlayerPosition.DEFENSE.toString())){
                countDefence = countDefence + 1;
            }
        }
        Assertions.assertEquals(countDefence, 5);

    }

    @Test
    public void dropPlayerTest(){

        ILeagueObjectModel league = leagueMockFactory.createLeagueMock().getLeagueObjectModel();
        league.setFreeAgents(tradeMockFactory.createFreeAgentMockForTrade().getListOfFreeAgents());

        ITeam team = tradeMockFactory.createTeamMockForTrade().getTeamWithGoodPlayer();
        IPlayer playerThatWillBeDroped = tradeMockFactory.createPlayerMockForTrade().getWeakPlayer("ToBeDropedPlayer" , PlayerPosition.DEFENSE.toString());
        team.getPlayers().add(playerThatWillBeDroped);
        int teamSizeBeforeDrop = team.getPlayers().size();
        testClassObject.dropPlayer(PlayerPosition.DEFENSE.toString() , team, league);
        int teamSizeAfterDrop = team.getPlayers().size();

        Assertions.assertTrue(teamSizeBeforeDrop > teamSizeAfterDrop);
        Assertions.assertFalse(team.getPlayers().contains(playerThatWillBeDroped));
        Assertions.assertTrue(league.getFreeAgents().contains(playerThatWillBeDroped));

    }

    @Test
    public void addPlayerTest(){
        ILeagueObjectModel league = leagueMockFactory.createLeagueMock().getLeagueObjectModel();
        league.setFreeAgents(tradeMockFactory.createFreeAgentMockForTrade().getListOfFreeAgents());
        ITeam team = tradeMockFactory.createTeamMockForTrade().getTeamWithGoodPlayer();

        int teamSizeBeforeAdd = team.getPlayers().size();
        testClassObject.addPlayer(PlayerPosition.DEFENSE.toString() , team, league);
        int teamSizeAfterAdd = team.getPlayers().size();

        Assertions.assertTrue(teamSizeAfterAdd > teamSizeBeforeAdd);
    }

    @Test
    public void findWeakestPlayerInListTest(){
        ITeam strongPlayerTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithGoodPlayer();
        IPlayer weakPlayer = tradeMockFactory.createPlayerMockForTrade().getWeakPlayer("weakPlayer", PlayerPosition.FORWARD.toString());

        strongPlayerTeam.getPlayers().add(weakPlayer);
        IPlayer playerToTest = ((AiTeamRosterUpdater) testClassObject).findWeakestPlayerInList(PlayerPosition.FORWARD.toString(), strongPlayerTeam.getPlayers());
        Assertions.assertEquals(playerToTest, weakPlayer);
    }

    @Test
    public void findBestPlayerInListTest(){
        ITeam weakPlayerTeam = tradeMockFactory.createTeamMockForTrade().getTeamWithGoodPlayer();
        IPlayer strongPlayer = tradeMockFactory.createPlayerMockForTrade().getStrongPlayer("StrongPlayer", PlayerPosition.FORWARD.toString());

        weakPlayerTeam.getPlayers().add(strongPlayer);
        IPlayer playerToTest = ((AiTeamRosterUpdater) testClassObject).findWeakestPlayerInList(PlayerPosition.FORWARD.toString(), weakPlayerTeam.getPlayers());
        Assertions.assertEquals(playerToTest, strongPlayer);
    }

    @Test
    public void playerFoundTest(){
        IPlayer player = null;
        Assertions.assertFalse(((AiTeamRosterUpdater)testClassObject).playerFound(player));
        player = leagueMockFactory.createPlayerMock().getPlayer();
        Assertions.assertTrue(((AiTeamRosterUpdater)testClassObject).playerFound(player));
    }
}
