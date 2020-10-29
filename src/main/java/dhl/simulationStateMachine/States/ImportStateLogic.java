package dhl.simulationStateMachine.States;

import dhl.importJson.CreateLeagueObjectModel;
import dhl.importJson.GameConfig;
import dhl.importJson.ImportJsonFile;
import dhl.importJson.Interface.IGameConfig;
import dhl.leagueModel.LeagueObjectModel;
import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.simulationStateMachine.States.Interface.IImportStateLogic;
import org.json.simple.JSONObject;

public class ImportStateLogic implements IImportStateLogic {
    public ILeagueObjectModel importAndGetLeagueObject(String validFilePath, IGameConfig gameConfig, ILeagueObjectModel newInMemoryLeague) throws Exception {
        JSONObject leagueJsonObject = new ImportJsonFile(validFilePath).getJsonObject();
        gameConfig = new GameConfig(leagueJsonObject);
        CreateLeagueObjectModel createLeagueObjectModel = new CreateLeagueObjectModel(leagueJsonObject);
        ILeagueObjectModel objLeagueObjectModel = new LeagueObjectModel();
        objLeagueObjectModel = createLeagueObjectModel.getLeagueObjectModel();
        return objLeagueObjectModel;
    }
    public ITeam findTeam(ILeagueObjectModel inMemoryLeague, String teamName){

        ITeam teamObject = null;

        for(IConference conference: inMemoryLeague.getConferences() ){
            for(IDivision division: conference.getDivisions()){
                for (ITeam team: division.getTeams()){
                    if (team.getTeamName().equals(teamName)){
                        teamObject = team;
                    }
                }
            }
        }

        return teamObject;
    }
}
