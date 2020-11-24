package dhl.businessLogic.aging.interfaceAging;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;

import java.time.LocalDate;

public interface ILeagueSchedule {

    ILeagueObjectModel initiateAging(int noOfDays, LocalDate currentDate) ;

    void initiateRetirementForAgedPlayers() ;

}
