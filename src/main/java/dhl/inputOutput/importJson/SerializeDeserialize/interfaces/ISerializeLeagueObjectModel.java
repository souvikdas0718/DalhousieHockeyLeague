package dhl.inputOutput.importJson.serializeDeserialize.interfaces;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;

import java.io.IOException;
import java.util.List;

public interface ISerializeLeagueObjectModel {
    public void writeSerializedLeagueObjectToJsonFile(ILeagueObjectModel objLeagueObjectModel) throws IOException, Exception;
    public void updateSerializedLeagueObjectToJsonFile(ILeagueObjectModel objLeagueObjectModel) throws IOException, Exception;
    public void updateSerializedPlayerListToJsonFile(List<IPlayer> playersToRetire) throws IOException, Exception;
}
