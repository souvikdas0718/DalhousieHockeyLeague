package dhl.businessLogic.simulationStateMachine.States;

import dhl.InputOutput.importJson.CreateLeagueObjectModel;
import dhl.InputOutput.importJson.GameConfig;
import dhl.InputOutput.importJson.ImportJsonFile;
import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.States.Interface.IImportStateLogic;
import org.json.simple.JSONObject;

public class ImportStateLogic implements IImportStateLogic {

    public ILeagueObjectModel importAndGetLeagueObject(String validFilePath, IGameConfig gameConfig, ILeagueObjectModel newInMemoryLeague) throws Exception {
        JSONObject leagueJsonObject = new ImportJsonFile(validFilePath).getJsonObject();
        gameConfig = new GameConfig(leagueJsonObject);

        CreateLeagueObjectModel createLeagueObjectModel = new CreateLeagueObjectModel(leagueJsonObject, gameConfig);
        ILeagueObjectModel objLeagueObjectModel = new LeagueObjectModel();

        objLeagueObjectModel = createLeagueObjectModel.getLeagueObjectModel();

        return objLeagueObjectModel;
    }

    public ITeam findTeam(ILeagueObjectModel inMemoryLeague, String teamName) {
        ITeam teamObject = null;

        for (IConference conference : inMemoryLeague.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    if (team.getTeamName().equals(teamName)) {
                        teamObject = team;
                    }
                }
            }
        }
        return teamObject;
    }
}
