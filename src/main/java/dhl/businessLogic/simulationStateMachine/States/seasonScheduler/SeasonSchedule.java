package dhl.businessLogic.simulationStateMachine.States.seasonScheduler;

import dhl.businessLogic.leagueModel.Conference;
import dhl.businessLogic.leagueModel.Division;
import dhl.businessLogic.leagueModel.Team;
import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.Interface.ISchedule;

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
