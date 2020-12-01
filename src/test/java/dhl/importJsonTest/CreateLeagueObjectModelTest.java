package dhl.importJsonTest;

import dhl.mocks.JsonFilePathMock;
import dhl.mocks.factory.MockAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.inputOutput.importJson.ImportJsonAbstractFactory;

import dhl.inputOutput.importJson.interfaces.IImportJsonFile;
import dhl.inputOutput.importJson.interfaces.ICreateLeagueObjectModel;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateLeagueObjectModelTest {
    MockAbstractFactory mockFactory;
    JSONObject leagueObject;
    ICreateLeagueObjectModel createLeagueObjectModel;
    JsonFilePathMock filePathMock;
    ImportJsonAbstractFactory importFactory;

    @BeforeEach
    public void initObject()  {
        mockFactory = MockAbstractFactory.instance();
        this.filePathMock = mockFactory.getJsonFilePath();
        importFactory = ImportJsonAbstractFactory.instance();
        IImportJsonFile importJsonFile = importFactory.createImportJsonFile(filePathMock.getFilePath());
        this.leagueObject = importJsonFile.getJsonObject();
        this.createLeagueObjectModel = importFactory.createCreateLeagueObjectModel(leagueObject);
    }

    @Test
    public void getLeagueObjectModelTest()  {
        ILeagueObjectModel leagueObjectModel = createLeagueObjectModel.getLeagueObjectModel();
        assertEquals(leagueObject.get("leagueName"),leagueObjectModel.getLeagueName());
    }

    @Test
    public void getLeagueObjectModelInvalidTest()  {
        IImportJsonFile importJsonFile = importFactory.createImportJsonFile(filePathMock.getIncorrectJsonfilepath());
        this.leagueObject = importJsonFile.getJsonObject();
        createLeagueObjectModel = importFactory.createCreateLeagueObjectModel(leagueObject);
        ILeagueObjectModel leagueObjectModel = createLeagueObjectModel.getLeagueObjectModel();

        Assertions.assertEquals(null,leagueObjectModel);
    }


}
