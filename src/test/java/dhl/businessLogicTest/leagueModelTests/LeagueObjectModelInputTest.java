package dhl.businessLogicTest.leagueModelTests;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.leagueModel.LeagueObjectModelInput;
import dhl.businessLogic.leagueModel.LeagueObjectModelValidation;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModelInput;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModelValidation;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LeagueObjectModelInputTest {
    ILeagueObjectModelValidation leagueValidation;
    LeagueObjectModelMocks leagueMock;
    ILeagueObjectModelInput leagueObjectModelInput;
    ISerializeLeagueObjectModel serializeLeagueObjectModel;

    @BeforeEach
    public void initialize() {
        leagueValidation = new LeagueObjectModelValidation();
        leagueMock = new LeagueObjectModelMocks();
        serializeLeagueObjectModel = new MockSerializeLeagueObjectModel();
        leagueObjectModelInput = new LeagueObjectModelInput("Dhl", "Western", "Atlantic", leagueMock.getTeamObjectMock(), leagueValidation, serializeLeagueObjectModel);
    }

    @Test
    public void LeagueObjectModelInputTest() {
        Assertions.assertEquals(leagueObjectModelInput.getLeagueName(), "Dhl");
        Assertions.assertEquals(leagueObjectModelInput.getConferenceName(), "Western");
        Assertions.assertEquals(leagueObjectModelInput.getDivisionName(), "Atlantic");
        ITeam team = leagueObjectModelInput.getNewlyCreatedTeam();
        Assertions.assertEquals(team.getTeamName(), "Mock Team");
        Assertions.assertNotNull(leagueObjectModelInput.getLeagueObjectModelValidation());
        Assertions.assertNotNull(leagueObjectModelInput.getserializeLeagueObjectModel());
    }

    @AfterEach
    public void destroyObject() {
        leagueMock = null;
        leagueObjectModelInput = null;
        leagueValidation = null;
    }


}
