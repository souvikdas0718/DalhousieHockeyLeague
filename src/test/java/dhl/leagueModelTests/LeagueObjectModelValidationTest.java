package dhl.leagueModelTests;

import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class LeagueObjectModelValidationTest {
    IValidation validate;
    ILeagueObjectModelValidation leagueValidation;
    LeagueObjectModel leagueModelParameterized;

    @BeforeEach
    public void initialize(){
        validate=new CommonValidation();
        leagueValidation=new LeagueObjectModelValidation();
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
        ArrayList<IFreeAgent> freeAgentsList=new ArrayList<>();
        leagueModelParameterized=new LeagueObjectModel("Dhl",conferences,freeAgentsList);
    }

    @Test
    public void checkIfLeagueObjectModelValidTest() throws Exception{
        ArrayList<IConference> conferences =leagueModelParameterized.getConferences();
        conferences.add(new Conference("Eastern",new ArrayList<IDivision>()));
        leagueModelParameterized.setConferences(conferences);
        Assertions.assertTrue(leagueValidation.checkIfLeagueObjectModelValid(validate,leagueModelParameterized));
    }

    @Test void checkIfLeagueHasEvenConferencesTest(){
        Exception error=Assertions.assertThrows(Exception.class,() ->{
            leagueValidation.checkIfLeagueObjectModelValid(validate,leagueModelParameterized);
        });
        Assertions.assertTrue(error.getMessage().contains("A League must contain even number of conferences"));
    }

    @Test
    public void checkIfConferenceNamesUniqueInLeagueTest() throws Exception{
        ArrayList<IConference> conferences =leagueModelParameterized.getConferences();
        conferences.add(new Conference("Western",new ArrayList<IDivision>()));
        Exception error=Assertions.assertThrows(Exception.class,() ->{
            leagueValidation.checkIfLeagueObjectModelValid(validate,leagueModelParameterized);
        });
        Assertions.assertTrue(error.getMessage().contains("The names of conferences inside a league must be unique"));
    }

    @Test
    void checkUserInputIncorrectLeagueTest() throws Exception{
        Exception error= Assertions.assertThrows(Exception.class,() ->{
            leagueValidation.checkUserInputForLeague(leagueModelParameterized,"Nhl","Western","Atlantic","Nova Scotia");
        });
        Assertions.assertTrue(error.getMessage().contains("League name is not present in file imported."));
    }

    @Test void checkUserInputIncorrectConferenceTest() throws Exception{
        Exception error=Assertions.assertThrows(Exception.class,() ->{
            leagueValidation.checkUserInputForLeague(leagueModelParameterized,"Dhl","Premier","Atlantic","Nova Scotia");
        });
        Assertions.assertTrue(error.getMessage().contains("Conference name is not present in file imported"));
    }

    @Test void checkUserInputIncorrectDivisionTest() throws Exception{
        Exception error=Assertions.assertThrows(Exception.class,() ->{
            leagueValidation.checkUserInputForLeague(leagueModelParameterized,"Dhl","Western","Metropolitan","Nova Scotia");
        });
        Assertions.assertTrue(error.getMessage().contains("Division name is not present in file imported"));
    }

    @Test void checkUserInputTeamAlreadyPresentTest() throws Exception{
        Exception error=Assertions.assertThrows(Exception.class,() ->{
            leagueValidation.checkUserInputForLeague(leagueModelParameterized,"Dhl","Western","Atlantic","Ontario");
        });
        Assertions.assertTrue(error.getMessage().contains("Team name entered is already present in file imported"));
    }

    @Test void checkUserInputForLeagueTest() throws Exception{
        Assertions.assertTrue(leagueValidation.checkUserInputForLeague(leagueModelParameterized,"Dhl","Western","Atlantic","Nova Scotia"));
    }

    @AfterEach
    public void destroyObject(){
        validate=null;
        leagueValidation=null;
        leagueModelParameterized=null;
    }
}
