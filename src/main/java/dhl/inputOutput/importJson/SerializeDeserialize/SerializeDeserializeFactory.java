package dhl.inputOutput.importJson.SerializeDeserialize;

import dhl.inputOutput.importJson.SerializeDeserialize.interfaces.IDeserializeLeagueObjectModel;
import dhl.inputOutput.importJson.SerializeDeserialize.interfaces.ISerializeLeagueObjectModel;

public class SerializeDeserializeFactory extends SerializeDeserializeAbstractFactory {
    @Override
    public ISerializeLeagueObjectModel createSerializeLeagueObjectModel() {
        return new SerializeLeagueObjectModel();
    }

    @Override
    public IDeserializeLeagueObjectModel createDeserializeLeagueObjectModel() {
        return new DeserializeLeagueObjectModel();
    }
}
