package dhl.businessLogicTest.simulationStateMachineTest;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.states.LoadTeamStateLogic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoadTeamStateLogicTest {

    LoadTeamStateLogic objLoadTeamStateLogic;

    @BeforeEach
    public void initObject() {
        objLoadTeamStateLogic = new LoadTeamStateLogic();
    }

//    @Test
//    public void findTeamOfLeagueInDatabaseTest() throws Exception {
//        ILeagueObjectModel newInMemoryLeague = new LeagueObjectModel();
//        GameContext ourGame = new GameContext();
//        IDeserializeLeagueObjectModel deserializeLeagueObjectModel = new MockDeserializeLeagueObjectModel();
//        LoadTeamStateLogic objLoadTeamStateLogic = new LoadTeamStateLogic("Dhl", "Ontario");
//        Boolean iTeamFound = objLoadTeamStateLogic.findTeamOfLeagueInDatabase(newInMemoryLeague, ourGame, deserializeLeagueObjectModel);
//        // TODO: 20-11-2020 Add missing JSON file
////        Assertions.assertEquals(true, iTeamFound);
//    }

//    @Test
//    public void teamNotFoundOfLeagueInDatabaseTest() throws Exception {
//        ILeagueObjectModel newInMemoryLeague = new LeagueObjectModel();
//        GameContext ourGame = new GameContext();
//        IDeserializeLeagueObjectModel deserializeLeagueObjectModel = new MockDeserializeLeagueObjectModel();
//        LoadTeamStateLogic objLoadTeamStateLogic = new LoadTeamStateLogic("Dhl", "Ontario1");
//        Boolean iTeamFound = objLoadTeamStateLogic.findTeamOfLeagueInDatabase(newInMemoryLeague, ourGame, deserializeLeagueObjectModel);
//        // TODO: 20-11-2020 Add missing json file
////        Assertions.assertEquals(false, iTeamFound);
//    }

    @Test
    public void findTeamTest() {
        LeagueObjectModelMocks mocks = new LeagueObjectModelMocks();
        ILeagueObjectModel inMemoryLeague = mocks.getLeagueObjectMock();
        ITeam objTeam = objLoadTeamStateLogic.findTeam(inMemoryLeague, "Ontario");
        Assertions.assertEquals("Ontario", objTeam.getTeamName());
    }
}
