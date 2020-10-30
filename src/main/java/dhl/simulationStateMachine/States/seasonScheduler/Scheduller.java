package dhl.simulationStateMachine.States.seasonScheduler;

import dhl.leagueModel.LeagueObjectModel;
import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.simulationStateMachine.Interface.ISchedule;
import dhl.simulationStateMachine.Interface.IScheduller;

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

    public void generateTeamList(ILeagueObjectModel inMemoryLeague){

        for(IConference conference: inMemoryLeague.getConferences() ){
            for(IDivision division: conference.getDivisions()){
                for (ITeam team: division.getTeams()){
                    teamList.add(team);
                }
            }
        }
    }

    public void generateTeamSchedule(){
        int totalDays = calculateDays(2020, 2021);
        int totalGames = 82;

        for ( int i=0; i<teamList.size(); i++) {
//            z
            for( int j=i+1; j<teamList.size(); j++) {
                ISchedule match = new SeasonSchedule();
                match.setTeamOne(teamList.get(i));
                match.setTeamTwo(teamList.get(i));
                fullSeasonSchedule.add(match);
            }
        }
    }

    private  int calculateDays(int currentYear, int nextYear) {
        int totalDays = 181;
        if (nextYear % 4 == 0) {
            totalDays = totalDays + 1;
        }
        LocalDate localDate = LocalDate.of(nextYear,03,01);
        LocalDate fs = localDate.with(firstDayOfNextMonth())
                .with(nextOrSame(DayOfWeek.SATURDAY));

        return totalDays + fs.getDayOfMonth();
    }
//    private LocalDate generateGameDate() {
//
//        return LocalDat
//    }




}
