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
        ArrayList<IPlayer> freeAgentObjectList = new ArrayList<>();

        try {
            if (checkJsonArray(jsonLeagueObject , "conferences")) {
                conferenceObjectList = getConcferenceArrayList((JSONArray) jsonLeagueObject.get("conferences"));
            } else {
                throw new Exception("Conference Array not Found in JSON");
            }

            if (checkJsonArray(jsonLeagueObject , "freeAgents")){
                freeAgentObjectList = getPlayerArrayList((JSONArray) jsonLeagueObject.get("freeAgents"));

            } else {
                throw new Exception("Free Agent Array not Found in JSON");
            }
            leagueObjectModel = new LeagueObjectModel(
                    leagueName,
                    conferenceObjectList,
                    freeAgentObjectList
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
}
