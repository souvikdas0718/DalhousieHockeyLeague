package dhl.businessLogic.aging.agingFactory;

import dhl.businessLogic.aging.interfaceAging.IAging;
import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.aging.interfaceAging.IRetirement;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;

public interface IAgingFactory {

    IInjury createInjury();

    IRetirement createRetirement(ISerializeLeagueObjectModel serializeModel, ILeagueObjectModel leagueObjectModel);

    IAging createAging(IGameConfig gameConfig);
}
