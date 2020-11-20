package dhl.businessLogicTest.leagueModelTests;

import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;

import java.io.IOException;
import java.util.List;

public class MockSerializeLeagueObjectModel implements ISerializeLeagueObjectModel {

    public void writeSerializedLeagueObjectToJsonFile(ILeagueObjectModel objLeagueObjectModel) throws IOException, Exception {

    }

    public void updateSerializedLeagueObjectToJsonFile(ILeagueObjectModel objLeagueObjectModel) throws IOException, Exception {

    }

    public void updateSerializedPlayerListToJsonFile(List<IPlayer> playersToRetire, String leagueName) throws IOException, Exception {

    }
}
