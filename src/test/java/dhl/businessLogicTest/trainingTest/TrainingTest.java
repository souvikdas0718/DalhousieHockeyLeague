package dhl.businessLogicTest.trainingTest;

import dhl.mocks.JsonFilePathMock;
import dhl.mocks.LeagueObjectModelMocks;
import dhl.businessLogic.aging.Injury;
import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.leagueModel.GameConfig;
import dhl.businessLogic.leagueModel.GeneralManager;
import dhl.businessLogic.leagueModel.Team;
import dhl.businessLogic.leagueModel.interfaceModel.IGeneralManager;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.traning.ITraining;
import dhl.businessLogic.traning.Training;
import dhl.businessLogic.traning.TrainingAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.LeagueMock;
import dhl.inputOutput.importJson.ImportJsonFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TrainingTest {

    TrainingAbstractFactory trainingAbstractFactory;
    ITraining trainingParameterized;
    Training training;
    LeagueObjectModelMocks leagueObjectModelMocks;
    JsonFilePathMock filePathMock;
    ImportJsonFile importJsonFile;
    GameConfig gameConfig;

    @BeforeEach
    public void initObject() throws Exception {
        trainingAbstractFactory = TrainingAbstractFactory.instance();
        filePathMock = new JsonFilePathMock();
        importJsonFile = new ImportJsonFile(filePathMock.getFilePath());
        IInjury injurySystem = new Injury();
        gameConfig = new GameConfig(importJsonFile.getJsonObject());
        trainingParameterized = trainingAbstractFactory.createTraining(injurySystem, gameConfig);
        leagueObjectModelMocks = new LeagueObjectModelMocks();
        training = new Training(injurySystem,gameConfig);
    }

    @Test
    public void updatePlayerStatsTest() throws Exception {
        LeagueModelMockAbstractFactory leagueMockFactory = LeagueModelMockAbstractFactory.instance();
        LeagueMock mock = leagueMockFactory.createLeagueMock();
        ILeagueObjectModel leagueObjectModel =  mock.getLeagueObjectModel();

        ILeagueObjectModel newLeagueObject = trainingParameterized.updatePlayerStats(leagueObjectModel);

        Assertions.assertNotNull("Dhl", newLeagueObject.getLeagueName());
    }

    @Test
    public void playerStatLessThanHeadCoachStatTest() throws Exception {
        List<IPlayer> updatedPlayersList = new ArrayList<>();
        Double[] randomValues = {0.01, 0.2, 0.3, 0.1};
        IGeneralManager manager = new GeneralManager("Sam", "normal");
        ITeam team = new Team("Ontario",manager,leagueObjectModelMocks.getSingleCoach(),updatedPlayersList);

        updatedPlayersList = training.playerStatLessThanHeadCoachStat(
                leagueObjectModelMocks.getPlayerArrayMock(),
                team, randomValues);

        for (int i = 0; i < updatedPlayersList.size(); i++) {

            Assertions.assertEquals(11, updatedPlayersList.get(i).getPlayerStats().getSkating());
            Assertions.assertEquals(11, updatedPlayersList.get(i).getPlayerStats().getShooting());
            Assertions.assertEquals(11, updatedPlayersList.get(i).getPlayerStats().getChecking());
            Assertions.assertEquals(11, updatedPlayersList.get(i).getPlayerStats().getSaving());
        }
    }

    @Test
    public void playerStatMoreThanHeadCoachStatTest() throws Exception {
        List<IPlayer> updatedPlayersList = new ArrayList<>();
        Double[] randomValues = {0.1, 0.2, 0.3, 0.4};

        IGeneralManager manager = new GeneralManager("Sam", "normal");
        ITeam team = new Team("Ontario",manager,leagueObjectModelMocks.getSingleCoach(),updatedPlayersList);

        training.gameConfig = new GameConfig(importJsonFile.getJsonObject());
        training.playerStatMoreThanHeadCoachStat(leagueObjectModelMocks.getPlayerArrayMock()
                , team, randomValues);
    }

    @Test
    public void getRandomValueTest() {
        Double randoValue = training.getRandomValue();
        Assertions.assertTrue(randoValue < 1);
    }
}
