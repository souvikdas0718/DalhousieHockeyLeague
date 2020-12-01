package dhl.mocks;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;

import java.util.List;

public class MockSerializeLeagueObjectModel implements ISerializeLeagueObjectModel {

    public Boolean writeSerializedLeagueObjectToJsonFile(ILeagueObjectModel objLeagueObjectModel)  {
        return true;
    }

    public Boolean updateSerializedLeagueObjectToJsonFile(ILeagueObjectModel objLeagueObjectModel)  {
        return true;
    }

    public Boolean updateSerializedPlayerListToJsonFile(List<IPlayer> playersToRetire, String leagueName)  {
        return true;
    }
}
