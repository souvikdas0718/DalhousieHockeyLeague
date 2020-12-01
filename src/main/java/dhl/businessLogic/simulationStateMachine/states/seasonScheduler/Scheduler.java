package dhl.businessLogic.simulationStateMachine.states.seasonScheduler;


import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.factory.SchedulerAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.ISeasonSchedule;
import dhl.businessLogic.simulationStateMachine.states.standings.factory.StandingsAbstractFactory;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandingSystem;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scheduler implements IScheduler {
    private static final int DAY = 1;
    private static final int FINALMATCHNUMBER = 14;
    private static final Logger logger = LogManager.getLogger(Scheduler.class);
    SchedulerAbstractFactory schedulerAbstractFactory;
    StandingsAbstractFactory standingsAbstractFactory;
    private List<ISeasonSchedule> fullSeasonSchedule;
    private List<ISeasonSchedule> playOffScheduleRound1;
    private LocalDate seasonStartDate;
    private LocalDate seasonEndDate;
    private LocalDate playOffStartDate;
    private LocalDate currentDate;
    private LocalDate finalDay;
    private LocalDate finalMatchDate;
    private List<ITeam> teamList;
    private List<IConference> conferences;
    private List<IDivision> divisions;
    private List<IStandings> gameStandings;

    public Scheduler() {
        fullSeasonSchedule = new ArrayList<>();
        playOffScheduleRound1 = new ArrayList<>();
        teamList = new ArrayList<>();
        conferences = new ArrayList<>();
        divisions = new ArrayList<>();
        gameStandings = new ArrayList<>();
        schedulerAbstractFactory = SchedulerAbstractFactory.instance();
        standingsAbstractFactory = StandingsAbstractFactory.instance();
    }

    public LocalDate getFinalMatchDate() {
        return finalMatchDate;
    }

    public void setFullSeasonSchedule(List<ISeasonSchedule> fullSeasonSchedule) {
        this.fullSeasonSchedule = fullSeasonSchedule;
    }

    public void setFinalMatchDate(LocalDate finalMatchDate) {
        this.finalMatchDate = finalMatchDate;
    }

    public List<ISeasonSchedule> getFullSeasonSchedule() {
        return fullSeasonSchedule;
    }

    public List<ISeasonSchedule> getPlayOffScheduleRound1() {
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

    public List<IConference> getConferences() {
        return conferences;
    }

    public List<IDivision> getDivisions() {
        return divisions;
    }

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
        logger.info("Entered generate Team List");
        if (inMemoryLeague == null) {
            return;
        }

        logger.debug("appending the list of teams, divisions,and confernces");
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
        logger.debug("Entered generate Team Schedule");

        for (int i = 0; i < teamList.size(); i++) {
            for (int j = i + 1; j < teamList.size(); j++) {
                ISeasonSchedule match = schedulerAbstractFactory.getSeasonSchedule();
                match.setTeamOneConference(conferences.get(i));
                match.setTeamTwoConference(conferences.get(j));
                match.setTeamOneDivision(divisions.get(i));
                match.setTeamTwoDivision(divisions.get(j));
                match.setTeamOne(teamList.get(i));
                match.setTeamTwo(teamList.get(j));
                match.setMatchPlayed(false);
                match.setMatchToBePlayed(false);
                fullSeasonSchedule.add(match);
            }
        }
    }

    public void gameScheduleDates(LocalDate seasonStartDate, LocalDate seasonEndDate) {
        logger.debug("Entered game schedule Dates: season start date " + seasonStartDate.toString() + " season end date " + seasonEndDate.toString());
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
                startingIndex = startingIndex + 1;
            }
            dateIndex = dateIndex.plusDays(DAY);
        }
    }

    public void playOffs(List<IStandings> regularGamesStandings, ILeagueObjectModel leagueObjectModel) {
        logger.debug("Into the playOffs method");
        gameStandings = regularGamesStandings;
        IStandingSystem standingSystem = standingsAbstractFactory.getStandingSystem();

        IConference conference1 = leagueObjectModel.getConferences().get(0);
        IConference conference2 = leagueObjectModel.getConferences().get(1);

        logger.debug("Adding the list of divisions in divisionList");
        List<IDivision> divisionList = new ArrayList<>();
        divisionList.add(conference1.getDivisions().get(0));
        divisionList.add(conference1.getDivisions().get(1));
        divisionList.add(conference2.getDivisions().get(0));
        divisionList.add(conference2.getDivisions().get(1));

        List<IStandings> conference1StandingList = new ArrayList<>();
        List<IStandings> conference2StandingList = new ArrayList<>();

        logger.debug("creating a divisonsStandingMap");
        HashMap<Integer, List<IStandings>> divisionStandingMap = new HashMap<>();
        divisionStandingMap.put(1, new ArrayList<>());
        divisionStandingMap.put(2, new ArrayList<>());
        divisionStandingMap.put(3, new ArrayList<>());
        divisionStandingMap.put(4, new ArrayList<>());

        logger.debug("calling the addStandingToConference method");
        addStandingsToConferences(regularGamesStandings, conference1, conference2, divisionList, conference1StandingList, conference2StandingList, divisionStandingMap);

        logger.debug("calling the generateRanks method for generating the rankings");
        generateRanks(standingSystem, conference1StandingList, conference2StandingList, divisionStandingMap);

        Map<Integer, List<IStandings>> conferenceWildCardListMap = new HashMap<>();
        conferenceWildCardListMap.put(1, new ArrayList<>());
        conferenceWildCardListMap.put(2, new ArrayList<>());

        addDivisionStandingsToConference(divisionStandingMap, conferenceWildCardListMap);

        logger.debug("removing the last two teams from wild card list");
        standingSystem.rankGenerator(conferenceWildCardListMap.get(1));
        conferenceWildCardListMap.get(1).remove(2);
        conferenceWildCardListMap.get(1).remove(2);

        logger.debug("removing the last two teams from wild card list");
        standingSystem.rankGenerator(conferenceWildCardListMap.get(2));
        conferenceWildCardListMap.get(2).remove(2);
        conferenceWildCardListMap.get(2).remove(2);

        logger.debug("setting the playOff date to currentDate variable");
        currentDate = playOffStartDate;

        logger.debug("generating the playOff schedules for 8 matches as per the NHL palyoff rules");
        ISeasonSchedule match1 = setMatchConferenceAndDivision(conference1, conference1StandingList.get(0).getTeamDivision(), conferenceWildCardListMap.get(1).get(1).getTeamDivision());
        setTeams(match1, conference1StandingList.get(0).getTeam(), conferenceWildCardListMap.get(1).get(1).getTeam());
        match1.setGameDate(currentDate);
        match1.setMatchPlayed(false);
        match1.setMatchToBePlayed(false);
        playOffScheduleRound1.add(match1);
        logger.debug("playoff match 1 added");

        IDivision conference1LeadDivision = conference1StandingList.get(0).getTeamDivision();
        IDivision anotherDivision = divisionList.get(0);
        if (divisionList.get(0).getDivisionName().equals(conference1LeadDivision.getDivisionName())) {
            anotherDivision = divisionList.get(1);
        }

        ISeasonSchedule match2 = setMatchConferenceAndDivision(conference1, divisionList.get(0), divisionList.get(0));
        setTeams(match2, divisionStandingMap.get(1).get(1).getTeam(), divisionStandingMap.get(1).get(2).getTeam());
        currentDate = currentDate.plusDays(DAY);
        match2.setGameDate(currentDate);
        match2.setMatchPlayed(false);
        match2.setMatchToBePlayed(false);
        playOffScheduleRound1.add(match2);
        logger.debug("playoff match 2 added");

        ISeasonSchedule match3 = setMatchConferenceAndDivision(conference1, anotherDivision, conferenceWildCardListMap.get(1).get(0).getTeamDivision());
        if (anotherDivision == divisionList.get(0)) {
            setTeams(match3, divisionStandingMap.get(1).get(0).getTeam(), conferenceWildCardListMap.get(1).get(0).getTeam());
        } else if (anotherDivision == divisionList.get(1)) {
            setTeams(match3, divisionStandingMap.get(2).get(0).getTeam(), conferenceWildCardListMap.get(1).get(0).getTeam());
        }
        currentDate = currentDate.plusDays(DAY);
        match3.setGameDate(currentDate);
        match3.setMatchPlayed(false);
        match3.setMatchToBePlayed(false);
        playOffScheduleRound1.add(match3);
        logger.debug("playoff match 3 added");

        ISeasonSchedule match4 = setMatchConferenceAndDivision(conference1, divisionList.get(1), divisionList.get(1));
        setTeams(match4, divisionStandingMap.get(2).get(1).getTeam(), divisionStandingMap.get(2).get(2).getTeam());
        currentDate = currentDate.plusDays(DAY);
        match4.setGameDate(currentDate);
        match4.setMatchPlayed(false);
        match4.setMatchToBePlayed(false);
        playOffScheduleRound1.add(match4);
        logger.debug("playoff match 4 added");

        ISeasonSchedule match5 = setMatchConferenceAndDivision(conference2, conference2StandingList.get(0).getTeamDivision(), conferenceWildCardListMap.get(2).get(1).getTeamDivision());
        setTeams(match5, conference2StandingList.get(0).getTeam(), conferenceWildCardListMap.get(2).get(1).getTeam());
        currentDate = currentDate.plusDays(DAY);
        match5.setGameDate(currentDate);
        match5.setMatchPlayed(false);
        match5.setMatchToBePlayed(false);
        playOffScheduleRound1.add(match5);
        logger.debug("playoff match 5 added");

        IDivision conference2LeadDivision = conference2StandingList.get(0).getTeamDivision();
        IDivision anotherDivision2 = divisionList.get(2);
        if (divisionList.get(2).getDivisionName().equals(conference2LeadDivision.getDivisionName())) {
            anotherDivision2 = divisionList.get(3);
        }

        ISeasonSchedule match6 = setMatchConferenceAndDivision(conference2, divisionList.get(2), divisionList.get(2));
        setTeams(match6, divisionStandingMap.get(3).get(1).getTeam(), divisionStandingMap.get(3).get(2).getTeam());
        currentDate = currentDate.plusDays(DAY);
        match6.setMatchPlayed(false);
        match6.setMatchToBePlayed(false);
        match6.setGameDate(currentDate);
        playOffScheduleRound1.add(match6);
        logger.debug("playoff match 6 added");

        ISeasonSchedule match7 = setMatchConferenceAndDivision(conference2, anotherDivision2, conferenceWildCardListMap.get(2).get(0).getTeamDivision());
        if (anotherDivision2 == divisionList.get(2)) {
            setTeams(match7, divisionStandingMap.get(3).get(0).getTeam(), conferenceWildCardListMap.get(2).get(0).getTeam());
        } else if (anotherDivision2 == divisionList.get(3)) {
            setTeams(match7, divisionStandingMap.get(4).get(0).getTeam(), conferenceWildCardListMap.get(2).get(0).getTeam());
        }
        currentDate = currentDate.plusDays(DAY);
        match7.setGameDate(currentDate);
        match7.setMatchPlayed(false);
        match7.setMatchToBePlayed(false);
        playOffScheduleRound1.add(match7);
        logger.debug("playoff match 7 added");

        ISeasonSchedule match8 = setMatchConferenceAndDivision(conference2, divisionList.get(3), divisionList.get(3));
        setTeams(match8, divisionStandingMap.get(4).get(1).getTeam(), divisionStandingMap.get(4).get(2).getTeam());
        currentDate = currentDate.plusDays(DAY);
        match8.setMatchPlayed(false);
        match8.setMatchToBePlayed(false);
        match8.setGameDate(currentDate);
        playOffScheduleRound1.add(match8);
        logger.debug("playoff match 8 added");
    }

    private ISeasonSchedule setMatchConferenceAndDivision(IConference conference, IDivision division1, IDivision division2) {
        logger.debug("setting match conference and division");
        ISeasonSchedule match = schedulerAbstractFactory.getSeasonSchedule();
        match.setTeamOneConference(conference);
        match.setTeamTwoConference(conference);
        match.setTeamOneDivision(division1);
        match.setTeamTwoDivision(division2);
        return match;
    }

    private void setTeams(ISeasonSchedule match, ITeam team1, ITeam team2) {
        logger.debug("Setting match between two teams namely " + team1.getTeamName() + ", " + team2.getTeamName());
        match.setTeamOne(team1);
        match.setTeamTwo(team2);
    }

    private void generateRanks(IStandingSystem standingSystem, List<IStandings> conference1StandingList, List<IStandings> conference2StandingList, HashMap<Integer, List<IStandings>> divisionStandingMap) {
        logger.debug("generating the ranks based on various standings list by calling the rank generator method");
        standingSystem.rankGenerator(conference1StandingList);
        standingSystem.rankGenerator(conference2StandingList);

        standingSystem.rankGenerator(divisionStandingMap.get(1));
        standingSystem.rankGenerator(divisionStandingMap.get(2));
        standingSystem.rankGenerator(divisionStandingMap.get(3));
        standingSystem.rankGenerator(divisionStandingMap.get(4));
    }

    private void addDivisionStandingsToConference(HashMap<Integer, List<IStandings>> divisionStandingMap, Map<Integer, List<IStandings>> conferenceWildCardListMap) {
        logger.debug("Adding division standings to the conferences");
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
        logger.debug("Adding standings to the conferences");
        for (IStandings standings : regularGamesStandings) {
            if (standings.getTeamConference().getConferenceName().equals(conference1.getConferenceName())) {
                logger.debug("Adding standings to divisionStandingMap based on divisions for 1st conference");
                if (standings.getTeamDivision().getDivisionName().equals(divisionList.get(0).getDivisionName())) {
                    divisionStandingMap.get(1).add(standings);
                } else if (standings.getTeamDivision().getDivisionName().equals(divisionList.get(1).getDivisionName())) {
                    divisionStandingMap.get(2).add(standings);
                }
                conference1StandingList.add(standings);
            } else if (standings.getTeamConference().getConferenceName().equals(conference2.getConferenceName())) {
                logger.debug("Adding standings to divisionStandingMap based on divisions for 2nd conference");
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

        ISeasonSchedule lastSchedule = playOffScheduleRound1.get(playOffScheduleRound1.size() - 1);
        IStandings standing = getTeamIndexFromStanding(team);

        if (null == standing) {
            logger.debug(" General season standings cannot be null ");
        } else {
            if (lastSchedule.getTeamTwo().getTeamName().isEmpty()) {
                logger.debug("setting the second team in playOffScheduleRound1 list next match");
                if (null == standing) {
                    logger.debug("General season standings cannot be null");
                } else {
                    lastSchedule.setTeamTwoConference(standing.getTeamConference());
                    lastSchedule.setTeamTwoDivision(standing.getTeamDivision());
                    lastSchedule.setTeamTwo(team);
                }
            } else {
                if (playOffScheduleRound1.size() == 15) {
                    logger.debug("InCase the playOffScheduleRound1 list size is 15 then it means the season has ended");
                    return;
                }
                logger.debug("setting the fist team in playOffScheduleRound1 list next match");
                ISeasonSchedule match = schedulerAbstractFactory.getSeasonSchedule();
                match.setTeamOneConference(standing.getTeamConference());
                match.setTeamOneDivision(standing.getTeamDivision());
                match.setTeamOne(team);
                currentDate = currentDate.plusDays(DAY);
                match.setGameDate(currentDate);
                match.setMatchPlayed(false);
                match.setMatchToBePlayed(false);
                playOffScheduleRound1.add(match);
                if (playOffScheduleRound1.size() == 15) {
                    setFinalMatchDate(playOffScheduleRound1.get(FINALMATCHNUMBER).getGameDate());
                }
            }
            if (playOffScheduleRound1.size() == 15) {
                logger.debug("InCase the playOffScheduleRound1 list size is 15 then it means the season has ended");
                return;
            }
        }

    }

    private IStandings getTeamIndexFromStanding(ITeam team) {
        logger.debug("Team name requested for standings: ", team.getTeamName());
        for (IStandings standing : gameStandings) {
            if (standing.getTeam().getTeamName().equals(team.getTeamName())) {
                return standing;
            }
        }
        logger.debug("InCase of no standings");
        return null;
    }

    public boolean stanleyCupWinner(LocalDate date) {
        logger.debug("Today's date: " + date);
        if (null == finalMatchDate) {
            logger.debug("Winner not yet decided");
            return false;
        } else {
            logger.debug("Final Game date: " + finalMatchDate);
            if (finalMatchDate.isBefore(date) || finalMatchDate.isEqual(date)) {
                logger.debug("Winner decided");
                return true;
            } else {
                logger.debug("Winner not yet decided");
                return false;
            }
        }
    }

}
