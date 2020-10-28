package dhl.leagueModelTests;


import dhl.Mocks.LeagueObjectModelMocks;
import dhl.importJson.Interface.IGameConfig;
import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class AgingSystemTest {

    LeagueObjectModelMocks leagueMock;
    IGameConfig gameConfig;
    AgingSystem agingSystem;

    @BeforeEach()
    public void initObject(){
        leagueMock= new LeagueObjectModelMocks();
        gameConfig=leagueMock.getGameConfig();
        agingSystem = new AgingSystem(gameConfig);

    }

    @Test
    public void setLikelihoodForGreaterThanAvgTest() {
        agingSystem.setLikelihoodForGreaterThanAvg(85);
        Assertions.assertEquals(85, agingSystem.getLikelihoodForGreaterThanAvg());
    }

    @Test
    public void setLikelihoodForLesserThanAvgTest() {
        agingSystem.setLikelihoodForLesserThanAvg(25);
        Assertions.assertEquals(25, agingSystem.getLikelihoodForLesserThanAvg());
    }

    @Test
    public void setAverageRetirementAgeTest() {
        agingSystem.setAverageRetirementAge(30);
        Assertions.assertEquals(30, agingSystem.getAverageRetirementAge());
    }


    @Test
    public void setMaximumAgeTest() {
        agingSystem.setMaximumAge(50);
        Assertions.assertEquals(50, agingSystem.getMaximumAge());
    }

    @Test
    public void ageAllPlayerTest(){
        ILeagueObjectModel leagueObjectModel=leagueMock.getLeagueObjectMock();
        agingSystem.ageAllPlayers(leagueObjectModel,365);
        for(IConference conference : leagueObjectModel.getConferences()){
            for (IDivision division : conference.getDivisions()){
                for(ITeam team :division.getTeams()){
                    for(IPlayer player:team.getPlayers()){
                        IPlayerStatistics playerStatistics=player.getPlayerStats();
                        Assertions.assertEquals(26,playerStatistics.getAge());
                    }

                }
            }
        }
    }

    @Test
    public void checkLikelihoodOfRetirementTest(){
        Assertions.assertTrue(agingSystem.checkLikelihoodOfRetirement(100));
        Assertions.assertFalse(agingSystem.checkLikelihoodOfRetirement(0));
    }

    @Test
    public void selectPlayersToRetireTest(){
        Map<String,List<IPlayer>> playersSelectedToRetire=new HashMap<>();
        ArrayList<IPlayer> players = new ArrayList<>();
        IPlayerStatistics playerStatistics1=new PlayerStatistics(50,20,20,20,20);
        players.add(new Player("PlayerOne","forward",true,playerStatistics1));
        IPlayerStatistics playerStatistics2=new PlayerStatistics(36,20,20,20,20);
        players.add(new Player("PlayerTwo","forward",true,playerStatistics2));
        IPlayerStatistics playerStatistics3=new PlayerStatistics(34,20,20,20,20);
        players.add(new Player("PlayerThree","forward",true,playerStatistics3));

        ITeam team =leagueMock.getTeamObjectMock();
        team.setPlayers(players);
        ArrayList<ITeam> teams = new ArrayList<>();
        teams.add(team);
        agingSystem.setLikelihoodForGreaterThanAvg(100);
        agingSystem.setLikelihoodForLesserThanAvg(100);
        playersSelectedToRetire=agingSystem.selectPlayersToRetire(teams);

        Assertions.assertTrue(playersSelectedToRetire.containsKey("Mock Team"));
        Assertions.assertEquals(3,playersSelectedToRetire.get("Mock Team").size());
    }

    @Test
    public void selectFreeAgentsToRetireTest(){
        Map<String,List<IFreeAgent>> agentsSelectedToRetire=new HashMap<>();
        ArrayList<IFreeAgent> freeAgents = new ArrayList<>();
        IPlayerStatistics playerStatistics1=new PlayerStatistics(50,20,20,20,20);
        freeAgents.add(new FreeAgent("PlayerOne","forward",playerStatistics1));
        IPlayerStatistics playerStatistics2=new PlayerStatistics(36,20,20,20,20);
        freeAgents.add(new FreeAgent("PlayerTwo","forward",playerStatistics2));
        IPlayerStatistics playerStatistics3=new PlayerStatistics(34,20,20,20,20);
        freeAgents.add(new FreeAgent("PlayerThree","forward",playerStatistics3));

        agingSystem.setLikelihoodForGreaterThanAvg(100);
        agingSystem.setLikelihoodForLesserThanAvg(100);
        ILeagueObjectModel leagueObjectModel= leagueMock.getLeagueObjectMock();
        leagueObjectModel.setFreeAgents(freeAgents);
        agentsSelectedToRetire=agingSystem.selectFreeAgentsToRetire(leagueObjectModel);

        Assertions.assertTrue(agentsSelectedToRetire.containsKey("Dhl"));
        Assertions.assertEquals(3,agentsSelectedToRetire.get("Dhl").size());
    }

    @Test
    public void healInjuredPlayersTest() throws ParseException {
        SimpleDateFormat todaysDate=new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = todaysDate.parse("18/07/2020");
        IPlayer player = new Player();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
        Date injuryDate = dateformat.parse("17/07/2020");
        player.setInjurySystem(new InjurySystem(injuryDate,1));
        agingSystem.healInjuredPlayers(currentDate, player);
        IInjurySystem system= player.getInjurySystem();
        Assertions.assertEquals(false,system.isInjured());

    }

    @AfterEach()
    public void destroyObject(){
        leagueMock= null;
        gameConfig=null;
        agingSystem = null;

    }

}
