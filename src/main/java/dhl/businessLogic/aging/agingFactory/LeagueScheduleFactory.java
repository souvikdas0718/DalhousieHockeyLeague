package dhl.businessLogic.aging.agingFactory;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.InputOutput.importJson.Interface.ISerializeLeagueObjectModel;
import dhl.businessLogic.aging.LeagueSchedule;
import dhl.businessLogic.aging.interfaceAging.IAging;
import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.aging.interfaceAging.ILeagueSchedule;
import dhl.businessLogic.aging.interfaceAging.IRetirement;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;

public class LeagueScheduleFactory extends LeagueScheduleAbstractFactory{
    public ILeagueSchedule createAbstractLeagueSchedule(int daysSinceStartOfSimulation, ILeagueObjectModel leagueObjectModel){
        AgingAbstractFactory factory = AgingAbstractFactory.instance();
        IInjury injury = factory.createInjury();
        // TODO: 19-11-2020 ADD FACTORIES!
        IGameConfig gameConfig = null;
        ISerializeLeagueObjectModel serializeModel = null;
        IAging aging = factory.createAging(gameConfig);
        IRetirement retirement = factory.createRetirement(serializeModel,leagueObjectModel);

        return new LeagueSchedule(aging,retirement,injury,leagueObjectModel,daysSinceStartOfSimulation);
    }
}
