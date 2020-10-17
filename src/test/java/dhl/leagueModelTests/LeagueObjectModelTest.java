package dhl.leagueModelTests;

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
    LeagueObjectModel leagueModelParameterized;

    @BeforeEach
    public void initialize(){
        leagueModel=new LeagueObjectModel();
        validate=new CommonValidation();
        ArrayList<IPlayer> playersList=new ArrayList<>();
        IPlayerStatistics playerStatistics =new PlayerStatistics(20,10,10,10,0);
        playersList.add(new Player("Henry","forward",false,playerStatistics));
        playersList.add(new Player("Max","goalie",true,playerStatistics));
        ITeam team = new Team("Ontario","Mathew","henry",playersList);
        ArrayList<ITeam> teamArrayList=new ArrayList<>();
        teamArrayList.add(team);
        IDivision division = new Division("Atlantic",teamArrayList);
        ArrayList<IDivision> divisionsList=new ArrayList<>();
        divisionsList.add(division);
        IConference conference=new Conference("Western",divisionsList);
        ArrayList<IConference> conferences= new ArrayList<>();
        conferences.add(conference);
        ArrayList<IFreeAgent> freeAgentsList=new ArrayList<>();
        leagueModelParameterized=new LeagueObjectModel("Dhl",conferences,freeAgentsList);
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
        IPlayerStatistics playerStatistics =new PlayerStatistics(20,10,10,10,0);
        freeAgentsList.add(new FreeAgent("Henry","forward",playerStatistics));
        freeAgentsList.add(new FreeAgent("Max","goalie",playerStatistics));
        leagueModel.setFreeAgents(freeAgentsList);
        Assertions.assertEquals(leagueModel.getFreeAgents().size(),freeAgentsList.size());
    }

    @Test void setConferencesTest(){
        leagueModel.setConferences(new ArrayList<IConference>());
        Assertions.assertTrue(leagueModel.getConferences().size()==0);
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
        ITeam newlyCreatedTeam=new Team("Nova Scotia","Mathew","Harry",players);
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


