package dhl.businessLogic.aging.Interface;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;

public interface IAging {

    ILeagueObjectModel initiateAging() throws Exception;

    void initiateRetirementForAgedPlayers() throws Exception;

}
