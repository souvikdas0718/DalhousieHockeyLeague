package dhl.simulationStateMachine;

import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class CreateLeagueObjectModel {

    JSONObject jsonLeagueObject = null;

    public CreateLeagueObjectModel(JSONObject jsonLeagueObject){
        this.jsonLeagueObject = jsonLeagueObject;
    }

    public LeagueObjectModel getLeagueObjectModel(){

        String leagueName= (String) jsonLeagueObject.get("leagueName");
        ArrayList<IConference> conferenceObjectList = new ArrayList<>();
        ArrayList<IPlayer> freeAgentObjectList = new ArrayList<>();

        if(jsonLeagueObject.get("conferences") instanceof JSONArray){
            conferenceObjectList = getConcferenceArrayList((JSONArray) jsonLeagueObject.get("conferences"));
        }else{
            System.out.println("Conference Array Not Found");
        }

        if(jsonLeagueObject.get("freeAgents") instanceof JSONArray){
            freeAgentObjectList = getPlayerArrayList((JSONArray) jsonLeagueObject.get("freeAgents"));

        }else{
            System.out.println("No free Agents");
        }

        LeagueObjectModel leagueObjectModel = new LeagueObjectModel(
                leagueName,
                conferenceObjectList,
                freeAgentObjectList
        );
        return leagueObjectModel;
    }

    public ArrayList<IConference> getConcferenceArrayList(JSONArray conferenceJsonArray){
        Iterator<?> conferenceListIterator = (conferenceJsonArray).iterator();
        ArrayList<IConference> conferencesListToReturn = new ArrayList<>();
        while(conferenceListIterator.hasNext()){
            JSONObject conferenceJsonObject = (JSONObject) conferenceListIterator.next();
            Conference conferenceObject = new Conference(
                    (String) conferenceJsonObject.get("conferenceName"),
                    getDivisionObjectArrayList( (JSONArray)conferenceJsonObject.get("divisions"))
            );
            conferencesListToReturn.add(conferenceObject);
            System.out.println("Added Confrenct Object: "+ (String) conferenceJsonObject.get("conferenceName"));
        }

        return conferencesListToReturn;
    }

    public ArrayList getDivisionObjectArrayList(JSONArray divisionJsonArray){
        Iterator<?> divisionListIterator = (divisionJsonArray).iterator();
        ArrayList<IDivision> divisonListToReturn = new ArrayList<>();
        while(divisionListIterator.hasNext()){
            JSONObject divisionJsonObject = (JSONObject) divisionListIterator.next();
            Division divisionObject = new Division(
                    (String) divisionJsonObject.get("divisionName"),
                    getTeamObjectArrayList( (JSONArray)divisionJsonObject.get("teams"))
            );
            divisonListToReturn.add(divisionObject);
            System.out.println("Added Division Object: "+ (String) divisionJsonObject.get("divisionName"));
        }

        return divisonListToReturn;

    }
    public ArrayList getTeamObjectArrayList(JSONArray TeamJsonArray){
        Iterator<?> teamListIterator = (TeamJsonArray).iterator();
        ArrayList<ITeam> TeamListToReturn = new ArrayList<>();
        while(teamListIterator.hasNext()){
            JSONObject teamJsonObject = (JSONObject) teamListIterator.next();
            Team teamObject = new Team(
                    (String) teamJsonObject.get("teamName"),
                    (String) teamJsonObject.get("generalManager"),
                    (String) teamJsonObject.get("headCoach"),
                    getPlayerArrayList( (JSONArray) teamJsonObject.get("teams"))
            );
            TeamListToReturn.add(teamObject);
            System.out.println("Added Division Object: "+ (String) teamJsonObject.get("teamName"));
        }

        return TeamListToReturn;
    }

    public ArrayList getPlayerArrayList(JSONArray PlayerJsonArray){
        Iterator<?> playerListIterator = PlayerJsonArray.iterator();
        ArrayList<IPlayer> playerListToReturn = new ArrayList<>();

        while(playerListIterator.hasNext()){
            JSONObject playerJsonObject = (JSONObject) playerListIterator.next();
            // TODO: 06-10-2020 Update new Player() with
            //  Player playerOb = new Player(playerJsonObject.get("playerName") ,
            //                    playerJsonObject.get("position") ,
            //                    playerJsonObject.get("captain"));

            Player playerOb = new Player();

            playerListToReturn.add(playerOb);
        }
        return playerListToReturn;
    }
}
