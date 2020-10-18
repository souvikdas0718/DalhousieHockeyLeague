package dhl.simulationStateMachine;

import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;
import dhl.simulationStateMachine.Interface.ICreateLeagueObjectModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;

public class CreateLeagueObjectModel implements ICreateLeagueObjectModel {
    JSONObject jsonLeagueObject = null;
    IValidation validationObject;
    LeagueObjectModel leagueObjectModel;

    public CreateLeagueObjectModel(){
        this.jsonLeagueObject = null;
        this.validationObject = new CommonValidation();
        this.leagueObjectModel = null;
    }

    public CreateLeagueObjectModel(JSONObject jsonLeagueObject){
        this.jsonLeagueObject = jsonLeagueObject;
        this.validationObject = new CommonValidation();
        this.leagueObjectModel = null;
    }

    public LeagueObjectModel getLeagueObjectModel() {
        String leagueName = (String) jsonLeagueObject.get("leagueName");
        ArrayList<IConference> conferenceObjectList = new ArrayList<>();
        ArrayList<IFreeAgent> freeAgentObjectList = new ArrayList<>();
        ArrayList<ICoach> coachObjectList = new ArrayList<>();
        ArrayList<IGeneralManager> generalManagerObjectList = new ArrayList<>();

        try {
            if (checkJsonArray(jsonLeagueObject , "conferences")) {
                conferenceObjectList = getConcferenceArrayList((JSONArray) jsonLeagueObject.get("conferences"));
            } else {
                throw new Exception("Conference Array not Found in JSON");
            }

            if (checkJsonArray(jsonLeagueObject , "freeAgents")){
                freeAgentObjectList = getFreeAgentArrayList((JSONArray) jsonLeagueObject.get("freeAgents"));

            } else {
                throw new Exception("Free Agent Array not Found in JSON");
            }

            if (checkJsonArray(jsonLeagueObject , "coaches")){
                coachObjectList = getCoachArrayList((JSONArray) jsonLeagueObject.get("coaches"));

            } else {
                throw new Exception("Coaches Array not Found in JSON");
            }

            if (checkJsonArray(jsonLeagueObject , "generalManagers")){
                generalManagerObjectList = getGeneralManagerArrayList((JSONArray) jsonLeagueObject.get("generalManagers"));

            } else {
                throw new Exception("General manager Array not Found in JSON");
            }
            leagueObjectModel = new LeagueObjectModel(
                    leagueName,
                    conferenceObjectList,
                    freeAgentObjectList,
                    coachObjectList,
                    generalManagerObjectList
            );

            leagueObjectModel.checkIfLeagueModelValid(validationObject);
        }catch (Exception e){
            System.out.println(e);
            System.exit(0);
        }
        return leagueObjectModel;
    }

    public boolean checkJsonArray(JSONObject jsonLeagueObject, String arrayKey){
        Object arrayToCheck = jsonLeagueObject.get(arrayKey);

        if (arrayToCheck instanceof JSONArray && ((JSONArray) arrayToCheck).size() > 0 ){
            return true;
        }
        return false;
    }

    public ArrayList<IConference> getConcferenceArrayList(JSONArray conferenceJsonArray) throws Exception {
        Iterator<?> conferenceListIterator = (conferenceJsonArray).iterator();
        ArrayList<IConference> conferencesListToReturn = new ArrayList<IConference>();

        while(conferenceListIterator.hasNext()){
            JSONObject conferenceJsonObject = (JSONObject) conferenceListIterator.next();

            if(checkJsonArray(conferenceJsonObject , "divisions")){
                if (conferenceJsonObject.get("conferenceName")==null || conferenceJsonObject.get("divisions")== null){
                    throw new Exception("ERROR: Hey! Division cant have Null values....");
                }
                Conference conferenceObject = new Conference(
                        (String) conferenceJsonObject.get("conferenceName"),
                        getDivisionObjectArrayList( (JSONArray)conferenceJsonObject.get("divisions"))
                );
                if(conferenceObject.checkIfConferenceValid(validationObject)) {
                    conferencesListToReturn.add(conferenceObject);
                }
            }else{
                throw new Exception("Division Array not Found for Conference: " + conferenceJsonObject.get("conferenceName"));
            }
        }
        return conferencesListToReturn;
    }

    public ArrayList<IDivision> getDivisionObjectArrayList(JSONArray divisionJsonArray) throws Exception{
        Iterator<?> divisionListIterator = (divisionJsonArray).iterator();
        ArrayList<IDivision> divisonListToReturn = new ArrayList<IDivision>();
        while(divisionListIterator.hasNext()){
            JSONObject divisionJsonObject = (JSONObject) divisionListIterator.next();
            if (divisionJsonObject.get("divisionName")==null || divisionJsonObject.get("teams")== null){
                throw new Exception("ERROR: Hey! Division cant have Null values....");
            }
            Division divisionObject = new Division(
                    (String) divisionJsonObject.get("divisionName"),
                    getTeamObjectArrayList( (JSONArray)divisionJsonObject.get("teams"))
            );

            if(divisionObject.checkIfDivisionValid(validationObject)){
                divisonListToReturn.add(divisionObject);
            }
        }

        return divisonListToReturn;

    }

    public ArrayList<ITeam> getTeamObjectArrayList(JSONArray TeamJsonArray) throws Exception {
        Iterator<?> teamListIterator = (TeamJsonArray).iterator();
        ArrayList<ITeam> TeamListToReturn = new ArrayList<ITeam>();
        while(teamListIterator.hasNext()){
            JSONObject teamJsonObject = (JSONObject) teamListIterator.next();
            if (teamJsonObject.get("teamName")==null || teamJsonObject.get("generalManager")== null ||
                    teamJsonObject.get("headCoach")==null || teamJsonObject.get("players")==null){
                throw new Exception("ERROR: Hey! Team cant have Null values....");
            }
            ITeam teamObject = new Team(
                    (String) teamJsonObject.get("teamName"),
                    (String) teamJsonObject.get("generalManager"),
                    (String) teamJsonObject.get("headCoach"),
                    getPlayerArrayList( (JSONArray) teamJsonObject.get("players"))
            );
            if (teamObject.checkIfTeamValid(validationObject)){
                TeamListToReturn.add(teamObject);
            }
        }
        return TeamListToReturn;
    }

    public ArrayList<IPlayer> getPlayerArrayList(JSONArray playerJsonArray) throws Exception {
        Iterator<?> playerListIterator = playerJsonArray.iterator();
        ArrayList<IPlayer> playerListToReturn = new ArrayList<IPlayer>();

        while(playerListIterator.hasNext()){
            JSONObject playerJsonObject = (JSONObject) playerListIterator.next();

            if (playerJsonObject.get("playerName")==null || playerJsonObject.get("position")== null || playerJsonObject.get("captain")==null){
                throw new Exception("ERROR: Hey! Player cant have Null values....");
            }
            IPlayer playerOb = new Player((String) playerJsonObject.get("playerName") ,
                    (String) playerJsonObject.get("position") ,
                    (Boolean) playerJsonObject.get("captain"));

            if(playerOb.checkPlayerValid()){
                playerListToReturn.add(playerOb);
            }
        }
        return playerListToReturn;
    }

    public ArrayList<IFreeAgent> getFreeAgentArrayList(JSONArray freeAgentJsonArray) throws Exception {
        Iterator<?> playerListIterator = freeAgentJsonArray.iterator();
        ArrayList<IFreeAgent> playerListToReturn = new ArrayList<IFreeAgent>();

        while(playerListIterator.hasNext()){
            JSONObject freeAgentJsonObject = (JSONObject) playerListIterator.next();

            if (freeAgentJsonObject.get("playerName")==null || freeAgentJsonObject.get("position")== null){
                throw new Exception("ERROR: Hey! Free Agent cant have Null values....");
            }
            IFreeAgent freeAgentOb = new FreeAgent((String) freeAgentJsonObject.get("playerName") ,
                    (String) freeAgentJsonObject.get("position"));


                playerListToReturn.add(freeAgentOb);

        }
        return playerListToReturn;
    }

    public ArrayList<ICoach> getCoachArrayList(JSONArray coachJsonArray) throws Exception {
        Iterator<?> coachListIterator = coachJsonArray.iterator();
        ArrayList<ICoach> coachListToReturn = new ArrayList<ICoach>();

        while(coachListIterator.hasNext()){
            JSONObject coachJsonObject = (JSONObject) coachListIterator.next();

            if (coachJsonObject.get("name")==null || coachJsonObject.get("skating")== null ||
                    coachJsonObject.get("shooting")==null || coachJsonObject.get("checking")==null ||
                    coachJsonObject.get("saving")==null){
                throw new Exception("ERROR: Hey! Coach cant have Null values....");
            }
            ICoach coachOb = new Coach((String) coachJsonObject.get("name") ,
                    (double) coachJsonObject.get("skating"),
                    (double) coachJsonObject.get("shooting"),
                    (double) coachJsonObject.get("checking"),
                    (double) coachJsonObject.get("saving")
                    );

            coachListToReturn.add(coachOb);

        }
        return coachListToReturn;
    }

    public ArrayList<IGeneralManager> getGeneralManagerArrayList(JSONArray generalManagerJsonArray) throws Exception {
        Iterator<?> generalManagerListIterator = generalManagerJsonArray.iterator();
        ArrayList<IGeneralManager> generalManagerListToReturn = new ArrayList<IGeneralManager>();

        while(generalManagerListIterator.hasNext()){
           // JSONObject generalManagerJsonObject = (JSONObject) generalManagerListIterator.next();
            String genManager = generalManagerListIterator.next().toString();
            IGeneralManager generalManagerOb = new GeneralManager(genManager);

            generalManagerListToReturn.add(generalManagerOb);

        }
        return generalManagerListToReturn;
    }
}
