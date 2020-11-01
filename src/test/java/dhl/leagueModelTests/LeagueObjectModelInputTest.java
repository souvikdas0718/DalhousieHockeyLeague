package dhl.leagueModelTests;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.database.interfaceDB.ILeagueObjectModelDB;
import dhl.leagueModel.CommonValidation;
import dhl.leagueModel.LeagueObjectModelInput;
import dhl.leagueModel.LeagueObjectModelValidation;
import dhl.leagueModel.interfaceModel.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LeagueObjectModelInputTest {
    ILeagueObjectModelValidation leagueValidation;
    LeagueObjectModelMocks leagueMock;
    ILeagueObjectModelInput leagueObjectModelInput;
    ILeagueObjectModelDB leagueObjectModelDB;

    @BeforeEach
    public void initialize(){
        leagueValidation=new LeagueObjectModelValidation();
        leagueMock= new LeagueObjectModelMocks();
        leagueObjectModelDB = new MockDatabase();
        leagueObjectModelInput=new LeagueObjectModelInput("Dhl","Western","Atlantic",leagueMock.getTeamObjectMock(),leagueValidation, leagueObjectModelDB);
    }

    @Test
    public  void LeagueObjectModelInputTest(){
        Assertions.assertEquals(leagueObjectModelInput.getLeagueName(),"Dhl");
        Assertions.assertEquals(leagueObjectModelInput.getConferenceName(),"Western");
        Assertions.assertEquals(leagueObjectModelInput.getDivisionName(),"Atlantic");
        ITeam team = leagueObjectModelInput.getNewlyCreatedTeam();
        Assertions.assertEquals(team.getTeamName(),"Mock Team");
        Assertions.assertNotNull(leagueObjectModelInput.getLeagueObjectModelValidation());
        Assertions.assertNotNull(leagueObjectModelInput.getLeagueObjectModelDB());
    }

    @AfterEach
    public void destroyObject(){
        leagueMock=null;
        leagueObjectModelInput=null;
        leagueValidation=null;
    }


}
