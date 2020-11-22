package dhl.businessLogic.aging.agingFactory;

import dhl.businessLogic.aging.Aging;
import dhl.businessLogic.aging.Injury;
import dhl.businessLogic.aging.Retirement;
import dhl.businessLogic.aging.interfaceAging.IAging;
import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.aging.interfaceAging.IRetirement;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;


public class AgingFactory extends AgingAbstractFactory {

    public IInjury createInjury(){
        return new Injury();
    }

    public IRetirement createRetirement(ISerializeLeagueObjectModel serializeModel, ILeagueObjectModel leagueObjectModel){
        return new Retirement(serializeModel,leagueObjectModel);
    }

    public IAging createAging(IGameConfig gameConfig){
        return new Aging(gameConfig);
    }

}
