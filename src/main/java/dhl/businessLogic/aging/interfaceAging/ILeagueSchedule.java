package dhl.businessLogic.aging.interfaceAging;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDate;

public interface ILeagueSchedule {

    ILeagueObjectModel initiateAging(int noOfDays, LocalDate currentDate) throws IOException, ParseException;

    void initiateRetirementForAgedPlayers() throws IOException, ParseException;

}
