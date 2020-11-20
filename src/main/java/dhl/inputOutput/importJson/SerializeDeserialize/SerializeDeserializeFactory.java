package dhl.inputOutput.importJson.serializeDeserialize;

import dhl.inputOutput.importJson.serializeDeserialize.interfaces.IDeserializeLeagueObjectModel;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;

public class SerializeDeserializeFactory extends SerializeDeserializeAbstractFactory {

    public ISerializeLeagueObjectModel createSerializeLeagueObjectModel(String leagueName) {
        return new SerializeLeagueObjectModel(leagueName);
    }

    public IDeserializeLeagueObjectModel createDeserializeLeagueObjectModel(String leagueName) {
        return new DeserializeLeagueObjectModel(leagueName);
    }
}
