package dhl.businessLogic.simulationStateMachine.states.standings.interfaces;


import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

import java.util.List;

public interface IStandingSystem {

    public List<IStandings> getStandingsList();

    public void setStandingsList(List<IStandings> standingsList);

    public void updateWinningStandings(ITeam team);

    public void updateLosingStandings(ITeam team);

    public List<IStandings> divisionRanking(IDivision division, List<IStandings> standingsList);

    public List<IStandings> conferenceRanking(IConference conference, List<IStandings> standingsList);

    public List<IStandings> leagueRanking();

    public void rankGenerator(List<IStandings> rankList);

    public void createStandings(ILeagueObjectModel leagueObjectModel);
}
