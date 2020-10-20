package dhl.leagueModelTests;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;
import dhl.database.interfaceDB.ILeagueObjectModelData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class LeagueObjectModelTest {
    LeagueObjectModel leagueModel;
    IValidation validate;
    ILeagueObjectModel leagueModelParameterized;
    LeagueObjectModelMocks leagueMock;

    @BeforeEach
    public void initialize(){
        leagueModel=new LeagueObjectModel();
        validate=new CommonValidation();
        leagueMock= new LeagueObjectModelMocks();
        leagueModelParameterized=leagueMock.getLeagueObjectMock();
    }

    @Test
    public void defaultConstructorTest(){
        Assertions.assertTrue(leagueModel.getLeagueName().isEmpty());
        leagueModel.setLeagueName("Dhl");
        Assertions.assertEquals("Dhl",leagueModel.getLeagueName() );
        Assertions.assertTrue(leagueModel.conferences.isEmpty());
    }

    @Test
    public void setFreeAgentsTest(){
        ArrayList<IFreeAgent> freeAgentsList=new ArrayList<>();
        IPlayerStatistics playerStatistics =new PlayerStatistics(20,10,10,10,10);
        freeAgentsList.add(new FreeAgent("Henry","forward",playerStatistics));
        freeAgentsList.add(new FreeAgent("Max","goalie",playerStatistics));
        leagueModel.setFreeAgents(freeAgentsList);
        Assertions.assertEquals(leagueModel.getFreeAgents().size(),freeAgentsList.size());
    }

    @Test
    public void setCoachesTest(){
        leagueModel.setCoaches(leagueMock.getCoaches());
        Assertions.assertEquals(2,leagueModel.getCoaches().size());
    }

    @Test
    public void setManagersTest(){
        leagueModel.setManagers(leagueMock.getManagers());
        Assertions.assertEquals(3,leagueModel.getManagers().size());
    }

    @Test
    public void checkIfLeagueModelValidTest() throws Exception{
        ArrayList<IConference> conferences =leagueModelParameterized.getConferences();
        conferences.add(new Conference("Eastern",new ArrayList<IDivision>()));
        leagueModelParameterized.setConferences(conferences);
        Assertions.assertTrue(leagueModelParameterized.checkIfLeagueModelValid(validate));
    }

    @Test void saveLeagueObjectModelTest() throws Exception{
        ILeagueObjectModelData mockDb=new MockDatabase();
        ArrayList<IPlayer> players= new ArrayList<>();
        ICoach headCoach = new Coach("Todd McLellan",0.1,0.5,1.0,0.2);
        ITeam newlyCreatedTeam=new Team("Nova Scotia","Mathew",headCoach,players);
        Assertions.assertEquals("Dhl",leagueModelParameterized.saveLeagueObjectModel(mockDb,"Dhl","Western","Atlantic",newlyCreatedTeam).getLeagueName());
    }

    @Test void loadLeagueObjectModelTest() throws Exception{
        ILeagueObjectModelData mockDb=new MockDatabase();
        Assertions.assertEquals("Dhl",leagueModelParameterized.loadLeagueObjectModel(mockDb,"Dhl","Nova Scotia").getLeagueName());
    }

    @AfterEach
    public void destroyObject(){
        leagueModel=null;
    }

}


