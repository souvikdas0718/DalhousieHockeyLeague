package dhl.businessLogicTest.leagueModelTests.factory;

import dhl.Mocks.JsonFilePathMock;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.leagueModel.factory.LeagueObjectModelBuilder;
import dhl.businessLogic.leagueModel.factory.LeagueObjectModelDirector;
import dhl.businessLogic.leagueModel.factory.interfaceFactory.ILeagueObjectModelBuilder;
import dhl.businessLogic.leagueModel.factory.interfaceFactory.ILeagueObjectModelDirector;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.inputOutput.importJson.ImportJsonFile;
import dhl.inputOutput.importJson.interfaces.IImportJsonFile;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LeagueObjectModelDirectorTest {
    ILeagueObjectModelBuilder leagueBuilder;
    ILeagueObjectModelDirector leagueDirector;
    LeagueObjectModelMocks leagueMock = new LeagueObjectModelMocks();

    @BeforeEach
    public void initialize() {
        leagueBuilder = new LeagueObjectModelBuilder();
        leagueDirector = new LeagueObjectModelDirector(leagueBuilder);
    }

    @Test
    public void constructTest(){
        List<IConference> conferences = leagueMock.getConferenceArrayMock();
        List<IPlayer> agents = leagueMock.getFreeAgentArrayMock();
        List<ICoach> coaches = leagueMock.getCoaches();
        List<IGeneralManager> managers = leagueMock.getManagers();
        IGameConfig gameConfig = leagueMock.getGameConfig();
        ILeagueObjectModel leagueObjectModel = leagueDirector.construct("Dalhousie Hockey League",conferences,agents,coaches,managers,gameConfig);
        Assertions.assertEquals("Dalhousie Hockey League",leagueObjectModel.getLeagueName());
        Assertions.assertEquals(agents.size(),leagueObjectModel.getFreeAgents().size());
    }

    @Test
    public void constructFromJsonTest() throws Exception {
        JsonFilePathMock filePathMock = new JsonFilePathMock();
        IImportJsonFile importJsonFile = new ImportJsonFile(filePathMock.getFilePath());
        JSONObject leagueObject = importJsonFile.getJsonObject();
        ILeagueObjectModel leagueObjectModel =  leagueDirector.constructFromJson(leagueObject);
        Assertions.assertEquals("Dalhousie Hockey League",leagueObjectModel.getLeagueName());


    }
}
