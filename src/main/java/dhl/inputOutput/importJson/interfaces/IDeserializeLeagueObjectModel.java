package dhl.inputOutput.importJson.interfaces;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.List;

public interface IDeserializeLeagueObjectModel {
    ILeagueObjectModel deserializeLeagueObjectJson(JSONObject jsonLeagueObject);
    List<IPlayer> deserializePlayers(JSONArray arrPlayers) throws ParseException;
}
