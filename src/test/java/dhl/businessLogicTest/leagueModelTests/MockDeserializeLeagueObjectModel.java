package dhl.businessLogicTest.leagueModelTests;

import dhl.inputOutput.importJson.serializeDeserialize.interfaces.IDeserializeLeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.List;

public class MockDeserializeLeagueObjectModel implements IDeserializeLeagueObjectModel {

    public ILeagueObjectModel deserializeLeagueObjectJson() {
        return null;
    }

    public List<IPlayer> deserializePlayers() throws ParseException {
        return null;
    }
}
