package dhl.importJsonTest;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.InputOutput.importJson.Interface.ISerializeLeagueobjectModel;
import dhl.leagueModel.*;
import dhl.InputOutput.importJson.SerializeLeagueobjectModel;
import dhl.leagueModel.interfaceModel.*;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class SerializeLeagueobjectModelTest {
    ISerializeLeagueobjectModel objSerialize = new SerializeLeagueobjectModel();
    LeagueObjectModelMocks mocks;

    @BeforeEach
    public void initObject(){
        mocks = new LeagueObjectModelMocks();
    }
    @Test
    void serializeLeagueObjectModelTest() throws Exception{
        ILeagueObjectModel leagueObjectModel=new LeagueObjectModel();
        String str = objSerialize.serializeLeagueObjectModel(mocks.leagueModelMock());

        JSONParser jsonParser = new JSONParser();
        JSONObject JsonLeagueObject = (JSONObject) jsonParser.parse(str);

        leagueObjectModel = deserializeLeagueObjectJson(JsonLeagueObject);

        Assertions.assertNotNull(leagueObjectModel);
        Assertions.assertEquals("Dhl",leagueObjectModel.getLeagueName());
        Assertions.assertEquals(1,leagueObjectModel.getConferences().size());
        Assertions.assertEquals(2,leagueObjectModel.getFreeAgents().size());
        Assertions.assertEquals(2,leagueObjectModel.getCoaches().size());
        Assertions.assertEquals(2,leagueObjectModel.getGeneralManagers().size());
    }

    public ILeagueObjectModel deserializeLeagueObjectJson(JSONObject jsonLeagueObject) {
        LeagueObjectModel leagueObjectModel;
        String leagueName = (String) jsonLeagueObject.get("leagueName");
        JSONArray coachesJsonArrayList = (JSONArray) jsonLeagueObject.get("coaches");
        JSONArray generalManagerJsonArrayList = (JSONArray) jsonLeagueObject.get("managers");

        leagueObjectModel = new LeagueObjectModel(
            leagueName,
            deserializeConference(jsonLeagueObject),
            deserializeFreeAgent(jsonLeagueObject)
        );

        leagueObjectModel.setCoaches(deserializeCoach(coachesJsonArrayList));
        leagueObjectModel.setGeneralManagers(deserializeGeneralManager(generalManagerJsonArrayList));

        return leagueObjectModel;
    }
    public ArrayList<ICoach> deserializeCoach (JSONArray coachesJsonArrayList){
        Iterator<?> coachListIterator = coachesJsonArrayList.iterator();
        ArrayList<ICoach> coachListToReturn = new ArrayList<>();

        while (coachListIterator.hasNext()) {
            JSONObject coachJsonObject = (JSONObject) coachListIterator.next();
            ICoach coachOb = new Coach((String) coachJsonObject.get("name"),
                    (double) coachJsonObject.get("skating"), (double) coachJsonObject.get("shooting"), (double) coachJsonObject.get("checking"), (double) coachJsonObject.get("saving"));
            coachListToReturn.add(coachOb);
        }
        return  coachListToReturn;
    }
    public ArrayList<IGeneralManager> deserializeGeneralManager (JSONArray generalManagerJsonArrayList){
        Iterator<?> generalManagerListIterator = generalManagerJsonArrayList.iterator();
        ArrayList<IGeneralManager> generalManagerListToReturn = new ArrayList<IGeneralManager>();

        while (generalManagerListIterator.hasNext()) {
            String genManager = generalManagerListIterator.next().toString();
            IGeneralManager generalManagerOb = new GeneralManager(genManager);

            generalManagerListToReturn.add(generalManagerOb);
        }
        return  generalManagerListToReturn;
    }
    public ArrayList<IFreeAgent> deserializeFreeAgent (JSONObject jsonLeagueObject){
        JSONArray freeAgentJsonArray = (JSONArray) jsonLeagueObject.get("freeAgents");
        Iterator<?> freeAgentListIterator = freeAgentJsonArray.iterator();
        ArrayList<IFreeAgent> freeAgentListToReturn = new ArrayList<IFreeAgent>();

        while (freeAgentListIterator.hasNext()) {
            JSONObject freeAgentJsonObject = (JSONObject) freeAgentListIterator.next();
            JSONObject StatsObj = (JSONObject) freeAgentJsonObject.get("playerStats");

            IPlayerStatistics freeAgentStatistics = new PlayerStatistics(
                    (int) (long) StatsObj.get("age"),
                    (int) (long) StatsObj.get("skating"),
                    (int) (long) StatsObj.get("shooting"),
                    (int) (long) StatsObj.get("checking"),
                    (int) (long) StatsObj.get("saving"));
            IFreeAgent freeAgentOb = new FreeAgent((String) freeAgentJsonObject.get("playerName"),
                    (String) freeAgentJsonObject.get("position"), freeAgentStatistics);

            freeAgentListToReturn.add(freeAgentOb);

        }
        return freeAgentListToReturn;
    }
    public ArrayList<IConference> deserializeConference(JSONObject jsonLeagueObject){
        JSONArray conferenceJsonArray = (JSONArray) jsonLeagueObject.get("conferences");
        Iterator<?> conferenceListIterator = (conferenceJsonArray).iterator();
        ArrayList<IConference> conferencesListToReturn = new ArrayList<IConference>();

        while (conferenceListIterator.hasNext()) {
            JSONObject conferenceJsonObject = (JSONObject) conferenceListIterator.next();
            JSONArray divisionJsonArray = (JSONArray) conferenceJsonObject.get("divisions");

            Iterator<?> divisionListIterator = (divisionJsonArray).iterator();
            ArrayList<IDivision> divisonListToReturn = new ArrayList<IDivision>();
            while (divisionListIterator.hasNext()) {
                JSONObject divisionJsonObject = (JSONObject) divisionListIterator.next();

                Division divisionObject = new Division(
                        (String) divisionJsonObject.get("divisionName"),
                        deserializeTeam(divisionJsonObject)
                );
                divisonListToReturn.add(divisionObject);
            }
            Conference conferenceObject = new Conference((String) conferenceJsonObject.get("conferenceName"), divisonListToReturn);
            conferencesListToReturn.add(conferenceObject);
        }
        return conferencesListToReturn;
    }
    public ArrayList<ITeam> deserializeTeam(JSONObject divisionJsonObject){
        JSONArray teamJsonArray = (JSONArray) divisionJsonObject.get("teams");
        Iterator<?> teamListIterator = (teamJsonArray).iterator();
        ArrayList<ITeam> TeamListToReturn = new ArrayList<ITeam>();
        while (teamListIterator.hasNext()) {
            JSONObject teamJsonObject = (JSONObject) teamListIterator.next();

            JSONArray playerJsonArray = (JSONArray) teamJsonObject.get("players");
            JSONObject headCoachJsonObject = (JSONObject) teamJsonObject.get("headCoach");

            Iterator<?> playerListIterator = playerJsonArray.iterator();
            ArrayList<IPlayer> playerListToReturn = new ArrayList<IPlayer>();

            while (playerListIterator.hasNext()) {
                JSONObject playerJsonObject = (JSONObject) playerListIterator.next();
                JSONObject StatsObj = (JSONObject) playerJsonObject.get("playerStats");

                IPlayerStatistics playerStatistics = new PlayerStatistics((int) (long) StatsObj.get("age"), (int) (long) StatsObj.get("skating"), (int) (long) StatsObj.get("shooting"), (int) (long) StatsObj.get("checking") , (int) (long) StatsObj.get("saving"));
                IPlayer playerOb = new Player((String) playerJsonObject.get("playerName"),(String) playerJsonObject.get("position"),(Boolean) playerJsonObject.get("captain"),playerStatistics);

                playerListToReturn.add(playerOb);
            }
            ITeam teamObject = new Team(
                    (String) teamJsonObject.get("teamName"),
                    (String) teamJsonObject.get("generalManager"),
                    new Coach((String) headCoachJsonObject.get("name"), (double) headCoachJsonObject.get("skating"), (double) headCoachJsonObject.get("shooting"), (double) headCoachJsonObject.get("checking"), (double) headCoachJsonObject.get("saving")),
                    playerListToReturn
            );
            TeamListToReturn.add(teamObject);
        }
        return TeamListToReturn;
    }

}
