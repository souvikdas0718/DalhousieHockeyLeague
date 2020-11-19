package dhl.businessLogicTest.training;

import dhl.inputOutput.importJson.GameConfig;
import dhl.inputOutput.importJson.ImportJsonFile;
import dhl.Mocks.JsonFilePathMock;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.leagueModel.Team;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogicTest.leagueModelTests.MockDatabase;
import dhl.database.interfaceDB.ILeagueObjectModelDB;
import dhl.businessLogic.aging.Injury;
import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.traning.Training;
import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TrainingTest {

    Training trainingParameterized;
    LeagueObjectModelMocks leagueObjectModelMocks;
    JsonFilePathMock filePathMock;
    ImportJsonFile importJsonFile;
    GameConfig gameConfig;

    @BeforeEach
    public void initObject() throws Exception {
        filePathMock = new JsonFilePathMock();
        importJsonFile = new ImportJsonFile(filePathMock.getFilePath());
        IInjury injurySystem = new Injury();
        gameConfig = new GameConfig(importJsonFile.getJsonObject());
        trainingParameterized = new Training(injurySystem, gameConfig);
        leagueObjectModelMocks = new LeagueObjectModelMocks();
    }

    @Test
    public void updatePlayerStatsTest() throws Exception {
        ILeagueObjectModelDB mockLeagueObject = new MockDatabase();
        ILeagueObjectModel newLeagueObject = new LeagueObjectModel();
        newLeagueObject = trainingParameterized.updatePlayerStats(mockLeagueObject.loadLeagueModel("Dhl", "Ontario"));

        Assertions.assertNotNull("Dhl", newLeagueObject.getLeagueName());
    }

    @Test
    public void playerStatLessThanHeadCoachStatTest() throws Exception {
        List<IPlayer> updatedPlayersList = new ArrayList<>();
        Double[] randomValues = {0.01, 0.2, 0.3, 0.1};
        ITeam team = new Team("Ontario","Sam",leagueObjectModelMocks.getSingleCoach(),updatedPlayersList);

        updatedPlayersList = trainingParameterized.playerStatLessThanHeadCoachStat(
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
        ITeam team = new Team("Ontario","Sam",leagueObjectModelMocks.getSingleCoach(),updatedPlayersList);

        trainingParameterized.gameConfig = new GameConfig(importJsonFile.getJsonObject());
        trainingParameterized.playerStatMoreThanHeadCoachStat(leagueObjectModelMocks.getPlayerArrayMock()
                , team, randomValues);
    }

    @Test
    public void getRandomValueTest() {
        Double randoValue = trainingParameterized.getRandomValue();
        Assertions.assertTrue(randoValue < 1);
    }
}
