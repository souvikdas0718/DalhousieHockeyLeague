package dhl.inputOutput.importJson.serializeDeserialize.interfaces;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IDeserializeLeagueObjectModel {
    ILeagueObjectModel deserializeLeagueObjectJson(String leagueName) throws Exception;
    List<IPlayer> deserializePlayers(String leagueName) throws ParseException, IOException;
}
