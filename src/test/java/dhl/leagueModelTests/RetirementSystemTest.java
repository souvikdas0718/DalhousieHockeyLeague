package dhl.leagueModelTests;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.database.interfaceDB.IPlayerDB;
import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RetirementSystemTest {
    IRetirementSystem retirementSystem;
    LeagueObjectModelMocks leagueMock;
    IPlayerDB playerDBMock;
    @BeforeEach()
    public void initObject(){
        leagueMock= new LeagueObjectModelMocks();
        playerDBMock= new IPlayerDBMock();
        retirementSystem = new RetirementSystem(playerDBMock,leagueMock.getLeagueObjectMock());
    }

    @Test
    public void setLeagueObjectModelTest() {
        retirementSystem.setLeagueObjectModel(leagueMock.getLeagueObjectMock());
        ILeagueObjectModel leagueObjectModel= retirementSystem.getLeagueObjectModel();
        Assertions.assertEquals("Dhl",leagueObjectModel.getLeagueName());
    }

    @Test
    public void initiateRetirementTest() throws Exception{
        Map<String, List<IPlayer>> playersSelectedToRetire=new HashMap<>();
        ArrayList<IPlayer> players = new ArrayList<>();
        IPlayerStatistics playerStatistics1=new PlayerStatistics(50,5,5,5,5);
        players.add(new Player("Henry","forward",false,playerStatistics1));
        playersSelectedToRetire.put("Ontario",players);

        Map<String,List<IFreeAgent>> freeAgentsToRetire=new HashMap<>();
        freeAgentsToRetire.put("Dhl",new ArrayList<>());

        retirementSystem.initiateRetirement(playersSelectedToRetire,freeAgentsToRetire);
        ILeagueObjectModel leagueObjectModel = retirementSystem.getLeagueObjectModel();
        List<IFreeAgent> freeAgents= leagueObjectModel.getFreeAgents();
        Assertions.assertTrue(freeAgents.size()==1);
    }

    @Test
    public void initiateFreeAgentRetirementTest() throws Exception{
        Map<String, List<IPlayer>> playersSelectedToRetire=new HashMap<>();
        ArrayList<IPlayer> players = new ArrayList<>();
        playersSelectedToRetire.put("Ontario",players);

        Map<String,List<IFreeAgent>> freeAgentsToRetire=new HashMap<>();
        ArrayList<IFreeAgent> freeAgents = new ArrayList<>();
        IPlayerStatistics playerStatistics1=new PlayerStatistics(50,5,5,5,5);
        freeAgents.add(new FreeAgent("Jack","forward",playerStatistics1));
        freeAgentsToRetire.put("Dhl",freeAgents);

        retirementSystem.initiateRetirement(playersSelectedToRetire,freeAgentsToRetire);
        ILeagueObjectModel leagueObjectModel = retirementSystem.getLeagueObjectModel();
        List<IFreeAgent> freeAgentsList= leagueObjectModel.getFreeAgents();
        Assertions.assertTrue(freeAgentsList.size()==1);
    }

    @Test
    public void insertVeteransTest() throws Exception {
        Map<String, List<IPlayer>> playersSelectedToRetire=new HashMap<>();
        Map<String,List<IFreeAgent>> freeAgentsToRetire=new HashMap<>();
        retirementSystem.insertVeterans( playersSelectedToRetire, freeAgentsToRetire);
        ILeagueObjectModel leagueObjectModel=leagueMock.getLeagueObjectMock();
        Assertions.assertEquals("Dhl",leagueObjectModel.getLeagueName());
    }

    @AfterEach()
    public void destroyObject(){
        leagueMock= null;
        retirementSystem = null;

    }

}
