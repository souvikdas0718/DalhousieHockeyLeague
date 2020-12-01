package dhl.businessLogicTest.leagueModelTests.mocks;

import dhl.mocks.JsonFilePathMock;
import dhl.mocks.factory.MockAbstractFactory;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.inputOutput.importJson.ImportJsonAbstractFactory;
import dhl.inputOutput.importJson.interfaces.IImportJsonFile;

public class GameplayConfigMock {
    private static final String AGINGCONFIGPATH ="src/test/java/dhl/importJsonTest/GameConfigMockFile.json";
    JsonFilePathMock filePathMock;
    LeagueModelAbstractFactory leagueFactory;
    ImportJsonAbstractFactory importJsonFactory;
    MockAbstractFactory mockFactory;

    public GameplayConfigMock (){
        mockFactory= MockAbstractFactory.instance();
        filePathMock = mockFactory.getJsonFilePath();
        leagueFactory = LeagueModelAbstractFactory.instance();
        importJsonFactory = ImportJsonAbstractFactory.instance();
    }

    public IGameConfig getGameplayConfig() {
        IGameConfig gameConfig = null;
        try{
            IImportJsonFile importJsonFile = importJsonFactory.createImportJsonFile(filePathMock.getFilePath());
            gameConfig = leagueFactory.createGameConfig(importJsonFile.getJsonObject());
        }
        catch (Exception exception) {
        }
        return gameConfig;
    }

    public IGameConfig getAgingGameConfig()  {
        IGameConfig gameConfig =null;
        try{
            IImportJsonFile importJsonFile = importJsonFactory.createImportJsonFile(AGINGCONFIGPATH);
            gameConfig = leagueFactory.createGameConfig(importJsonFile.getJsonObject());
        }
        catch (Exception exception) {
        }
        return gameConfig;
    }

}
