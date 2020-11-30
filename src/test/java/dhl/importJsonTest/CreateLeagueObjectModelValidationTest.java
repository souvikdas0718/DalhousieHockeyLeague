package dhl.importJsonTest;

import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.*;
import dhl.inputOutput.importJson.CreatedLeagueValidation;
import dhl.inputOutput.importJson.ImportJsonAbstractFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CreateLeagueObjectModelValidationTest {
    LeagueObjectModel leagueModel;
    IValidation validate;
    ILeagueObjectModel leagueModelParameterized;

    LeagueModelAbstractFactory leagueFactory;
    LeagueModelMockAbstractFactory leagueMockFactory;
    LeagueModelMockAbstractFactory mockFactory;
    ImportJsonAbstractFactory importFactory;
    LeagueMock leagueMock;
    CreatedLeagueValidation createdLeagueValidation;

    @BeforeEach
    public void initialize()  {
        leagueFactory= LeagueModelAbstractFactory.instance();

        validate = leagueFactory.createCommonValidation();
        createdLeagueValidation = (CreatedLeagueValidation) ImportJsonAbstractFactory.instance().createdLeagueValidation(validate);

        leagueMockFactory=LeagueModelMockAbstractFactory.instance();
        leagueMock =  leagueMockFactory.createLeagueMock();
        mockFactory= LeagueModelMockAbstractFactory.instance();
        leagueModelParameterized = leagueMock.getLeagueObjectModel();

    }

    @Test
    public void checkCreatedLeagueObjectModel(){
        boolean status = createdLeagueValidation.checkCreatedLeagueObjectModel(leagueModelParameterized);
        Assertions.assertTrue(status);
    }

    @Test
    public void validateFreeAgentsTest()  {
        FreeAgentMock freeAgentMock = mockFactory.createFreeAgentMock();
        IPlayer freeAgent = freeAgentMock.getPlayerWithoutName();
        List<IPlayer> agents = new ArrayList<>();
        agents.add(freeAgent);
        Assertions.assertFalse(createdLeagueValidation.validateFreeAgents(agents));
    }

    @Test
    public void checkPlayerNameInvalidTest() {
        PlayerMock playerMock = mockFactory.createPlayerMock();
        IPlayer player = playerMock.getPlayerWithoutName();
        Assertions.assertFalse(createdLeagueValidation.checkPlayerValid(player));
    }

    @Test
    public void checkPlayerPositionInValidTest() {
        PlayerMock playerMock = mockFactory.createPlayerMock();
        IPlayer player = playerMock.getPlayerInvalidPosition();
        Assertions.assertFalse(createdLeagueValidation.checkPlayerValid(player));
    }

    @Test
    public void checkPlayerStatsInValidTest() {
        PlayerMock playerMock = mockFactory.createPlayerMock();
        IPlayer player = playerMock.getPlayerWithInvalidStats();
        List<IPlayer> players = new ArrayList<>();
        players.add(player);
        Assertions.assertFalse(createdLeagueValidation.validatePlayers(players));
    }

    @Test
    public void checkCaptainInValidTest() {
        PlayerMock playerMock = mockFactory.createPlayerMock();
        IPlayer player = playerMock.getInvalidPlayerCaptain();
        List<IPlayer> players = new ArrayList<>();
        players.add(player);
        Assertions.assertFalse(createdLeagueValidation.validatePlayers(players));
    }

    @Test
    public void checkCaptainValidTest() {
        PlayerMock playerMock = mockFactory.createPlayerMock();
        IPlayer player = playerMock.getPlayer();
        List<IPlayer> players = new ArrayList<>();
        players.add(player);
        Assertions.assertTrue(createdLeagueValidation.validatePlayers(players));
    }

    @Test
    public void checkCoachStateInValidTest() {
        CoachMock coachMock = mockFactory.createCoachMock();
        ICoach coach = coachMock.getCoachInvalidStat();
        Assertions.assertFalse(createdLeagueValidation.checkIfCoachValid(coach));
    }

    @Test
    public void checkCoachInvalid() {
        CoachMock coachMock = mockFactory.createCoachMock();
        ICoach coach = coachMock.getCoachInvalidStat();
        Assertions.assertFalse(createdLeagueValidation.checkIfCoachValid(coach));
    }

    @Test
    public void checkCoachNameInvalid() {
        CoachMock coachMock = mockFactory.createCoachMock();
        ICoach coach = coachMock.getCoachWithoutName();
        Assertions.assertFalse(createdLeagueValidation.checkIfCoachValid(coach));
    }

    @Test
    public void checkIfTeamSizeInvalidTest() {
        TeamMock teamMock = mockFactory.createTeamMock();
        ITeam team = teamMock.getInvalidSizeTeam();
        Assertions.assertFalse(createdLeagueValidation.checkIfTeamValid(team));
    }

    @Test
    public void checkIfTeamCaptainInvalidTest(){
        TeamMock teamMock = mockFactory.createTeamMock();
        ITeam team= teamMock.getTeamWithTwoCaptains();
        Assertions.assertFalse( createdLeagueValidation.checkIfTeamValid(team));
    }

    @Test
    public void checkTeamValidTest(){
        TeamMock teamMock = mockFactory.createTeamMock();
        ITeam team= teamMock.getTeam();
        Assertions.assertTrue( createdLeagueValidation.checkIfTeamValid(team));
    }

    @Test
    public void checkTeamNameInvalidTest(){
        TeamMock teamMock = mockFactory.createTeamMock();
        ITeam team= teamMock.getTeamByName("");
        Assertions.assertFalse( createdLeagueValidation.checkIfTeamValid(team));
    }

    @Test
    public void checkDivisionTest(){
        DivisionMock divisionMock = mockFactory.createDivisionMock();
        IDivision division= divisionMock.getDivisionWithName("");
        Assertions.assertFalse( createdLeagueValidation.checkIfDivisionValid(division));
    }

    @Test
    public void checkDivisionSameNameTest(){
        DivisionMock divisionMock = mockFactory.createDivisionMock();
        IDivision division= divisionMock.getDivisionWithSameTeamName();
        Assertions.assertFalse( createdLeagueValidation.checkIfDivisionValid(division));
    }

    @Test
    public void checkDivisionValidTest(){
        DivisionMock divisionMock = mockFactory.createDivisionMock();
        IDivision division= divisionMock.getDivision();
        Assertions.assertTrue( createdLeagueValidation.checkIfDivisionValid(division));
    }

    @Test
    public void checkConferenceInvalidTest(){
        ConferenceMock conferenceMock = mockFactory.createConferenceMock();
        IConference conference= conferenceMock.getConferenceWithSameDivisions();
        Assertions.assertFalse( createdLeagueValidation.checkIfConferenceValid(conference));
    }

    @Test
    public void checkConferenceOddDivisionInvalidTest(){
        ConferenceMock conferenceMock = mockFactory.createConferenceMock();
        IConference conference= conferenceMock.getConferenceOddDivision();
        Assertions.assertFalse( createdLeagueValidation.checkIfConferenceValid(conference));
    }

    @Test
    public void checkLeagueObjectModelSameDivisionTest(){
        LeagueMock leagueMock = mockFactory.createLeagueMock();
        ILeagueObjectModel leagueObjectModel= leagueMock.getLeagueObjectModelSameDivision();
        Assertions.assertFalse( createdLeagueValidation.checkCreatedLeagueObjectModel(leagueObjectModel));
    }

    @Test
    public void invalidateFreeAgentsTest()  {
        FreeAgentMock freeAgentMock = mockFactory.createFreeAgentMock();
        IPlayer freeAgent = freeAgentMock.getPlayerWithoutName();
        List<IPlayer> agents = new ArrayList<>();
        agents.add(freeAgent);
        leagueModelParameterized.setFreeAgents(agents);
        Assertions.assertFalse(createdLeagueValidation.checkCreatedLeagueObjectModel(leagueModelParameterized));
    }


    @AfterEach
    public void destroyObject() {
        leagueModel = null;
    }

}
