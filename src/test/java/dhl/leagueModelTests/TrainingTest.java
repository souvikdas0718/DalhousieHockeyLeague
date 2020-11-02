package dhl.leagueModelTests;

import dhl.InputOutput.importJson.GameConfig;
import dhl.InputOutput.importJson.ImportJsonFile;
import dhl.Mocks.JsonFilePathMock;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.leagueModel.interfaceModel.ITraining;
import dhl.leagueModel.Training;
import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;
import dhl.database.interfaceDB.ILeagueObjectModelDB;
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
        IInjurySystem injurySystem = new InjurySystem();
        gameConfig = new GameConfig(importJsonFile.getJsonObject());
        trainingParameterized = new Training(injurySystem,gameConfig);
        leagueObjectModelMocks = new LeagueObjectModelMocks();
    }

    @Test
    public void updatePlayerStatsTest() throws Exception{
        ILeagueObjectModelDB mockLeagueObject=new MockDatabase();
        ILeagueObjectModel newLeagueObject=new LeagueObjectModel();
        newLeagueObject = trainingParameterized.updatePlayerStats(mockLeagueObject.loadLeagueModel("Dhl","Ontario"));

        Assertions.assertNotNull("Dhl", newLeagueObject.getLeagueName());
    }

    @Test
    public void playerStatLessThanHeadCoachStatTest() throws Exception{
        List<IPlayer> updatedPlayersList=new ArrayList<>();
        Double [] randomValues = {0.1,0.2,0.3,0.4};

        updatedPlayersList = trainingParameterized.playerStatLessThanHeadCoachStat(
                leagueObjectModelMocks.getPlayerArrayMock(),
                leagueObjectModelMocks.getSingleCoach(), randomValues);

        for(int i=0; i<updatedPlayersList.size(); i++){

            Assertions.assertEquals(10,updatedPlayersList.get(i).getPlayerStats().getSkating());
            Assertions.assertEquals(11,updatedPlayersList.get(i).getPlayerStats().getShooting());
            Assertions.assertEquals(11,updatedPlayersList.get(i).getPlayerStats().getChecking());
            Assertions.assertEquals(10,updatedPlayersList.get(i).getPlayerStats().getSaving());
        }
    }

    @Test
    public void playerStatMoreThanHeadCoachStatTest() throws Exception {
        List<IPlayer> updatedPlayersList=new ArrayList<>();
        Double [] randomValues = {0.1,0.2,0.3,0.4};

        trainingParameterized.gameConfig = new GameConfig(importJsonFile.getJsonObject());
        trainingParameterized.playerStatMoreThanHeadCoachStat(leagueObjectModelMocks.getPlayerArrayMock()
                , leagueObjectModelMocks.getSingleCoach(), randomValues);
    }
    
    @Test
    public void getRandomValueTest(){
        Double randoValue = trainingParameterized.getRandomValue();
        Assertions.assertTrue(randoValue<1);
    } 
}
