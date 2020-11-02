package dhl.InputOutput.importJson.Interface;

import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import org.json.simple.JSONObject;

public interface IDeserializeLeagueObjectModel {
    ILeagueObjectModel deserializeLeagueObjectJson(JSONObject jsonLeagueObject);
}
