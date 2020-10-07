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

    public CreateLeagueObjectModel(JSONObject jsonLeagueObject){
        this.jsonLeagueObject = jsonLeagueObject;
    }

    public LeagueObjectModel getLeagueObjectModel() throws Exception {

        String leagueName= (String) jsonLeagueObject.get("leagueName");
        ArrayList<IConference> conferenceObjectList = new ArrayList<>();
        ArrayList<IPlayer> freeAgentObjectList = new ArrayList<>();
        validationObject = new CommonValidation();
        LeagueObjectModel leagueObjectModel = null;

        try {
            if (jsonLeagueObject.get("conferences") instanceof JSONArray) {
                conferenceObjectList = getConcferenceArrayList((JSONArray) jsonLeagueObject.get("conferences"));
            } else {
                System.out.println("Conference Array Not Found");
            }
            if (jsonLeagueObject.get("freeAgents") instanceof JSONArray) {
                freeAgentObjectList = getPlayerArrayList((JSONArray) jsonLeagueObject.get("freeAgents"));

            } else {
                System.out.println("No free Agents");
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

    public ArrayList<IConference> getConcferenceArrayList(JSONArray conferenceJsonArray) throws Exception {
        Iterator<?> conferenceListIterator = (conferenceJsonArray).iterator();
        ArrayList<IConference> conferencesListToReturn = new ArrayList<IConference>();
        while(conferenceListIterator.hasNext()){
            JSONObject conferenceJsonObject = (JSONObject) conferenceListIterator.next();
            Conference conferenceObject = new Conference(
                    (String) conferenceJsonObject.get("conferenceName"),
                    getDivisionObjectArrayList( (JSONArray)conferenceJsonObject.get("divisions"))
            );
            if(conferenceObject.checkIfConferenceValid(validationObject)) {
                conferencesListToReturn.add(conferenceObject);
                System.out.println("Added Confrenct Object: " + (String) conferenceJsonObject.get("conferenceName"));
            }
        }
        System.out.println(conferencesListToReturn.size());
        return conferencesListToReturn;
    }

    public ArrayList<IDivision> getDivisionObjectArrayList(JSONArray divisionJsonArray) throws Exception{
        Iterator<?> divisionListIterator = (divisionJsonArray).iterator();
        ArrayList<IDivision> divisonListToReturn = new ArrayList<IDivision>();
        while(divisionListIterator.hasNext()){
            JSONObject divisionJsonObject = (JSONObject) divisionListIterator.next();
            Division divisionObject = new Division(
                    (String) divisionJsonObject.get("divisionName"),
                    getTeamObjectArrayList( (JSONArray)divisionJsonObject.get("teams"))
            );

            if(divisionObject.checkIfDivisionValid(validationObject)){
                divisonListToReturn.add(divisionObject);
                System.out.println("Added Division Object: "+ (String) divisionJsonObject.get("divisionName"));
            }
        }

        return divisonListToReturn;

    }
    public ArrayList<ITeam> getTeamObjectArrayList(JSONArray TeamJsonArray) throws Exception {
        Iterator<?> teamListIterator = (TeamJsonArray).iterator();
        ArrayList<ITeam> TeamListToReturn = new ArrayList<ITeam>();
        while(teamListIterator.hasNext()){
            JSONObject teamJsonObject = (JSONObject) teamListIterator.next();
            ITeam teamObject = new Team(
                    (String) teamJsonObject.get("teamName"),
                    (String) teamJsonObject.get("generalManager"),
                    (String) teamJsonObject.get("headCoach"),
                    getPlayerArrayList( (JSONArray) teamJsonObject.get("players"))
            );
            if (teamObject.checkIfTeamValid(validationObject)){
                TeamListToReturn.add(teamObject);
                System.out.println("Added Team Object: "+ (String) teamJsonObject.get("teamName"));
            }
        }

        return TeamListToReturn;
    }

    public ArrayList<IPlayer> getPlayerArrayList(JSONArray playerJsonArray) throws Exception {
        //System.out.println(playerJsonArray);
        Iterator<?> playerListIterator = playerJsonArray.iterator();
        ArrayList<IPlayer> playerListToReturn = new ArrayList<IPlayer>();

        while(playerListIterator.hasNext()){
            JSONObject playerJsonObject = (JSONObject) playerListIterator.next();

            IPlayer playerOb = new Player((String) playerJsonObject.get("playerName") ,
                    (String) playerJsonObject.get("position") ,
                    (Boolean) playerJsonObject.get("captain"));

            if(playerOb.checkPlayerValid()){
                playerListToReturn.add(playerOb);
                System.out.println("Added Player Object: "+ (String) playerOb.getPlayerName());
            }
        }
        return playerListToReturn;
    }
}
