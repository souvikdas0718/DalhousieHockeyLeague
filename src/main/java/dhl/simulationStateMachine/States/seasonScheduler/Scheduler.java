package dhl.simulationStateMachine.States.seasonScheduler;

import com.sun.org.apache.bcel.internal.generic.IDIV;
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
import dhl.simulationStateMachine.Interface.IStandingSystem;
import dhl.simulationStateMachine.Interface.IStandings;
import dhl.simulationStateMachine.States.standings.StandingSystem;

import java.time.temporal.ChronoUnit;

import java.time.LocalDate;
import  java.time.DayOfWeek;
import static java.time.temporal.TemporalAdjusters.*;

import java.util.ArrayList;
import java.util.List;

public class Scheduler implements IScheduller {
    private List<ISchedule> fullSeasonSchedule;
    private List<ISchedule> playOffSchedule;
    private LocalDate seasonStartDate;
    private LocalDate seasonEndDate;
    private LocalDate playOffStartDate;
    private LocalDate finalDay;
    private List<ITeam> teamList;
    private List<IConference> conferences;
    private List<IDivision> divisions;
    
    public Scheduler(){
        fullSeasonSchedule =  new ArrayList<>();
        playOffSchedule = new ArrayList<>();
    }

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

    public void playOffs(List<IStandings> regularGamesStandings, ILeagueObjectModel leagueObjectModel) {
        
        StandingSystem standingSystem = new StandingSystem();
        
        IConference conference1 = leagueObjectModel.getConferences().get(0);
        IConference conference2 = leagueObjectModel.getConferences().get(1);
        
        IDivision division1 = conference1.getDivisions().get(0);
        IDivision division2 = conference1.getDivisions().get(1);
        IDivision division3 = conference2.getDivisions().get(0);
        IDivision division4 = conference2.getDivisions().get(1);

        List<IStandings> conference1StandingList = new ArrayList<>();
        List<IStandings> conference2StandingList = new ArrayList<>();

        List<IStandings> division1StandingList = new ArrayList<>();
        List<IStandings> division2StandingList = new ArrayList<>();
        List<IStandings> division3StandingList = new ArrayList<>();
        List<IStandings> division4StandingList = new ArrayList<>();
        
        for (IStandings standings: regularGamesStandings){
            if (standings.getTeamConference().getConferenceName().equals(conference1.getConferenceName())) {
                if (standings.getTeamDivision().getDivisionName().equals(division1.getDivisionName())) {
                    division1StandingList.add(standings);
                }
                else if (standings.getTeamDivision().getDivisionName().equals(division2.getDivisionName())) {
                    division2StandingList.add(standings);
                }
                conference1StandingList.add(standings);
            }
            else if (standings.getTeamConference().getConferenceName().equals(conference2.getConferenceName())){
                if (standings.getTeamDivision().getDivisionName().equals(division3.getDivisionName())) {
                    division3StandingList.add(standings);
                }
                else if (standings.getTeamDivision().getDivisionName().equals(division4.getDivisionName())) {
                    division4StandingList.add(standings);
                }
                conference2StandingList.add(standings);
            }
        }

        standingSystem.rankGenerator(conference1StandingList);
        standingSystem.rankGenerator(conference2StandingList);
        
        standingSystem.rankGenerator(division1StandingList);
        standingSystem.rankGenerator(division2StandingList);
        standingSystem.rankGenerator(division3StandingList);
        standingSystem.rankGenerator(division4StandingList);
        
        List<IStandings> conferenceOneWildCardList = new ArrayList<>();
        conferenceOneWildCardList.add(division1StandingList.get(3));
        conferenceOneWildCardList.add(division1StandingList.get(4));
        conferenceOneWildCardList.add(division2StandingList.get(3));
        conferenceOneWildCardList.add(division2StandingList.get(4));

        List<IStandings> conferenceTwoWildCardList = new ArrayList<>();
        conferenceTwoWildCardList.add(division3StandingList.get(3));
        conferenceTwoWildCardList.add(division3StandingList.get(4));
        conferenceTwoWildCardList.add(division4StandingList.get(3));
        conferenceTwoWildCardList.add(division4StandingList.get(4));

        standingSystem.rankGenerator(conferenceOneWildCardList);
        conferenceOneWildCardList.remove(2);
        conferenceOneWildCardList.remove(2);

        standingSystem.rankGenerator(conferenceTwoWildCardList);
        conferenceTwoWildCardList.remove(2);
        conferenceTwoWildCardList.remove(2);

        ISchedule match1 = new SeasonSchedule();
        match1.setTeamOneConference(conference1);
        match1.setTeamTwoConference(conference1);
        match1.setTeamOneDivision(conference1StandingList.get(0).getTeamDivision());
        match1.setTeamTwoDivision(conferenceOneWildCardList.get(1).getTeamDivision());
        match1.setTeamOne(conference1StandingList.get(0).getTeamName());
        match1.setTeamTwo(conferenceOneWildCardList.get(1).getTeamName());
        playOffSchedule.add(match1);
        
        IDivision conference1LeadDivision = conference1StandingList.get(0).getTeamDivision();
        IDivision anotherDivision = division1;
        if (division1.getDivisionName().equals(conference1LeadDivision.getDivisionName())){
            anotherDivision = division2;
        }
//        if (division1 == conference1LeadDivision){
//            anotherDivision = division2;
//        }

        ISchedule match2 = new SeasonSchedule();
        match2.setTeamOneConference(conference1);
        match2.setTeamTwoConference(conference1);
        match2.setTeamOneDivision(division1);
        match2.setTeamTwoDivision(division1);
        match2.setTeamOne(division1StandingList.get(1).getTeamName());
        match2.setTeamTwo(division1StandingList.get(2).getTeamName());
        playOffSchedule.add(match2);

        ISchedule match3 = new SeasonSchedule();
        match3.setTeamOneConference(conference1);
        match3.setTeamTwoConference(conference1);
        match3.setTeamOneDivision(anotherDivision);
        match3.setTeamTwoDivision(conferenceOneWildCardList.get(0).getTeamDivision());
        if (anotherDivision == division1){
            match3.setTeamOne(division1StandingList.get(0).getTeamName());
        }
        else if (anotherDivision == division2){
            match3.setTeamOne(division2StandingList.get(0).getTeamName());
        }
        match3.setTeamTwo(conferenceOneWildCardList.get(0).getTeamName());
        playOffSchedule.add(match3);

        ISchedule match4 = new SeasonSchedule();
        match4.setTeamOneConference(conference1);
        match4.setTeamTwoConference(conference1);
        match4.setTeamOneDivision(division2);
        match4.setTeamTwoDivision(division2);
        match4.setTeamOne(division2StandingList.get(1).getTeamName());
        match4.setTeamTwo(division2StandingList.get(2).getTeamName());
        playOffSchedule.add(match4);

        ISchedule match5 = new SeasonSchedule();
        match5.setTeamOneConference(conference2);
        match5.setTeamTwoConference(conference2);
        match5.setTeamOneDivision(conference2StandingList.get(0).getTeamDivision());
        match5.setTeamTwoDivision(conferenceTwoWildCardList.get(1).getTeamDivision());
        match5.setTeamOne(conference2StandingList.get(0).getTeamName());
        match5.setTeamTwo(conferenceTwoWildCardList.get(1).getTeamName());
        playOffSchedule.add(match5);

        IDivision conference2LeadDivision = conference2StandingList.get(0).getTeamDivision();
        IDivision anotherDivision2 = division3;
        if (division3.getDivisionName().equals(conference2LeadDivision.getDivisionName()) ){
            anotherDivision2 = division4;
        }

        ISchedule match6 = new SeasonSchedule();
        match6.setTeamOneConference(conference2);
        match6.setTeamTwoConference(conference2);
        match6.setTeamOneDivision(division3);
        match6.setTeamTwoDivision(division3);
        match6.setTeamOne(division3StandingList.get(1).getTeamName());
        match6.setTeamTwo(division3StandingList.get(2).getTeamName());
        playOffSchedule.add(match6);

        ISchedule match7 = new SeasonSchedule();
        match7.setTeamOneConference(conference2);
        match7.setTeamTwoConference(conference2);
        match7.setTeamOneDivision(anotherDivision2);
        match7.setTeamTwoDivision(conferenceTwoWildCardList.get(0).getTeamDivision());
        if (anotherDivision2 == division3){
            match7.setTeamOne(division3StandingList.get(0).getTeamName());
        }
        else if (anotherDivision2 == division4){
            match7.setTeamOne(division4StandingList.get(0).getTeamName());
        }
        match7.setTeamTwo(conferenceTwoWildCardList.get(0).getTeamName());
        playOffSchedule.add(match7);

        ISchedule match8 = new SeasonSchedule();
        match8.setTeamOneConference(conference2);
        match8.setTeamTwoConference(conference2);
        match8.setTeamOneDivision(division4);
        match8.setTeamTwoDivision(division4);
        match8.setTeamOne(division4StandingList.get(1).getTeamName());
        match8.setTeamTwo(division4StandingList.get(2).getTeamName());
        playOffSchedule.add(match8);

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
