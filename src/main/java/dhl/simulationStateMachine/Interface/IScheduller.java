package dhl.simulationStateMachine.Interface;

import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.ITeam;

import java.time.LocalDate;
import java.util.List;

public interface IScheduller {
    public List<ISchedule> getFullSeasonSchedule();

    public void setFullSeasonSchedule(List<ISchedule> fullSeasonSchedule);

    public List<ISchedule> getPlayOffSchedule();

    public void setPlayOffSchedule(List<ISchedule> playOffSchedule);

    public LocalDate getSeasonStartDate();

    public void setSeasonStartDate(LocalDate seasonStartDate);

    public LocalDate getSeasonEndDate();

    public void setSeasonEndDate(LocalDate seasonEndDate);

    public LocalDate getPlayOffStartDate();

    public void setPlayOffStartDate(LocalDate playOffStartDate);

    public LocalDate getFinalDay();

    public void setFinalDay(LocalDate finalDay);

    public List<ITeam> getTeamList();

    public void setTeamList(List<ITeam> teamList);

    public List<IConference> getConferences();

    public void setConferences(List<IConference> conferences);

    public List<IDivision> getDivisions();

    public void setDivisions(List<IDivision> divisions);
}
