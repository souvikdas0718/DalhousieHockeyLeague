package dhl.businessLogic.simulationStateMachine.Interface;



import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

import java.time.LocalDate;
import java.util.List;

public interface IScheduler {
    public List<ISchedule> getFullSeasonSchedule();

    public void setFullSeasonSchedule(List<ISchedule> fullSeasonSchedule);

    public List<ISchedule> getPlayOffScheduleRound1();

    public void setPlayOffScheduleRound1(List<ISchedule> playOffSchedule);

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

    public void gameWinner(ITeam team);

    public void generateTeamList(ILeagueObjectModel inMemoryLeague);

    public void generateTeamSchedule(ILeagueObjectModel inMemoryLeague);

    public void gameScheduleDates( LocalDate seasonStartDate, LocalDate seasonEndDate );

    public List<ISchedule> getFinals();

    public void setFinals(List<ISchedule> finals);

    public LocalDate getCurrentDate();

    public void setCurrentDate(LocalDate currentDate);

    public List<IStandings> getGameStandings();

    public void setGameStandings(List<IStandings> gameStandings);

    public void playOffs(List<IStandings> regularGamesStandings, ILeagueObjectModel leagueObjectModel);

    public boolean stanleyCupWinner(LocalDate date);

    public List<ISchedule> getPlayOffScheduleRound2();

    public void setPlayOffScheduleRound2(List<ISchedule> playOffScheduleRound2);

}
