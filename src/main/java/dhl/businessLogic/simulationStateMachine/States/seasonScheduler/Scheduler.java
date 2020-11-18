package dhl.businessLogic.simulationStateMachine.States.seasonScheduler;


import dhl.InputOutput.UI.IUserInputOutput;
import dhl.InputOutput.UI.UserInputOutput;
import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.Interface.ISchedule;
import dhl.businessLogic.simulationStateMachine.Interface.IScheduler;
import dhl.businessLogic.simulationStateMachine.Interface.IStandings;
import dhl.businessLogic.simulationStateMachine.States.standings.StandingSystem;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scheduler implements IScheduler {
    private List<ISchedule> fullSeasonSchedule;
    private List<ISchedule> playOffScheduleRound1;
//    private List<ISchedule> finals;
    private LocalDate seasonStartDate;
    private LocalDate seasonEndDate;
    private LocalDate playOffStartDate;
    private LocalDate currentDate;
    private LocalDate finalDay;
    private List<ITeam> teamList;
    private List<IConference> conferences;
    private List<IDivision> divisions;
    private List<IStandings> gameStandings;

    public Scheduler() {
        fullSeasonSchedule = new ArrayList<>();
        playOffScheduleRound1 = new ArrayList<>();
        playOffStartDate = LocalDate.of(2021, 03, 01);
        currentDate = playOffStartDate;
        teamList = new ArrayList<>();
        conferences = new ArrayList<>();
        divisions = new ArrayList<>();
        gameStandings = new ArrayList<>();
    }

    public List<ISchedule> getFullSeasonSchedule() {
        return fullSeasonSchedule;
    }

    public List<ISchedule> getPlayOffScheduleRound1() {
        return playOffScheduleRound1;
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

//    public void setTeamList(List<ITeam> teamList) {
//        this.teamList = teamList;
//    }

    public List<IConference> getConferences() {
        return conferences;
    }

//    public void setConferences(List<IConference> conferences) {
//        this.conferences = conferences;
//    }

    public List<IDivision> getDivisions() {
        return divisions;
    }

//    public void setDivisions(List<IDivision> divisions) {
//        this.divisions = divisions;
//    }


//    public List<ISchedule> getFinals() {
//        return finals;
//    }
//
//    public void setFinals(List<ISchedule> finals) {
//        this.finals = finals;
//    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    public List<IStandings> getGameStandings() {
        return gameStandings;
    }

    public void setGameStandings(List<IStandings> gameStandings) {
        this.gameStandings = gameStandings;
    }

    public void generateTeamList(ILeagueObjectModel inMemoryLeague) {

        for (IConference conference : inMemoryLeague.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    teamList.add(team);
                    divisions.add(division);
                    conferences.add(conference);
                }
            }
        }
    }

    public void generateTeamSchedule(ILeagueObjectModel inMemoryLeague) {

        for (int i = 0; i < teamList.size(); i++) {
            for (int j = i + 1; j < teamList.size(); j++) {
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

    public void gameScheduleDates(LocalDate seasonStartDate, LocalDate seasonEndDate) {
        long noOfDaysInRegularSeason = ChronoUnit.DAYS.between(seasonStartDate, seasonEndDate) + 1;
        int totalNoOfGamesInRegularSeason = fullSeasonSchedule.size();

        int minGamesPerDay = (int) Math.floor(totalNoOfGamesInRegularSeason / noOfDaysInRegularSeason);
        int remainingGames = (int) (totalNoOfGamesInRegularSeason - noOfDaysInRegularSeason * minGamesPerDay);

        int startingIndex = 0;
        LocalDate dateIndex = seasonStartDate;
        while (startingIndex < totalNoOfGamesInRegularSeason) {
            for (int games = 1; games <= minGamesPerDay; games++) {
                fullSeasonSchedule.get(startingIndex).setGameDate(dateIndex);
                startingIndex = startingIndex + 1;
            }
            if (remainingGames > 0) {
                fullSeasonSchedule.get(startingIndex).setGameDate(dateIndex);
                remainingGames = remainingGames - 1;
            }
            dateIndex = dateIndex.plusDays(1);
        }
    }

    public void playOffs(List<IStandings> regularGamesStandings, ILeagueObjectModel leagueObjectModel) {

        gameStandings = regularGamesStandings;
        StandingSystem standingSystem = new StandingSystem();

        IConference conference1 = leagueObjectModel.getConferences().get(0);
        IConference conference2 = leagueObjectModel.getConferences().get(1);

        List<IDivision> divisionList = new ArrayList<>();
        divisionList.add(conference1.getDivisions().get(0));
        divisionList.add(conference1.getDivisions().get(1));
        divisionList.add(conference2.getDivisions().get(0));
        divisionList.add(conference2.getDivisions().get(1));

        List<IStandings> conference1StandingList = new ArrayList<>();
        List<IStandings> conference2StandingList = new ArrayList<>();

        HashMap<Integer, List<IStandings>> divisionStandingMap = new HashMap<>();
        divisionStandingMap.put(1, new ArrayList<>());
        divisionStandingMap.put(2, new ArrayList<>());
        divisionStandingMap.put(3, new ArrayList<>());
        divisionStandingMap.put(4, new ArrayList<>());

        addStandingsToConferences(regularGamesStandings, conference1, conference2, divisionList, conference1StandingList, conference2StandingList, divisionStandingMap);

        generateRanks(standingSystem, conference1StandingList, conference2StandingList, divisionStandingMap);

        Map<Integer, List<IStandings>> conferenceWildCardListMap = new HashMap<>();
        conferenceWildCardListMap.put(1, new ArrayList<>());
        conferenceWildCardListMap.put(2, new ArrayList<>());

        addDivisionStandingsToConference(divisionStandingMap, conferenceWildCardListMap);

        standingSystem.rankGenerator(conferenceWildCardListMap.get(1));
        conferenceWildCardListMap.get(1).remove(2);
        conferenceWildCardListMap.get(1).remove(2);

        standingSystem.rankGenerator(conferenceWildCardListMap.get(2));
        conferenceWildCardListMap.get(2).remove(2);
        conferenceWildCardListMap.get(2).remove(2);


        ISchedule match1 = setMatchConferenceAndDivision(conference1, conference1StandingList.get(0).getTeamDivision(), conferenceWildCardListMap.get(1).get(1).getTeamDivision());
        setTeams(match1, conference1StandingList.get(0).getTeam(), conferenceWildCardListMap.get(1).get(1).getTeam());
        currentDate = currentDate.plusDays(1);
        match1.setGameDate(currentDate);
        playOffScheduleRound1.add(match1);

        IDivision conference1LeadDivision = conference1StandingList.get(0).getTeamDivision();
        IDivision anotherDivision = divisionList.get(0);
        if (divisionList.get(0).getDivisionName().equals(conference1LeadDivision.getDivisionName())) {
            anotherDivision = divisionList.get(1);
        }

        ISchedule match2 = setMatchConferenceAndDivision(conference1, divisionList.get(0), divisionList.get(0));
        setTeams(match2, divisionStandingMap.get(1).get(1).getTeam(), divisionStandingMap.get(1).get(2).getTeam());
        currentDate = currentDate.plusDays(1);
        match2.setGameDate(currentDate);
        playOffScheduleRound1.add(match2);

        ISchedule match3 = setMatchConferenceAndDivision(conference1, anotherDivision, conferenceWildCardListMap.get(1).get(0).getTeamDivision());
        if (anotherDivision == divisionList.get(0)) {
            setTeams(match3, divisionStandingMap.get(1).get(0).getTeam(), conferenceWildCardListMap.get(1).get(0).getTeam());
        } else if (anotherDivision == divisionList.get(1)) {
            setTeams(match3, divisionStandingMap.get(2).get(0).getTeam(), conferenceWildCardListMap.get(1).get(0).getTeam());
        }
        currentDate = currentDate.plusDays(1);
        match3.setGameDate(currentDate);
        playOffScheduleRound1.add(match3);

        ISchedule match4 = setMatchConferenceAndDivision(conference1, divisionList.get(1), divisionList.get(1));
        setTeams(match4, divisionStandingMap.get(2).get(1).getTeam(), divisionStandingMap.get(2).get(2).getTeam());
        currentDate = currentDate.plusDays(1);
        match4.setGameDate(currentDate);
        playOffScheduleRound1.add(match4);

        ISchedule match5 = setMatchConferenceAndDivision(conference2, conference2StandingList.get(0).getTeamDivision(), conferenceWildCardListMap.get(2).get(1).getTeamDivision());
        setTeams(match5, conference2StandingList.get(0).getTeam(), conferenceWildCardListMap.get(2).get(1).getTeam());
        currentDate = currentDate.plusDays(1);
        match5.setGameDate(currentDate);
        playOffScheduleRound1.add(match5);

        IDivision conference2LeadDivision = conference2StandingList.get(0).getTeamDivision();
        IDivision anotherDivision2 = divisionList.get(2);
        if (divisionList.get(2).getDivisionName().equals(conference2LeadDivision.getDivisionName())) {
            anotherDivision2 = divisionList.get(3);
        }

        ISchedule match6 = setMatchConferenceAndDivision(conference2, divisionList.get(2), divisionList.get(2));
        setTeams(match6, divisionStandingMap.get(3).get(1).getTeam(), divisionStandingMap.get(3).get(2).getTeam());
        currentDate = currentDate.plusDays(1);
        match6.setGameDate(currentDate);
        playOffScheduleRound1.add(match6);

        ISchedule match7 = setMatchConferenceAndDivision(conference2, anotherDivision2, conferenceWildCardListMap.get(2).get(0).getTeamDivision());
        if (anotherDivision2 == divisionList.get(2)) {
            setTeams(match7, divisionStandingMap.get(3).get(0).getTeam(), conferenceWildCardListMap.get(2).get(0).getTeam());
        } else if (anotherDivision2 == divisionList.get(3)) {
            setTeams(match7, divisionStandingMap.get(4).get(0).getTeam(), conferenceWildCardListMap.get(2).get(0).getTeam());
        }
        currentDate = currentDate.plusDays(1);
        match7.setGameDate(currentDate);
        playOffScheduleRound1.add(match7);

        ISchedule match8 = setMatchConferenceAndDivision(conference2, divisionList.get(3), divisionList.get(3));
        setTeams(match8, divisionStandingMap.get(4).get(1).getTeam(), divisionStandingMap.get(4).get(2).getTeam());
        currentDate = currentDate.plusDays(1);
        match8.setGameDate(currentDate);
        playOffScheduleRound1.add(match8);
    }

    private ISchedule setMatchConferenceAndDivision(IConference conference, IDivision division1, IDivision division2) {
        ISchedule match = new SeasonSchedule();
        match.setTeamOneConference(conference);
        match.setTeamTwoConference(conference);
        match.setTeamOneDivision(division1);
        match.setTeamTwoDivision(division2);
        return match;
    }

    private void setTeams(ISchedule match, ITeam team1, ITeam team2) {
        match.setTeamOne(team1);
        match.setTeamTwo(team2);
    }

    private void generateRanks(StandingSystem standingSystem, List<IStandings> conference1StandingList, List<IStandings> conference2StandingList, HashMap<Integer, List<IStandings>> divisionStandingMap) {
        standingSystem.rankGenerator(conference1StandingList);
        standingSystem.rankGenerator(conference2StandingList);

        standingSystem.rankGenerator(divisionStandingMap.get(1));
        standingSystem.rankGenerator(divisionStandingMap.get(2));
        standingSystem.rankGenerator(divisionStandingMap.get(3));
        standingSystem.rankGenerator(divisionStandingMap.get(4));
    }

    private void addDivisionStandingsToConference(HashMap<Integer, List<IStandings>> divisionStandingMap, Map<Integer, List<IStandings>> conferenceWildCardListMap) {
        conferenceWildCardListMap.get(1).add(divisionStandingMap.get(1).get(3));
        conferenceWildCardListMap.get(1).add(divisionStandingMap.get(1).get(4));
        conferenceWildCardListMap.get(1).add(divisionStandingMap.get(2).get(3));
        conferenceWildCardListMap.get(1).add(divisionStandingMap.get(2).get(4));

        conferenceWildCardListMap.get(2).add(divisionStandingMap.get(3).get(3));
        conferenceWildCardListMap.get(2).add(divisionStandingMap.get(3).get(4));
        conferenceWildCardListMap.get(2).add(divisionStandingMap.get(4).get(3));
        conferenceWildCardListMap.get(2).add(divisionStandingMap.get(4).get(4));
    }

    private void addStandingsToConferences(List<IStandings> regularGamesStandings, IConference conference1, IConference conference2, List<IDivision> divisionList, List<IStandings> conference1StandingList, List<IStandings> conference2StandingList, HashMap<Integer, List<IStandings>> divisionStandingMap) {
        for (IStandings standings : regularGamesStandings) {
            if (standings.getTeamConference().getConferenceName().equals(conference1.getConferenceName())) {
                if (standings.getTeamDivision().getDivisionName().equals(divisionList.get(0).getDivisionName())) {
                    divisionStandingMap.get(1).add(standings);
                } else if (standings.getTeamDivision().getDivisionName().equals(divisionList.get(1).getDivisionName())) {
                    divisionStandingMap.get(2).add(standings);
                }
                conference1StandingList.add(standings);
            } else if (standings.getTeamConference().getConferenceName().equals(conference2.getConferenceName())) {
                if (standings.getTeamDivision().getDivisionName().equals(divisionList.get(2).getDivisionName())) {
                    divisionStandingMap.get(3).add(standings);
                } else if (standings.getTeamDivision().getDivisionName().equals(divisionList.get(3).getDivisionName())) {
                    divisionStandingMap.get(4).add(standings);
                }
                conference2StandingList.add(standings);
            }
        }
    }

    public void gameWinner(ITeam team) {

        ISchedule lastSchedule = playOffScheduleRound1.get(playOffScheduleRound1.size() - 1);
        IStandings standing = getTeamIndexFromStanding(team);

        if (standing != null) {
            if (lastSchedule.getTeamTwo().getTeamName().isEmpty()) {
                if (standing != null) {
                    lastSchedule.setTeamTwoConference(standing.getTeamConference());
                    lastSchedule.setTeamTwoDivision(standing.getTeamDivision());
                    lastSchedule.setTeamTwo(team);
                }
            } else {
                ISchedule match = new SeasonSchedule();
                match.setTeamOneConference(standing.getTeamConference());
                match.setTeamOneDivision(standing.getTeamDivision());
                match.setTeamOne(team);
                currentDate = currentDate.plusDays(1);
                match.setGameDate(currentDate);
                playOffScheduleRound1.add(match);
                if(playOffScheduleRound1.size() == 15) {
                    setFinalDay(playOffScheduleRound1.get(14).getGameDate());
                }
            }
        }
    }

    private IStandings getTeamIndexFromStanding(ITeam team) {
        for (IStandings standing : gameStandings) {
            if (standing.getTeam().getTeamName().equals(team.getTeamName())) {
                return standing;
            }
        }
        return null;
    }

    public boolean stanleyCupWinner(LocalDate date) {
        IUserInputOutput output = new UserInputOutput();
        output.printMessage("Today's date: " + date);
        if (playOffScheduleRound1.get(14).getGameDate().isBefore(date) || playOffScheduleRound1.get(14).getGameDate().isEqual(date)) {
            return true;
        } else {
            return false;
        }
    }

}
