package dhl.businessLogicTest.simulationStateMachineTest;

import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.factory.GameStateAbstractFactory;
import dhl.businessLogic.simulationStateMachine.interfaces.IGameContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IGameState;
import dhl.businessLogic.simulationStateMachine.states.*;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameContextTest {

    IGameContext testClassObject;
    GameStateAbstractFactory gameStateFactory;
    LeagueModelMockAbstractFactory leagueMockFactory;

    @BeforeEach
    public void initObject() {
        StatesAbstractFactory statesFactory = StatesAbstractFactory.instance();
        testClassObject = statesFactory.createGameContext();
        gameStateFactory = GameStateAbstractFactory.instance();
        leagueMockFactory = LeagueModelMockAbstractFactory.instance();

    }

    @Test
    public void setGameStateTest(){
        IGameState oldState = ((GameContext)testClassObject).getCurrentState();
        testClassObject.setGameState(gameStateFactory.createSimulateState((GameContext) testClassObject));
        IGameState newState = ((GameContext)testClassObject).getCurrentState();
        Assertions.assertFalse(newState == oldState);
    }

    @Test
    public void setYearTest(){
        ((GameContext)testClassObject).setYear(12);
        int year = ((GameContext)testClassObject).getYear();
        Assertions.assertEquals(12, year);
    }

    @Test
    public void getYearTest() {
        ((GameContext)testClassObject).setYear(12);
        int year = ((GameContext)testClassObject).getYear();
        Assertions.assertEquals(12, year);
    }

    @Test
    public void getInMemoryLeagueTest(){
        ILeagueObjectModel league = leagueMockFactory.createLeagueMock().getLeagueObjectModel();
        testClassObject.setInMemoryLeague(league);
        ILeagueObjectModel testLeague = testClassObject.getInMemoryLeague();

        Assertions.assertEquals(testLeague, league);
    }

    @Test
    public void setInMemoryLeagueTest(){
        ILeagueObjectModel league = leagueMockFactory.createLeagueMock().getLeagueObjectModel();
        testClassObject.setInMemoryLeague(league);
        ILeagueObjectModel testLeague = testClassObject.getInMemoryLeague();

        Assertions.assertEquals(testLeague, league);
    }

    @Test
    public void getSelectedTeamTest(){
        ITeam team = leagueMockFactory.createTeamMock().getTeam();
        testClassObject.setSelectedTeam(team);
        ITeam testTeam= testClassObject.getSelectedTeam();

        Assertions.assertEquals(testTeam, team);
    }

    @Test
    public void setSelectedTeamTest(){
        ITeam team = leagueMockFactory.createTeamMock().getTeam();
        testClassObject.setSelectedTeam(team);
        ITeam testTeam= testClassObject.getSelectedTeam();

        Assertions.assertEquals(testTeam, team);
    }

    @Test
    public void getSimulateStateTest(){
        Assertions.assertFalse(testClassObject.getSimulateState() == null);
        Assertions.assertTrue(testClassObject.getSimulateState() instanceof SimulateState);
    }

    @Test
    public void getImportStateTest(){
        Assertions.assertFalse(testClassObject.getImportState() == null);
        Assertions.assertTrue(testClassObject.getImportState() instanceof ImportState);
    }

    @Test
    public void getLoadTeamStateTest(){
        Assertions.assertFalse(testClassObject.getLoadTeamState() == null);
        Assertions.assertTrue(testClassObject.getLoadTeamState() instanceof LoadTeamState);
    }

    @Test
    public void getCreateTeamStateTest(){
        Assertions.assertFalse(testClassObject.getCreateTeamState() == null);
        Assertions.assertTrue(testClassObject.getCreateTeamState() instanceof CreateTeamState);
    }

    @Test
    public void isGameInProgressTest() {
        testClassObject.setGameInProgress(true);
        Assertions.assertTrue(testClassObject.isGameInProgress());

        testClassObject.setGameInProgress(false);
        Assertions.assertFalse(testClassObject.isGameInProgress());
    }

    @Test
    public void setGameInProgressTest() {
        testClassObject.setGameInProgress(true);
        Assertions.assertTrue(testClassObject.isGameInProgress());

        testClassObject.setGameInProgress(false);
        Assertions.assertFalse(testClassObject.isGameInProgress());
    }

    @Test
    public void getGameConfigTest() {
        IGameConfig gameConfig = leagueMockFactory.createGameplayConfig().getGameplayConfig();
        testClassObject.setGameConfig(gameConfig);

        Assertions.assertEquals(gameConfig , testClassObject.getGameConfig());
    }

    @Test
    public void setGameConfigTest() {
        IGameConfig gameConfig = leagueMockFactory.createGameplayConfig().getGameplayConfig();
        testClassObject.setGameConfig(gameConfig);

        Assertions.assertEquals(gameConfig , testClassObject.getGameConfig());
    }

    @Test
    public void getCurrentStateTest() {
        IGameState state = ((GameContext)testClassObject).getCurrentState();
        IGameState initState = testClassObject.getImportState();

        Assertions.assertEquals(state, initState);
    }
}
