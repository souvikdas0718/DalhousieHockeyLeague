package dhl.simulationStateMachineTest;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.database.interfaceDB.ILeagueObjectModelDB;
import dhl.leagueModel.LeagueObjectModel;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.leagueModelTests.MockDatabase;
import dhl.simulationStateMachine.GameContext;
import dhl.simulationStateMachine.States.LoadTeamStateLogic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoadTeamStateLogicTest {

    LoadTeamStateLogic objLoadTeamStateLogic;

    @BeforeEach
    public void initObject() {
        objLoadTeamStateLogic = new LoadTeamStateLogic();
    }

    @Test
    public void findTeamOfLeagueInDatabaseTest() throws Exception {
        ILeagueObjectModel newInMemoryLeague = new LeagueObjectModel();
        GameContext ourGame = new GameContext();
        ILeagueObjectModelDB databaseRefrenceOb = new MockDatabase();
        LoadTeamStateLogic objLoadTeamStateLogic = new LoadTeamStateLogic("Dhl", "Ontario");
        Boolean iTeamFound = objLoadTeamStateLogic.findTeamOfLeagueInDatabase(newInMemoryLeague, ourGame, databaseRefrenceOb);
        Assertions.assertEquals(true, iTeamFound);
    }

    @Test
    public void teamNotFoundOfLeagueInDatabaseTest() throws Exception {
        ILeagueObjectModel newInMemoryLeague = new LeagueObjectModel();
        GameContext ourGame = new GameContext();
        ILeagueObjectModelDB databaseRefrenceOb = new MockDatabase();
        LoadTeamStateLogic objLoadTeamStateLogic = new LoadTeamStateLogic("Dhl", "Ontario1");
        Boolean iTeamFound = objLoadTeamStateLogic.findTeamOfLeagueInDatabase(newInMemoryLeague, ourGame, databaseRefrenceOb);
        Assertions.assertEquals(false, iTeamFound);
    }

    @Test
    public void findTeamTest() {
        LeagueObjectModelMocks mocks = new LeagueObjectModelMocks();
        ILeagueObjectModel inMemoryLeague = mocks.getLeagueObjectMock();
        ITeam objTeam = objLoadTeamStateLogic.findTeam(inMemoryLeague, "Ontario");
        Assertions.assertEquals("Ontario", objTeam.getTeamName());
    }
}
