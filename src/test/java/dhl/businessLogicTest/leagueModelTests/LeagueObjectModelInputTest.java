package dhl.businessLogicTest.leagueModelTests;

import dhl.mocks.factory.MockAbstractFactory;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModelInput;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.TeamMock;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LeagueObjectModelInputTest {
    ILeagueObjectModelInput leagueObjectModelInput;
    ISerializeLeagueObjectModel serializeLeagueObjectModel;
    LeagueModelAbstractFactory leagueFactory;
    LeagueModelMockAbstractFactory leagueMockFactory;
    MockAbstractFactory mockFactory;

    @BeforeEach
    public void initialize() {
        leagueFactory= LeagueModelAbstractFactory.instance();
        mockFactory= MockAbstractFactory.instance();
        serializeLeagueObjectModel = mockFactory.getMockSerialize();

        LeagueModelMockAbstractFactory leagueMockFactory= LeagueModelMockAbstractFactory.instance();
        TeamMock teamMock =  leagueMockFactory.createTeamMock();
        ITeam newlyCreatedTeam = teamMock.getTeamByName("Halifax");
        leagueObjectModelInput =leagueFactory.createLeagueObjectModelInput("Dhl", "Western", "Atlantic", newlyCreatedTeam, serializeLeagueObjectModel);
    }

    @Test
    public void LeagueObjectModelInputTest() {
        Assertions.assertEquals(leagueObjectModelInput.getLeagueName(), "Dhl");
        Assertions.assertEquals(leagueObjectModelInput.getConferenceName(), "Western");
        Assertions.assertEquals(leagueObjectModelInput.getDivisionName(), "Atlantic");
        ITeam team = leagueObjectModelInput.getNewlyCreatedTeam();
        Assertions.assertEquals(team.getTeamName(), "Halifax");
        Assertions.assertNotNull(leagueObjectModelInput.getLeagueObjectModelValidation());
        Assertions.assertNotNull(leagueObjectModelInput.getSerializeLeagueObjectModel());
    }

    @AfterEach
    public void destroyObject() {
        leagueObjectModelInput = null;
    }


}
