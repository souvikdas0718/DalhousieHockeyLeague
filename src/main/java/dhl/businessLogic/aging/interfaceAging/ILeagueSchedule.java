package dhl.businessLogic.aging.interfaceAging;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;

public interface ILeagueSchedule {

    ILeagueObjectModel initiateAging() throws Exception;

    void initiateRetirementForAgedPlayers() throws Exception;

}
