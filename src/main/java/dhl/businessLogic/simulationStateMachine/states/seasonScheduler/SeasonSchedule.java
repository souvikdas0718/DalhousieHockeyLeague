package dhl.businessLogic.simulationStateMachine.states.seasonScheduler;

import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.ISeasonSchedule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class SeasonSchedule implements ISeasonSchedule {
    private static final Logger logger = LogManager.getLogger(Scheduler.class);
    LeagueModelAbstractFactory factory;
    private IConference teamOneConference;
    private IConference teamTwoConference;
    private IDivision teamOneDivision;
    private IDivision teamTwoDivision;
    private ITeam teamOne;
    private ITeam teamTwo;
    private LocalDate gameDate;
    private boolean isMatchPlayed;
    private boolean isMatchToBePlayed;

    public SeasonSchedule() {
        logger.info("Setting default values for Constructor");
        factory = LeagueModelAbstractFactory.instance();
        teamOneConference = factory.createConferenceDefault();
        teamTwoConference = factory.createConferenceDefault();
        teamOneDivision = factory.createDivisionDefault();
        teamTwoDivision = factory.createDivisionDefault();
        teamOne = factory.createTeamDefault();
        teamTwo = factory.createTeamDefault();
        isMatchPlayed = false;
    }

    public IConference getTeamOneConference() {
        return teamOneConference;
    }

    public void setTeamOneConference(IConference teamOneConference) {
        this.teamOneConference = teamOneConference;
    }

    public IConference getTeamTwoConference() {
        return teamTwoConference;
    }

    public void setTeamTwoConference(IConference teamTwoConference) {
        this.teamTwoConference = teamTwoConference;
    }

    public IDivision getTeamOneDivision() {
        return teamOneDivision;
    }

    public void setTeamOneDivision(IDivision teamOneDivision) {
        this.teamOneDivision = teamOneDivision;
    }

    public IDivision getTeamTwoDivision() {
        return teamTwoDivision;
    }

    public void setTeamTwoDivision(IDivision teamTwoDivision) {
        this.teamTwoDivision = teamTwoDivision;
    }

    public ITeam getTeamOne() {
        return teamOne;
    }

    public void setTeamOne(ITeam teamOne) {
        this.teamOne = teamOne;
    }

    public ITeam getTeamTwo() {
        return teamTwo;
    }

    public void setTeamTwo(ITeam teamTwo) {
        this.teamTwo = teamTwo;
    }

    public LocalDate getGameDate() {
        return gameDate;
    }

    public void setGameDate(LocalDate gameDate) {
        this.gameDate = gameDate;
    }

    public boolean isMatchPlayed() {
        return isMatchPlayed;
    }

    public void setMatchPlayed(boolean matchPlayed) {
        isMatchPlayed = matchPlayed;
    }

    public boolean isMatchToBePlayed() {
        return isMatchToBePlayed;
    }

    public void setMatchToBePlayed(boolean matchToBePlayed) {
        isMatchToBePlayed = matchToBePlayed;
    }
}
