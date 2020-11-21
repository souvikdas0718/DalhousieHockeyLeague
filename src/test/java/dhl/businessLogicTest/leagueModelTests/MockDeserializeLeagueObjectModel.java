package dhl.businessLogicTest.leagueModelTests;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.IDeserializeLeagueObjectModel;
import org.json.simple.parser.ParseException;

import java.util.List;

public class MockDeserializeLeagueObjectModel implements IDeserializeLeagueObjectModel {

    public ILeagueObjectModel deserializeLeagueObjectJson(String leagueName) {
        return null;
    }

    public List<IPlayer> deserializePlayers(String leagueName) throws ParseException {
        return null;
    }
}
