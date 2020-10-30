package dhl.simulationStateMachine.States.seasonScheduler;

import dhl.leagueModel.Conference;
import dhl.leagueModel.Division;
import dhl.leagueModel.LeagueObjectModel;
import dhl.leagueModel.Team;
import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.simulationStateMachine.Interface.ISchedule;
import dhl.simulationStateMachine.Interface.IScheduller;
import java.time.temporal.ChronoUnit;

import java.time.LocalDate;
import  java.time.DayOfWeek;
import static java.time.temporal.TemporalAdjusters.*;
import java.util.List;

public class Scheduller implements IScheduller {
    private List<ISchedule> fullSeasonSchedule;
    private List<ISchedule> playOffSchedule;
    private LocalDate seasonStartDate;
    private LocalDate seasonEndDate;
    private LocalDate playOffStartDate;
    private LocalDate finalDay;
    private List<ITeam> teamList;
    private List<IConference> conferences;
    private List<IDivision> divisions;

    public List<ISchedule> getFullSeasonSchedule() {
        return fullSeasonSchedule;
    }

    public void setFullSeasonSchedule(List<ISchedule> fullSeasonSchedule) {
        this.fullSeasonSchedule = fullSeasonSchedule;
    }

    public List<ISchedule> getPlayOffSchedule() {
        return playOffSchedule;
    }

    public void setPlayOffSchedule(List<ISchedule> playOffSchedule) {
        this.playOffSchedule = playOffSchedule;
    }

    public LocalDate getSeasonStartDate() {
        return seasonStartDate;
    }

    public void setSeasonStartDate(LocalDate seasonStartDate) {
        this.seasonStartDate = seasonStartDate;
    }

    public LocalDate getSeasonEndDate() {
        return seasonEndDate;
    }

    public void setSeasonEndDate(LocalDate seasonEndDate) {
        this.seasonEndDate = seasonEndDate;
    }

    public LocalDate getPlayOffStartDate() {
        return playOffStartDate;
    }

    public void setPlayOffStartDate(LocalDate playOffStartDate) {
        this.playOffStartDate = playOffStartDate;
    }

    public LocalDate getFinalDay() {
        return finalDay;
    }

    public void setFinalDay(LocalDate finalDay) {
        this.finalDay = finalDay;
    }

    public List<ITeam> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<ITeam> teamList) {
        this.teamList = teamList;
    }

    public List<IConference> getConferences() {
        return conferences;
    }

    public void setConferences(List<IConference> conferences) {
        this.conferences = conferences;
    }

    public List<IDivision> getDivisions() {
        return divisions;
    }

    public void setDivisions(List<IDivision> divisions) {
        this.divisions = divisions;
    }

    public void generateTeamList(ILeagueObjectModel inMemoryLeague){

        for(IConference conference: inMemoryLeague.getConferences() ){
            for(IDivision division: conference.getDivisions()){
                for (ITeam team: division.getTeams()){
                    teamList.add(team);
                    divisions.add(division);
                    conferences.add(conference);
                }
            }
        }
    }

    public void generateTeamSchedule(ILeagueObjectModel inMemoryLeague){
//        int totalDays = calculateDays(2020, 2021);
//        int totalGames = 82;

        for ( int i=0; i<teamList.size(); i++) {
            for( int j=i+1; j<teamList.size(); j++) {
                ISchedule match = new SeasonSchedule();
                match.setTeamOneConference(conferences.get(i));
                match.setTeamTwoConference(conferences.get(j));
                match.setTeamOneDivision(divisions.get(i));
                match.setTeamTwoDivision(divisions.get(j));
                match.setTeamOne(teamList.get(i));
                match.setTeamTwo(teamList.get(j));
                fullSeasonSchedule.add(match);
            }
        }
    }

    public void gameScheduleDates( LocalDate seasonStartDate, LocalDate seasonEndDate ) {
        long noOfDaysInRegularSeason = ChronoUnit.DAYS.between(seasonStartDate, seasonEndDate) + 1;
        int totalNoOfGamesInRegularSeason = fullSeasonSchedule.size();

        int minGamesPerDay = (int) Math.floor(totalNoOfGamesInRegularSeason/noOfDaysInRegularSeason);
        int remainingGames = (int) (totalNoOfGamesInRegularSeason - noOfDaysInRegularSeason*minGamesPerDay);

        int startingIndex = 0;
        LocalDate dateIndex = seasonStartDate;
        while (startingIndex < totalNoOfGamesInRegularSeason) {
            for(int games = 1; games <= minGamesPerDay; games++){
                fullSeasonSchedule.get(startingIndex).setGameDate(dateIndex);
                startingIndex = startingIndex + 1;
            }
            if(remainingGames > 0) {
                fullSeasonSchedule.get(startingIndex).setGameDate(dateIndex);
                remainingGames = remainingGames - 1;
            }
            dateIndex = dateIndex.plusDays(1);
        }
    }

//    private  int calculateDays(int currentYear, int nextYear) {
//        int totalDays = 181;
//        if (nextYear % 4 == 0) {
//            totalDays = totalDays + 1;
//        }
//        LocalDate localDate = LocalDate.of(nextYear,03,01);
//        LocalDate fs = localDate.with(firstDayOfNextMonth())
//                .with(nextOrSame(DayOfWeek.SATURDAY));
//
//        return totalDays + fs.getDayOfMonth();
//    }
//    private LocalDate generateGameDate() {
//
//        return LocalDat
//    }




}
