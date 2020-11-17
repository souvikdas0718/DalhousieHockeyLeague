package dhl.InputOutput.importJson;

import dhl.InputOutput.importJson.Interface.ICreateLeagueObjectModel;
import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.businessLogic.leagueModel.*;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CreateLeagueObjectModel implements ICreateLeagueObjectModel {
    JSONObject jsonLeagueObject;
    IValidation validationObject;
    ILeagueObjectModel leagueObjectModel;
    ILeagueObjectModelValidation leagueObjectModelValidation;
    private JSONArray conferenceJsonArray, divisionJsonArray, teamJsonArray, playerJsonArray, freeAgentJsonArray, coachesJsonArrayList, generalManagerJsonArrayList;
    IGameConfig gameConfig;

    public CreateLeagueObjectModel() {
        this.jsonLeagueObject = null;
        this.validationObject = new CommonValidation();
        this.leagueObjectModelValidation = new LeagueObjectModelValidation();
        this.leagueObjectModel = null;
    }

    public CreateLeagueObjectModel(JSONObject jsonLeagueObject, IGameConfig gameConfig) {
        this.jsonLeagueObject = jsonLeagueObject;
        this.validationObject = new CommonValidation();
        this.leagueObjectModelValidation = new LeagueObjectModelValidation();
        this.leagueObjectModel = null;
        this.gameConfig = gameConfig;
    }

    public ILeagueObjectModel getLeagueObjectModel() {
        String leagueName = (String) jsonLeagueObject.get("leagueName");
        List<IConference> conferenceObjectList;
        List<IPlayer> freeAgentObjectList;

        try {
            if (checkJsonArray(jsonLeagueObject, "conferences")) {
                conferenceJsonArray = (JSONArray) jsonLeagueObject.get("conferences");
                conferenceObjectList = getConferenceArrayList();
            } else {
                throw new Exception("Conference Array not Found in JSON");
            }

            if (checkJsonArray(jsonLeagueObject, "freeAgents")) {
                freeAgentJsonArray = (JSONArray) jsonLeagueObject.get("freeAgents");
                freeAgentObjectList = getFreeAgentArrayList();

            } else {
                throw new Exception("Free Agent Array not Found in JSON");
            }
            if (checkJsonArray(jsonLeagueObject, "coaches")) {
                coachesJsonArrayList = (JSONArray) jsonLeagueObject.get("coaches");

            } else {
                throw new Exception("Coaches Array not Found in JSON");
            }
            if (checkJsonArray(jsonLeagueObject, "generalManagers")) {
                generalManagerJsonArrayList = (JSONArray) jsonLeagueObject.get("generalManagers");

            } else {
                throw new Exception("General manager Array not Found in JSON");
            }
            leagueObjectModel = new LeagueObjectModel(
                    leagueName,
                    conferenceObjectList,
                    freeAgentObjectList,
                    getCoachesArrayList(),
                    getGeneralManagerArrayList(),
                    gameConfig
            );
            leagueObjectModel.checkIfLeagueModelValid(validationObject, leagueObjectModelValidation);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        return leagueObjectModel;
    }

    public boolean checkJsonArray(JSONObject jsonLeagueObject, String arrayKey) {
        Object arrayToCheck = jsonLeagueObject.get(arrayKey);

        if (arrayToCheck instanceof JSONArray && ((JSONArray) arrayToCheck).size() > 0) {
            return true;
        }
        return false;
    }

    public List<IConference> getConferenceArrayList() throws Exception {
        Iterator<?> conferenceListIterator = (conferenceJsonArray).iterator();
        List<IConference> conferencesListToReturn = new ArrayList<>();

        while (conferenceListIterator.hasNext()) {
            JSONObject conferenceJsonObject = (JSONObject) conferenceListIterator.next();
            if (checkJsonArray(conferenceJsonObject, "divisions")) {
                if (conferenceJsonObject.get("conferenceName") == null || conferenceJsonObject.get("divisions") == null) {
                    throw new Exception("ERROR: Hey! Conference cant have Null values....");
                }
                divisionJsonArray = (JSONArray) conferenceJsonObject.get("divisions");
                IConference conferenceObject = new Conference((String) conferenceJsonObject.get("conferenceName"), getDivisionObjectArrayList());
                if (conferenceObject.checkIfConferenceValid(validationObject)) {
                    conferencesListToReturn.add(conferenceObject);
                }
            } else {
                throw new Exception("Division Array not Found for Conference: " + conferenceJsonObject.get("conferenceName"));
            }
        }
        return conferencesListToReturn;
    }

    public List<IDivision> getDivisionObjectArrayList() throws Exception {
        Iterator<?> divisionListIterator = (divisionJsonArray).iterator();
        List<IDivision> divisonListToReturn = new ArrayList<>();
        while (divisionListIterator.hasNext()) {
            JSONObject divisionJsonObject = (JSONObject) divisionListIterator.next();
            if (divisionJsonObject.get("divisionName") == null || divisionJsonObject.get("teams") == null) {
                throw new Exception("ERROR: Hey! Division cant have Null values....");
            }
            teamJsonArray = (JSONArray) divisionJsonObject.get("teams");
            IDivision divisionObject = new Division(
                    (String) divisionJsonObject.get("divisionName"),
                    getTeamObjectArrayList()
            );

            if (divisionObject.checkIfDivisionValid(validationObject)) {
                divisonListToReturn.add(divisionObject);
            }
        }

        return divisonListToReturn;

    }

    public List<ITeam> getTeamObjectArrayList() throws Exception {
        Iterator<?> teamListIterator = (teamJsonArray).iterator();
        List<ITeam> TeamListToReturn = new ArrayList<>();
        while (teamListIterator.hasNext()) {
            JSONObject teamJsonObject = (JSONObject) teamListIterator.next();
            if (teamJsonObject.get("teamName") == null || teamJsonObject.get("generalManager") == null ||
                    teamJsonObject.get("headCoach") == null || teamJsonObject.get("players") == null) {
                throw new Exception("ERROR: Hey! Team cant have Null values....");
            }
            playerJsonArray = (JSONArray) teamJsonObject.get("players");
            JSONObject headCoachJsonObject = (JSONObject) teamJsonObject.get("headCoach");
            ITeam teamObject = new Team(
                    (String) teamJsonObject.get("teamName"),
                    (String) teamJsonObject.get("generalManager"),
                    new Coach((String) headCoachJsonObject.get("name"), (double) headCoachJsonObject.get("skating"), (double) headCoachJsonObject.get("shooting"), (double) headCoachJsonObject.get("checking"), (double) headCoachJsonObject.get("saving")),
                    getPlayerArrayList()
            );
            if (teamObject.checkIfTeamValid(validationObject)) {
                TeamListToReturn.add(teamObject);
            }
        }
        return TeamListToReturn;
    }

    public List<IPlayer> getPlayerArrayList() throws Exception {
        Iterator<?> playerListIterator = playerJsonArray.iterator();
        List<IPlayer> playerListToReturn = new ArrayList<>();

        while (playerListIterator.hasNext()) {
            JSONObject playerJsonObject = (JSONObject) playerListIterator.next();

            if (playerJsonObject.get("playerName") == null || playerJsonObject.get("position") == null || playerJsonObject.get("captain") == null || playerJsonObject.get("age") == null || playerJsonObject.get("skating") == null || playerJsonObject.get("shooting") == null || playerJsonObject.get("checking") == null || playerJsonObject.get("saving") == null) {
                throw new Exception("ERROR: Hey! Player cant have Null values....");
            }
            IPlayerStatistics playerStatistics = new PlayerStatistics((int) (long) playerJsonObject.get("age"), (int) (long) playerJsonObject.get("skating"), (int) (long) playerJsonObject.get("shooting"), (int) (long) playerJsonObject.get("checking"), (int) (long) playerJsonObject.get("saving"));
            IPlayer playerOb = new Player((String) playerJsonObject.get("playerName"),
                    (String) playerJsonObject.get("position"),
                    (Boolean) playerJsonObject.get("captain"),
                    playerStatistics);

            if (playerOb.checkPlayerValid()) {
                playerListToReturn.add(playerOb);
            }
        }
        return playerListToReturn;
    }

    public List<IPlayer> getFreeAgentArrayList() throws Exception {
        Iterator<?> playerListIterator = freeAgentJsonArray.iterator();
        List<IPlayer> playerListToReturn = new ArrayList<>();

        while (playerListIterator.hasNext()) {
            JSONObject freeAgentJsonObject = (JSONObject) playerListIterator.next();

            if (freeAgentJsonObject.get("playerName") == null || freeAgentJsonObject.get("position") == null || freeAgentJsonObject.get("skating") == null || freeAgentJsonObject.get("shooting") == null || freeAgentJsonObject.get("checking") == null || freeAgentJsonObject.get("saving") == null) {
                throw new Exception("ERROR: Hey! Free Agents cant have Null values....");
            }
            IPlayerStatistics freeAgentStatistics = new PlayerStatistics((int) (long) freeAgentJsonObject.get("age"), (int) (long) freeAgentJsonObject.get("skating"), (int) (long) freeAgentJsonObject.get("shooting"), (int) (long) freeAgentJsonObject.get("checking"), (int) (long) freeAgentJsonObject.get("saving"));
            IPlayer freeAgentOb = new FreeAgent((String) freeAgentJsonObject.get("playerName"),
                    (String) freeAgentJsonObject.get("position"), freeAgentStatistics);

            playerListToReturn.add(freeAgentOb);

        }
        return playerListToReturn;
    }

    public List<ICoach> getCoachesArrayList() throws Exception {
        Iterator<?> coachListIterator = coachesJsonArrayList.iterator();
        List<ICoach> coachListToReturn = new ArrayList<>();

        while (coachListIterator.hasNext()) {
            JSONObject coachJsonObject = (JSONObject) coachListIterator.next();

            if (coachJsonObject.get("name") == null || coachJsonObject.get("skating") == null || coachJsonObject.get("shooting") == null || coachJsonObject.get("checking") == null || coachJsonObject.get("saving") == null) {
                throw new Exception("ERROR: Hey! Player cant have Null values....");
            }
            ICoach coachOb = new Coach((String) coachJsonObject.get("name"),
                    (double) coachJsonObject.get("skating"), (double) coachJsonObject.get("shooting"), (double) coachJsonObject.get("checking"), (double) coachJsonObject.get("saving"));
            if (coachOb.checkIfCoachValid(validationObject)) {
                coachListToReturn.add(coachOb);
            }
        }
        return coachListToReturn;
    }

    public List<IGeneralManager> getGeneralManagerArrayList() throws Exception {
        Iterator<?> generalManagerListIterator = generalManagerJsonArrayList.iterator();
        List<IGeneralManager> generalManagerListToReturn = new ArrayList<>();

        while (generalManagerListIterator.hasNext()) {
            String genManager = generalManagerListIterator.next().toString();
            IGeneralManager generalManagerOb = new GeneralManager(genManager);

            generalManagerListToReturn.add(generalManagerOb);

        }
        return generalManagerListToReturn;
    }

}
