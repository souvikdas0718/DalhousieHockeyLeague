package dhl.leagueModelTests;


import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

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
        Assertions.assertEquals(35, agingSystem.getAverageRetirementAge());
    }


    @Test
    public void setMaximumAgeTest() {
        Assertions.assertEquals(50, agingSystem.getMaximumAge());
    }

    @Test
    public void ageAllPlayerTest(){
        ILeagueObjectModel leagueObjectModel=leagueMock.getLeagueObjectMock();
        for(IConference conference : leagueObjectModel.getConferences()){
            for (IDivision division : conference.getDivisions()){
                for(ITeam team :division.getTeams()){
                    agingSystem.ageAllPlayers(team.getPlayers());
                    List<IPlayer> players = team.getPlayers();
                    IPlayer player=players.get(0);
                    IPlayerStatistics playerStatistics=player.getPlayerStats();
                    Assertions.assertEquals(26,playerStatistics.getAge());
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
        List<IPlayer> players = new ArrayList<>();
        IPlayerStatistics playerStatistics1=new PlayerStatistics(50,20,20,20,20);
        players.add(new Player("PlayerOne","forward",true,playerStatistics1));
        IPlayerStatistics playerStatistics2=new PlayerStatistics(36,20,20,20,20);
        players.add(new Player("PlayerTwo","forward",true,playerStatistics2));
        IPlayerStatistics playerStatistics3=new PlayerStatistics(34,20,20,20,20);
        players.add(new Player("PlayerThree","forward",true,playerStatistics3));

        ICoach headCoach = new Coach("Todd McLellan",0.1,0.5,1.0,0.2);
        ITeam team = new Team("Mock Team", "Mock Manager", headCoach, players );
        List<ITeam> teams = new ArrayList<>();
        teams.add(team);

        agingSystem.setLikelihoodForGreaterThanAvg(100);
        agingSystem.setLikelihoodForLesserThanAvg(100);
        playersSelectedToRetire=agingSystem.selectPlayersToRetire(team);

        Assertions.assertTrue(playersSelectedToRetire.containsKey("Mock Team"));
        Assertions.assertEquals(3,playersSelectedToRetire.get("Mock Team").size());
    }

    @Test
    public void selectFreeAgentsToRetireTest(){
        List<IPlayer> agentsSelectedToRetire=new ArrayList<>();
        List<IPlayer> freeAgents = new ArrayList<>();
        IPlayerStatistics playerStatistics1=new PlayerStatistics(50,20,20,20,20);
        freeAgents.add(new FreeAgent("PlayerOne","forward",playerStatistics1));
        IPlayerStatistics playerStatistics2=new PlayerStatistics(36,20,20,20,20);
        freeAgents.add(new FreeAgent("PlayerTwo","forward",playerStatistics2));
        IPlayerStatistics playerStatistics3=new PlayerStatistics(34,20,20,20,20);
        freeAgents.add(new FreeAgent("PlayerThree","forward",playerStatistics3));

        agingSystem.setLikelihoodForGreaterThanAvg(100);
        agingSystem.setLikelihoodForLesserThanAvg(100);
        ILeagueObjectModel leagueObjectModel= leagueMock.getLeagueObjectMock();
        leagueObjectModel=new LeagueObjectModel(leagueObjectModel.getLeagueName(),leagueObjectModel.getConferences(),freeAgents,new ArrayList<>(),new ArrayList<>(),gameConfig);
        agentsSelectedToRetire=agingSystem.selectFreeAgentsToRetire(leagueObjectModel.getFreeAgents());

        Assertions.assertEquals(3,agentsSelectedToRetire.size());
    }

    @AfterEach()
    public void destroyObject(){
        leagueMock= null;
        gameConfig=null;
        agingSystem = null;

    }

}
