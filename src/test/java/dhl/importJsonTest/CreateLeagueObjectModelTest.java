package dhl.importJsonTest;

import dhl.Mocks.JsonFilePathMock;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.inputOutput.importJson.CreateLeagueObjectModel;
import dhl.inputOutput.importJson.ImportJsonFile;

import dhl.inputOutput.importJson.interfaces.IImportJsonFile;
import dhl.inputOutput.importJson.interfaces.ICreateLeagueObjectModel;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateLeagueObjectModelTest {
    JSONObject leagueObject;
    ICreateLeagueObjectModel createLeagueObjectModel;
    JsonFilePathMock filePathMock;

    @BeforeEach
    public void initObject() throws Exception {
        this.filePathMock = new JsonFilePathMock();
        IImportJsonFile importJsonFile = new ImportJsonFile(filePathMock.getFilePath());
        this.leagueObject = importJsonFile.getJsonObject();
        this.createLeagueObjectModel = new CreateLeagueObjectModel(leagueObject);
    }

    @Test
    public void getLeagueObjectModelTest()  {
        ILeagueObjectModel leagueObjectModel = createLeagueObjectModel.getLeagueObjectModel();
        assertEquals(leagueObject.get("leagueName"),leagueObjectModel.getLeagueName());
    }

    @Test
    public void getLeagueObjectModelInvalidTest() throws Exception {
        IImportJsonFile importJsonFile = new ImportJsonFile(filePathMock.getIncorrectJsonfilepath());
        this.leagueObject = importJsonFile.getJsonObject();
        createLeagueObjectModel =new CreateLeagueObjectModel(leagueObject);
        ILeagueObjectModel leagueObjectModel = createLeagueObjectModel.getLeagueObjectModel();

        Assertions.assertEquals(null,leagueObjectModel);
    }


}
