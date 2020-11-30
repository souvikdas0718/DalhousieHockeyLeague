package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.IDeserializeLeagueObjectModel;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeagueObjectModel implements ILeagueObjectModel {
    public String leagueName;
    public List<IConference> conferences;
    public List<IPlayer> freeAgents;
    public List<ICoach> coaches;
    public List<IGeneralManager> generalManagers;
    public IGameConfig gameplayConfig;

    public LeagueObjectModel() {
        setDefault();
    }

    private void setDefault(){
        leagueName = "";
        conferences = new ArrayList<>();
        freeAgents = new ArrayList<>();
    }

    public LeagueObjectModel(String leagueName, List<IConference> conferences, List<IPlayer> freeAgents, List<ICoach> coaches, List<IGeneralManager> managers, IGameConfig gameConfig) {
        setDefault();
        setLeagueName(leagueName);
        setConferences(conferences);
        setFreeAgents(freeAgents);
        setCoaches(coaches);
        setManagers(managers);
        setGameConfig(gameConfig);
    }

    public String getLeagueName() {
        return leagueName;
    }

    public List<IConference> getConferences() {
        return conferences;
    }

    public List<IPlayer> getFreeAgents() {
        return freeAgents;
    }

    public List<ICoach> getCoaches() {
        return coaches;
    }

    public IGameConfig getGameConfig() {
        return gameplayConfig;
    }

    public List<IGeneralManager> getGeneralManagers() {
        return generalManagers;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public void setConferences(List<IConference> conferences) {
        this.conferences = conferences;
    }

    public void setFreeAgents(List<IPlayer> freeAgents) {
        this.freeAgents = freeAgents;
    }

    public void setManagers(List managers) {
        this.generalManagers = managers;
    }

    public void setCoaches(List<ICoach> coaches) {
        this.coaches = coaches;
    }

    public void setGameConfig(IGameConfig gameConfig) {
        this.gameplayConfig = gameConfig;
    }

    public boolean checkIfLeagueModelValid(IValidation validation, ILeagueObjectModelValidation leagueObjectModelValidation) throws Exception {
        return leagueObjectModelValidation.checkIfLeagueObjectModelValid(validation, this);
    }

    public ILeagueObjectModel saveLeagueObjectModel(ISerializeLeagueObjectModel serializeLeagueObjectModel, ILeagueObjectModelInput saveLeagueInput) throws IOException {
        ILeagueObjectModelValidation leagueObjectModelValidation = saveLeagueInput.getLeagueObjectModelValidation();
        try{
            leagueObjectModelValidation.checkUserInputForLeague(this, saveLeagueInput);
        }
        catch (Exception e){
            // TODO: 23-11-2020 update!
        }
       
        List<IConference> conferenceArrayList = this.getConferences();
        boolean newTeamAddedToLeague = false;
        for (int i = 0; i < conferenceArrayList.size(); i++) {
            IConference conference = conferenceArrayList.get(i);
            if (conference.getConferenceName() == saveLeagueInput.getConferenceName()) {
                List<IDivision> divisionArrayList = conference.getDivisions();
                for (int j = 0; j < divisionArrayList.size(); j++) {
                    IDivision division = divisionArrayList.get(j);
                    if (division.getDivisionName() == saveLeagueInput.getDivisionName()) {
                        List<ITeam> teamArrayList = division.getTeams();
                        teamArrayList.add(saveLeagueInput.getNewlyCreatedTeam());
                        newTeamAddedToLeague = true;
                        break;
                    }
                }
                if (newTeamAddedToLeague) {
                    break;
                }
            }
        }
        this.conferences = conferenceArrayList;

        serializeLeagueObjectModel.writeSerializedLeagueObjectToJsonFile(this);

        return this;
    }

    public ILeagueObjectModel loadLeagueObjectModel( IDeserializeLeagueObjectModel deserializeLeagueObjectModel, String leagueName, String teamName) throws IOException, ParseException {
        ILeagueObjectModel leagueObjectModel;
        leagueObjectModel =deserializeLeagueObjectModel.deserializeLeagueObjectJson(leagueName);

        return leagueObjectModel;
    }

    public ILeagueObjectModel updateLeagueObjectModel(ISerializeLeagueObjectModel serializeLeagueObjectModel) throws IOException {
        serializeLeagueObjectModel.updateSerializedLeagueObjectToJsonFile(this);
        return this;
    }
}
