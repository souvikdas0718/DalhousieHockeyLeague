package dhl.leagueModelTests;

import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class LeagueObjectModelTest {
    LeagueObjectModel leagueModel;
    IParserOutput parsedOutput;
    IValidation validate;
    LeagueObjectModel leagueModelParameterized;
    @BeforeEach
    public void initialize(){
        leagueModel=new LeagueObjectModel();
        parsedOutput=new MockParserOutput();
        validate=new CommonValidation();
        ArrayList<IPlayer> playersList=new ArrayList<>();
        playersList.add(new Player("Henry","forward",false));
        playersList.add(new Player("Max","goalie",true));
        ITeam team = new Team("Ontario","Mathew","henry",playersList);
        ArrayList<ITeam> teamArrayList=new ArrayList<>();
        teamArrayList.add(team);
        IDivision division = new Division("Atlantic",teamArrayList);
        ArrayList<IDivision> divisionsList=new ArrayList<>();
        divisionsList.add(division);
        IConference conference=new Conference("Western",divisionsList);
        ArrayList<IConference> conferences= new ArrayList<>();
        conferences.add(conference);
        ArrayList<IPlayer> freeAgentsList=new ArrayList<>();
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
        ArrayList<IPlayer> freeAgentsList=new ArrayList<>();
        freeAgentsList.add(new Player("Henry","forward",true));
        freeAgentsList.add(new Player("Max","goalie",true));
        leagueModel.setFreeAgents(freeAgentsList);
        Assertions.assertEquals(leagueModel.getFreeAgents().size(),freeAgentsList.size());
    }
    @Test void setConferencesTest(){
        leagueModel.setConferences(new ArrayList<IConference>());
        Assertions.assertTrue(leagueModel.getConferences().size()==0);
    }

    @Test
    public void checkIfLeagueModelValidTest() throws Exception{

        Assertions.assertTrue(leagueModelParameterized.checkIfLeagueModelValid(validate));
    }
    @AfterEach
    public void destroyObject(){
        leagueModel=null;
        parsedOutput=null;
    }
}


