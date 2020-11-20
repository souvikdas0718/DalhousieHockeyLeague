package dhl.businessLogic.aging.agingFactory;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.InputOutput.importJson.Interface.ISerializeLeagueObjectModel;
import dhl.businessLogic.aging.LeagueSchedule;
import dhl.businessLogic.aging.interfaceAging.IAging;
import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.aging.interfaceAging.ILeagueSchedule;
import dhl.businessLogic.aging.interfaceAging.IRetirement;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;

public abstract class LeagueScheduleAbstractFactory {

    private static LeagueScheduleAbstractFactory uniqueInstance = null;

    protected LeagueScheduleAbstractFactory(){
    }

    public static LeagueScheduleAbstractFactory instance()
    {
        if (null == uniqueInstance)
        {
            uniqueInstance = new LeagueScheduleFactory();
        }
        return uniqueInstance;
    }

    public abstract ILeagueSchedule createAbstractLeagueSchedule(int daysSinceStartOfSimulation,ILeagueObjectModel leagueObjectModel);

}
