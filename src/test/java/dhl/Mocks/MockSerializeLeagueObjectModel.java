package dhl.Mocks;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;

import java.io.IOException;
import java.util.List;

public class MockSerializeLeagueObjectModel implements ISerializeLeagueObjectModel {

    public void writeSerializedLeagueObjectToJsonFile(ILeagueObjectModel objLeagueObjectModel)  {

    }

    public void updateSerializedLeagueObjectToJsonFile(ILeagueObjectModel objLeagueObjectModel)  {

    }

    public void updateSerializedPlayerListToJsonFile(List<IPlayer> playersToRetire, String leagueName)  {

    }
}
