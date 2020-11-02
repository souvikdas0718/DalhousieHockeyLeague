package dhl.simulationStateMachine.States;

import dhl.database.interfaceDB.ILeagueObjectModelDB;
import dhl.leagueModel.Team;
import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.simulationStateMachine.GameContext;
import dhl.simulationStateMachine.States.Interface.ILoadTeamStateLogic;

public class LoadTeamStateLogic implements ILoadTeamStateLogic {
    private String leagueName;
    private String team;
    private ILeagueObjectModel leagueObjectModel;

    public LoadTeamStateLogic() {

    }

    public LoadTeamStateLogic(String leagueName, String team) {
        this.leagueName = leagueName;
        this.team = team;
    }

    public Boolean findTeamOfLeagueInDatabase(ILeagueObjectModel newInMemoryLeague,
                                              GameContext ourGame, ILeagueObjectModelDB databaseRefrenceOb) throws Exception {

        newInMemoryLeague = newInMemoryLeague.loadLeagueObjectModel(databaseRefrenceOb, leagueName, team);
        ITeam objteam = new Team();
        objteam = findTeam(newInMemoryLeague, team);

        if (objteam == null) {
            return false;
        } else {
            ourGame.setSelectedTeam(objteam);
            return true;
        }
    }

    public ITeam findTeam(ILeagueObjectModel inMemoryLeague, String teamName) {
        ITeam teamObject = null;

        for (IConference conference : inMemoryLeague.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    if (team.getTeamName().equals(teamName)) {
                        teamObject = team;
                        System.out.println("Team Found");
                    }
                }
            }
        }

        return teamObject;
    }
}
