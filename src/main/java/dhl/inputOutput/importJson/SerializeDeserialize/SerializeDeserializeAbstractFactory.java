package dhl.inputOutput.importJson.SerializeDeserialize;

import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.traning.TrainingAbstractFactory;
import dhl.businessLogic.traning.TrainingFactory;
import dhl.businessLogic.traning.interfaces.ITraining;
import dhl.inputOutput.importJson.SerializeDeserialize.interfaces.IDeserializeLeagueObjectModel;
import dhl.inputOutput.importJson.SerializeDeserialize.interfaces.ISerializeLeagueObjectModel;
import dhl.inputOutput.importJson.interfaces.IGameConfig;

public abstract class SerializeDeserializeAbstractFactory {
    private static SerializeDeserializeAbstractFactory uniqueInstance = null;

    protected SerializeDeserializeAbstractFactory(){

    }

    public static SerializeDeserializeAbstractFactory instance(){
        if (null == uniqueInstance){
            uniqueInstance = new SerializeDeserializeFactory();
        }
        return uniqueInstance;
    }

    public static void setFactory(SerializeDeserializeAbstractFactory f) { uniqueInstance = f; }

    public abstract ISerializeLeagueObjectModel createSerializeLeagueObjectModel();
    public abstract IDeserializeLeagueObjectModel createDeserializeLeagueObjectModel();
}
