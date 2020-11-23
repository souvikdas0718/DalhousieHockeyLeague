package dhl.inputOutput.importJson.SerializeDeserialize;

import dhl.inputOutput.importJson.serializeDeserialize.interfaces.IDeserializeLeagueObjectModel;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;

public abstract class SerializeDeserializeAbstractFactory {
    private static SerializeDeserializeAbstractFactory uniqueInstance = null;

    protected SerializeDeserializeAbstractFactory() {

    }

    public static SerializeDeserializeAbstractFactory instance() {
        if (null == uniqueInstance) {
            uniqueInstance = new SerializeDeserializeFactory();
        }
        return uniqueInstance;
    }

    public static void setFactory(SerializeDeserializeAbstractFactory f) {
        uniqueInstance = f;
    }

    public abstract ISerializeLeagueObjectModel createSerializeLeagueObjectModel(String leagueName);

    public abstract IDeserializeLeagueObjectModel createDeserializeLeagueObjectModel(String leagueName);
}
