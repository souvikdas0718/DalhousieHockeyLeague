package dhl.simulationStateMachine.states.seasonScheduler;

import dhl.leagueModel.Conference;
import dhl.leagueModel.Division;
import dhl.leagueModel.Team;
import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.simulationStateMachine.Interface.ISchedule;

import java.time.LocalDate;

public class SeasonSchedule implements ISchedule {

    private IConference teamOneConference;
    private IConference teamTwoConference;
    private IDivision teamOneDivision;
    private IDivision teamTwoDivision;
    private ITeam teamOne;
    private ITeam teamTwo;
    private LocalDate gameDate;

    public SeasonSchedule() {
        teamOneConference = new Conference();
        teamTwoConference = new Conference();
        teamOneDivision = new Division();
        teamTwoDivision = new Division();
        teamOne = new Team();
        teamTwo = new Team();
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

}
