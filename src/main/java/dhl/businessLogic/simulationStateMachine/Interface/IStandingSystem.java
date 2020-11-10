package dhl.businessLogic.simulationStateMachine.Interface;


import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

import java.util.List;

public interface IStandingSystem {

    public List<IStandings> getStandingsList();

    public void setStandingsList(List<IStandings> standingsList);

    public void updateWinningStandings(ITeam team);

    public void updateLosingStandings(ITeam team);

    public List<IStandings> divisionRanking(IDivision division);

    public List<IStandings> conferenceRanking(IConference conference);

    public List<IStandings> leagueRanking();

    public void rankGenerator(List<IStandings> rankList);
}
