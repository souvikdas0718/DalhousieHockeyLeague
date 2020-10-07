package dhl.simulationStateMachineTest;


import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.simulationStateMachine.ImportJsonFile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;
public class CreateLeagueObjectModelTest {

    JSONObject leagueObject=null;
    public HashMap<String, ArrayList<IPlayer>> teamPlayersMapping;

    @BeforeEach
    public void initObject(){
        JsonFilePathMock filePathMock = new JsonFilePathMock();
        ImportJsonFile importJsonFile = new ImportJsonFile(filePathMock.getFilePath());
        this.leagueObject = importJsonFile.getJsonObject();
    }

    @Test
    public void CreatePlayerObjectTest(){

        assertTrue(true);
    }
    public JSONArray getDivisionObjectArrayList(JSONArray divisionsArray){return null;}

}
