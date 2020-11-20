package dhl.importJsonTest;

import dhl.InputOutput.importJson.CreateLeagueObjectModel;
import dhl.InputOutput.importJson.ImportJsonFile;
import dhl.InputOutput.importJson.Interface.ICreateLeagueObjectModel;
import dhl.InputOutput.importJson.Interface.IImportJsonFile;
import dhl.Mocks.JsonFilePathMock;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    public void getLeagueObjectModelTest() throws Exception {
        ILeagueObjectModel leagueObjectModel = createLeagueObjectModel.getLeagueObjectModel();
        assertEquals(leagueObject.get("leagueName"),leagueObjectModel.getLeagueName());
    }

    @Test
    public void getLeagueObjectModelInvalidTest() throws Exception {
        IImportJsonFile importJsonFile = new ImportJsonFile(filePathMock.getIncorrectJsonfilepath());
        this.leagueObject = importJsonFile.getJsonObject();
        createLeagueObjectModel =new CreateLeagueObjectModel(leagueObject);
        ILeagueObjectModel leagueObjectModel = createLeagueObjectModel.getLeagueObjectModel();

        Assertions.assertEquals("",leagueObjectModel.getLeagueName());
    }


}
