package dhl.importJson;

import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;
import dhl.importJson.Interface.ICreateLeagueObjectModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;

public class CreateLeagueObjectModel implements ICreateLeagueObjectModel {
    JSONObject jsonLeagueObject = null;
    IValidation validationObject;
    LeagueObjectModel leagueObjectModel;
    private JSONArray conferenceJsonArray,divisionJsonArray,teamJsonArray,playerJsonArray,freeAgentJsonArray;

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

    public ILeagueObjectModel getLeagueObjectModel() {
        String leagueName = (String) jsonLeagueObject.get("leagueName");
        ArrayList<IConference> conferenceObjectList = new ArrayList<>();
        ArrayList<IFreeAgent> freeAgentObjectList = new ArrayList<>();

        try {
            if (checkJsonArray(jsonLeagueObject , "conferences")) {
                conferenceJsonArray = (JSONArray) jsonLeagueObject.get("conferences");
                conferenceObjectList = getConcferenceArrayList();
            } else {
                throw new Exception("Conference Array not Found in JSON");
            }

            if (checkJsonArray(jsonLeagueObject , "freeAgents")){
                freeAgentJsonArray = (JSONArray) jsonLeagueObject.get("freeAgents");
                freeAgentObjectList = getFreeAgentArrayList();

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

    public ArrayList<IConference> getConcferenceArrayList() throws Exception {
        Iterator<?> conferenceListIterator = (conferenceJsonArray).iterator();
        ArrayList<IConference> conferencesListToReturn = new ArrayList<IConference>();

        while(conferenceListIterator.hasNext()){
            JSONObject conferenceJsonObject = (JSONObject) conferenceListIterator.next();
            if(checkJsonArray(conferenceJsonObject , "divisions")){
                if (conferenceJsonObject.get("conferenceName")==null || conferenceJsonObject.get("divisions")== null){
                    throw new Exception("ERROR: Hey! Division cant have Null values....");
                }
                divisionJsonArray = (JSONArray)conferenceJsonObject.get("divisions");
                Conference conferenceObject = new Conference((String) conferenceJsonObject.get("conferenceName"),getDivisionObjectArrayList());
                if(conferenceObject.checkIfConferenceValid(validationObject)) {
                    conferencesListToReturn.add(conferenceObject);
                }
            }else{
                throw new Exception("Division Array not Found for Conference: " + conferenceJsonObject.get("conferenceName"));
            }
        }
        return conferencesListToReturn;
    }

    public ArrayList<IDivision> getDivisionObjectArrayList() throws Exception{
        Iterator<?> divisionListIterator = (divisionJsonArray).iterator();
        ArrayList<IDivision> divisonListToReturn = new ArrayList<IDivision>();
        while(divisionListIterator.hasNext()){
            JSONObject divisionJsonObject = (JSONObject) divisionListIterator.next();
            if (divisionJsonObject.get("divisionName")==null || divisionJsonObject.get("teams")== null){
                throw new Exception("ERROR: Hey! Division cant have Null values....");
            }
            teamJsonArray = (JSONArray)divisionJsonObject.get("teams");
            Division divisionObject = new Division(
                    (String) divisionJsonObject.get("divisionName"),
                    getTeamObjectArrayList()
            );

            if(divisionObject.checkIfDivisionValid(validationObject)){
                divisonListToReturn.add(divisionObject);
            }
        }

        return divisonListToReturn;

    }

    public ArrayList<ITeam> getTeamObjectArrayList() throws Exception {
        Iterator<?> teamListIterator = (teamJsonArray).iterator();
        ArrayList<ITeam> TeamListToReturn = new ArrayList<ITeam>();
        while(teamListIterator.hasNext()){
            JSONObject teamJsonObject = (JSONObject) teamListIterator.next();
            if (teamJsonObject.get("teamName")==null || teamJsonObject.get("generalManager")== null ||
                    teamJsonObject.get("headCoach")==null || teamJsonObject.get("players")==null){
                throw new Exception("ERROR: Hey! Team cant have Null values....");
            }
            playerJsonArray = (JSONArray) teamJsonObject.get("players");
            JSONObject headCoachJsonObject=(JSONObject) teamJsonObject.get("headCoach");
            ITeam teamObject = new Team(
                    (String) teamJsonObject.get("teamName"),
                    (String) teamJsonObject.get("generalManager"),
                    new Coach( (String) headCoachJsonObject.get("name"),(double)headCoachJsonObject.get("skating"),(double)headCoachJsonObject.get("shooting"),(double)headCoachJsonObject.get("checking"),(double)headCoachJsonObject.get("saving")),
                    getPlayerArrayList()
            );
            if (teamObject.checkIfTeamValid(validationObject)){
                TeamListToReturn.add(teamObject);
            }
        }
        return TeamListToReturn;
    }

    public ArrayList<IPlayer> getPlayerArrayList() throws Exception {
        Iterator<?> playerListIterator = playerJsonArray.iterator();
        ArrayList<IPlayer> playerListToReturn = new ArrayList<IPlayer>();

        while(playerListIterator.hasNext()){
            JSONObject playerJsonObject = (JSONObject) playerListIterator.next();

            if (playerJsonObject.get("playerName")==null || playerJsonObject.get("position")== null || playerJsonObject.get("captain")==null){
                throw new Exception("ERROR: Hey! Player cant have Null values....");
            }
            IPlayerStatistics playerStatistics = new PlayerStatistics( (int) (long) playerJsonObject.get("age"),(int) (long) playerJsonObject.get("skating"),(int) (long) playerJsonObject.get("shooting"),(int) (long)playerJsonObject.get("checking"),(int)(long)playerJsonObject.get("saving"));
            IPlayer playerOb = new Player((String) playerJsonObject.get("playerName") ,
                    (String) playerJsonObject.get("position") ,
                    (Boolean) playerJsonObject.get("captain"),
                    playerStatistics);

            if(playerOb.checkPlayerValid()){
                playerListToReturn.add(playerOb);
            }
        }
        return playerListToReturn;
    }

    public ArrayList<IFreeAgent> getFreeAgentArrayList() throws Exception {
        Iterator<?> playerListIterator = freeAgentJsonArray.iterator();
        ArrayList<IFreeAgent> playerListToReturn = new ArrayList<IFreeAgent>();

        while(playerListIterator.hasNext()){
            JSONObject freeAgentJsonObject = (JSONObject) playerListIterator.next();

            if (freeAgentJsonObject.get("playerName")==null || freeAgentJsonObject.get("position")== null){
                throw new Exception("ERROR: Hey! Player cant have Null values....");
            }
            IPlayerStatistics freeAgentStatistics = new PlayerStatistics( (int) (long) freeAgentJsonObject.get("age"),(int) (long) freeAgentJsonObject.get("skating"),(int) (long) freeAgentJsonObject.get("shooting"),(int) (long)freeAgentJsonObject.get("checking"),(int)(long)freeAgentJsonObject.get("saving"));
            IFreeAgent freeAgentOb = new FreeAgent((String) freeAgentJsonObject.get("playerName") ,
                    (String) freeAgentJsonObject.get("position"),freeAgentStatistics);

            playerListToReturn.add(freeAgentOb);

        }
        return playerListToReturn;
    }
}
