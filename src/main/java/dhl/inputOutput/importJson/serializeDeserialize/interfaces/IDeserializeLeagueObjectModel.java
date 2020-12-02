package dhl.inputOutput.importJson.serializeDeserialize.interfaces;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

public interface IDeserializeLeagueObjectModel {
    ILeagueObjectModel deserializeLeagueObjectJson(String leagueName) throws IOException, ParseException;

    List<IPlayer> deserializePlayers(String leagueName) throws IOException, ParseException;
}
