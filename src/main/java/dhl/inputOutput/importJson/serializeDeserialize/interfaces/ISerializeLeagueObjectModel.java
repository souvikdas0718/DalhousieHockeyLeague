package dhl.inputOutput.importJson.serializeDeserialize.interfaces;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

public interface ISerializeLeagueObjectModel {
    public Boolean writeSerializedLeagueObjectToJsonFile(ILeagueObjectModel objLeagueObjectModel) throws IOException;

    public Boolean updateSerializedLeagueObjectToJsonFile(ILeagueObjectModel objLeagueObjectModel) throws IOException;

    public Boolean updateSerializedPlayerListToJsonFile(List<IPlayer> playersToRetire, String leagueName) throws IOException, ParseException;
}
