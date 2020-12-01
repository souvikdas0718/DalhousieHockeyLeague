package dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces;


import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandings;

import java.time.LocalDate;
import java.util.List;

public interface IScheduler {
    public List<ISeasonSchedule> getFullSeasonSchedule();

    public void setFullSeasonSchedule(List<ISeasonSchedule> fullSeasonSchedule);

    public List<ISeasonSchedule> getPlayOffScheduleRound1();

    public LocalDate getSeasonStartDate();

    public void setSeasonStartDate(LocalDate seasonStartDate);

    public LocalDate getSeasonEndDate();

    public void setSeasonEndDate(LocalDate seasonEndDate);

    public LocalDate getPlayOffStartDate();

    public void setPlayOffStartDate(LocalDate playOffStartDate);

    public LocalDate getFinalDay();

    public void setFinalDay(LocalDate finalDay);

    public LocalDate getFinalMatchDate();

    public void setFinalMatchDate(LocalDate finalMatchDate);

    public List<ITeam> getTeamList();

    public List<IConference> getConferences();

    public List<IDivision> getDivisions();

    public void gameWinner(ITeam team);

    public void generateTeamList(ILeagueObjectModel inMemoryLeague);

    public void generateTeamSchedule(ILeagueObjectModel inMemoryLeague);

    public void gameScheduleDates(LocalDate seasonStartDate, LocalDate seasonEndDate);

    public LocalDate getCurrentDate();

    public void setCurrentDate(LocalDate currentDate);

    public List<IStandings> getGameStandings();

    public void setGameStandings(List<IStandings> gameStandings);

    public void playOffs(List<IStandings> regularGamesStandings, ILeagueObjectModel leagueObjectModel);

    public boolean stanleyCupWinner(LocalDate date);

}
