package dhl.businessLogic.simulationStateMachine.states;

import dhl.businessLogic.leagueModel.Team;
import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.states.interfaces.ILoadTeamStateLogic;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.IDeserializeLeagueObjectModel;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;

public class LoadTeamStateLogic implements ILoadTeamStateLogic {
    private String leagueName;
    private String team;
    private ILeagueObjectModel leagueObjectModel;

    IUserInputOutput userInputOutput = IUserInputOutput.getInstance();

    public LoadTeamStateLogic() {

    }

    public LoadTeamStateLogic(String leagueName, String team) {
        this.leagueName = leagueName;
        this.team = team;
    }

    public Boolean findTeamOfLeagueInDatabase(ILeagueObjectModel newInMemoryLeague,
                                              GameContext ourGame, IDeserializeLeagueObjectModel deserializeLeagueObjectModel) throws Exception {

        newInMemoryLeague = newInMemoryLeague.loadLeagueObjectModel(deserializeLeagueObjectModel, leagueName, team);
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
                        userInputOutput.printMessage("Team Found");
                    }
                }
            }
        }
        return teamObject;
    }
}
