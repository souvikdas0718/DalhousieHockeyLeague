package dhl.businessLogic.aging.interfaceAging;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;

public interface IAging {

    ILeagueObjectModel initiateAging() throws Exception;

    void initiateRetirementForAgedPlayers() throws Exception;

}
