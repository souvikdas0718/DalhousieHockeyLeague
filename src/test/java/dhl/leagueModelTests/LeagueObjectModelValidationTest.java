package dhl.leagueModelTests;

import dhl.Mocks.LeagueObjectModelMocks;
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
    ILeagueObjectModel leagueModelParameterized;

    @BeforeEach
    public void initialize(){
        validate=new CommonValidation();
        leagueValidation=new LeagueObjectModelValidation();
        LeagueObjectModelMocks leagueMock= new LeagueObjectModelMocks();
        leagueModelParameterized=leagueMock.getLeagueObjectMock();
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
