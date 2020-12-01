package dhl.businessLogic.aging.agingFactory;

import dhl.businessLogic.aging.interfaceAging.IAging;
import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.aging.interfaceAging.ILeagueSchedule;
import dhl.businessLogic.aging.interfaceAging.IRetirement;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;

public abstract class AgingAbstractFactory {

    private static AgingAbstractFactory uniqueInstance = null;

    protected AgingAbstractFactory() {
    }

    public static AgingAbstractFactory instance() {
        if (null == uniqueInstance) {
            uniqueInstance = new AgingFactory();
        }
        return uniqueInstance;
    }

    public static void setFactory(AgingAbstractFactory f) {
        uniqueInstance = f;
    }

    public abstract IInjury createInjury();

    public abstract IRetirement createRetirement(ISerializeLeagueObjectModel serializeModel);

    public abstract IAging createAging(IGameConfig gameConfig);

    public abstract ILeagueSchedule createLeagueSchedule(ILeagueObjectModel leagueObjectModel);

}
