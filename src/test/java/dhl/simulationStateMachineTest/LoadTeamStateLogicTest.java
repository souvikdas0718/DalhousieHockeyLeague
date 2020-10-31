package dhl.simulationStateMachineTest;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.database.LeagueObjectModelData;
import dhl.database.interfaceDB.ILeagueObjectModelData;
import dhl.leagueModel.LeagueObjectModel;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.leagueModelTests.MockDatabase;
import dhl.simulationStateMachine.GameContext;
import dhl.simulationStateMachine.States.CreateTeamStateLogic;
import dhl.simulationStateMachine.States.Interface.ILoadTeamStateLogic;
import dhl.simulationStateMachine.States.LoadTeamStateLogic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoadTeamStateLogicTest {
    ILoadTeamStateLogic objLoadTeamStateLogic;

    @BeforeEach
    public void initObject(){
        objLoadTeamStateLogic = new LoadTeamStateLogic();
    }

    @Test
    public void findTeamOfLeagueInDatabaseTest() throws Exception {

        ILeagueObjectModel newInMemoryLeague = new LeagueObjectModel();
        GameContext ourGame=new GameContext();
        ILeagueObjectModelData databaseRefrenceOb = new MockDatabase();
        Boolean iTeamFound = objLoadTeamStateLogic.findTeamOfLeagueInDatabase("Dhl","Ontario",newInMemoryLeague,ourGame,databaseRefrenceOb);
        Assertions.assertEquals(true, iTeamFound);
    }
    @Test
    public void findTeamTest(){
        LeagueObjectModelMocks mocks = new LeagueObjectModelMocks();
        ILeagueObjectModel inMemoryLeague = mocks.getLeagueObjectMock();
        ITeam objTeam = objLoadTeamStateLogic.findTeam(inMemoryLeague, "Ontario");
        Assertions.assertEquals("Ontario",objTeam.getTeamName());
    }
}