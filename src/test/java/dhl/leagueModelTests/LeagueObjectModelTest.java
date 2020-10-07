package dhl.leagueModelTests;


import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;
import dhl.leagueModelData.ILeagueObjectModelData;
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
        ArrayList<IConference> conferences =leagueModelParameterized.getConferences();
        conferences.add(new Conference("Eastern",new ArrayList<IDivision>()));
        leagueModelParameterized.setConferences(conferences);
        Assertions.assertTrue(leagueModelParameterized.checkIfLeagueModelValid(validate));
    }
    @Test void checkIfLeagueHasEvenConferencesTest(){
        Exception error=Assertions.assertThrows(Exception.class,() ->{
            leagueModelParameterized.checkIfLeagueHasEvenConferences();
        });
        Assertions.assertTrue(error.getMessage().contains("A League must contain even number of conferences"));
    }

    @Test void checkUserInputIncorrectLeagueTest() throws Exception{
        Exception error=Assertions.assertThrows(Exception.class,() ->{
            leagueModel.checkUserInputForCreateTeams(leagueModelParameterized,"Nhl","Western","Atlantic","Nova Scotia");
        });
        Assertions.assertTrue(error.getMessage().contains("League name is not present in file imported."));
    }
    @Test void checkUserInputIncorrectConferenceTest() throws Exception{
        Exception error=Assertions.assertThrows(Exception.class,() ->{
            leagueModel.checkUserInputForCreateTeams(leagueModelParameterized,"Dhl","Premier","Atlantic","Nova Scotia");
        });
        Assertions.assertTrue(error.getMessage().contains("Conference name is not present in file imported"));
    }
    @Test void checkUserInputIncorrectDivisionTest() throws Exception{
        Exception error=Assertions.assertThrows(Exception.class,() ->{
            leagueModel.checkUserInputForCreateTeams(leagueModelParameterized,"Dhl","Western","Metropolitan","Nova Scotia");
        });
        Assertions.assertTrue(error.getMessage().contains("Division name is not present in file imported"));
    }
    @Test void checkUserInputTeamAlreadyPresentTest() throws Exception{
        Exception error=Assertions.assertThrows(Exception.class,() ->{
            leagueModel.checkUserInputForCreateTeams(leagueModelParameterized,"Dhl","Western","Atlantic","Ontario");
        });
        Assertions.assertTrue(error.getMessage().contains("Team name entered is already present in file imported"));
    }

    @Test void checkUserInputForCreateTeamsTest() throws Exception{
        Assertions.assertTrue(leagueModel.checkUserInputForCreateTeams(leagueModelParameterized,"Dhl","Western","Atlantic","Nova Scotia"));
    }
    @Test void createTeamTest() throws Exception{
        ILeagueObjectModelData mockdb=new MockDatabase();
        Assertions.assertEquals("Dhl",leagueModelParameterized.createTeam(mockdb,"Dhl","Western","Atlantic","Nova Scotia","Mathew","Harry").getLeagueName());
    }
    @Test void loadTeamTest() throws Exception{
        ILeagueObjectModelData mockdb=new MockDatabase();
        Assertions.assertEquals("Dhl",leagueModelParameterized.loadTeam(mockdb,"Dhl","Western","Atlantic","Nova Scotia").getLeagueName());
    }

    @Test
    public void checkIfConferenceNamesUniqueInLeagueTest() throws Exception{
        ArrayList<IConference> conferences =leagueModelParameterized.getConferences();
        conferences.add(new Conference("Western",new ArrayList<IDivision>()));
        Exception error=Assertions.assertThrows(Exception.class,() ->{
            leagueModelParameterized.checkIfLeagueModelValid(validate);
        });
        Assertions.assertTrue(error.getMessage().contains("The names of conferences inside a league must be unique"));
    }
    @AfterEach
    public void destroyObject(){
        leagueModel=null;
        parsedOutput=null;
    }
}


