package dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces;

import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

import java.time.LocalDate;

public interface ISeasonSchedule {
    public IConference getTeamOneConference();

    public void setTeamOneConference(IConference teamOneConference);

    public IConference getTeamTwoConference();

    public void setTeamTwoConference(IConference teamTwoConference);

    public IDivision getTeamOneDivision();

    public void setTeamOneDivision(IDivision teamOneDivision);

    public IDivision getTeamTwoDivision();

    public void setTeamTwoDivision(IDivision teamTwoDivision);

    public ITeam getTeamOne();

    public void setTeamOne(ITeam teamOne);

    public ITeam getTeamTwo();

    public void setTeamTwo(ITeam teamTwo);

    public LocalDate getGameDate();

    public void setGameDate(LocalDate gameDate);

    public boolean isMatchPlayed();

    public void setMatchPlayed(boolean matchPlayed);

    public boolean isMatchToBePlayed();

    public void setMatchToBePlayed(boolean matchToBePlayed);

}