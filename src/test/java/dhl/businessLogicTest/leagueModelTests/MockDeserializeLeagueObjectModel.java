package dhl.businessLogicTest.leagueModelTests;

import dhl.InputOutput.importJson.Interface.IDeserializeLeagueObjectModel;
import dhl.InputOutput.importJson.Interface.ISerializeLeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.List;

public class MockDeserializeLeagueObjectModel implements IDeserializeLeagueObjectModel {

    public ILeagueObjectModel deserializeLeagueObjectJson(JSONObject jsonLeagueObject) {
        return null;
    }

    public List<IPlayer> deserializePlayers(JSONArray arrPlayers) throws ParseException {
        return null;
    }
}
