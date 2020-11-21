package dhl.inputOutput.importJson.serializeDeserialize;

import dhl.businessLogic.leagueModel.Player;
import dhl.businessLogic.leagueModel.PlayerStatistics;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.IDeserializeLeagueObjectModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

;

public class DeserializeLeagueObjectModel implements IDeserializeLeagueObjectModel {
    String jsonFilePath;
    final String playerFileName = "--InjuredPlayer.json";
    final String jsonExtension = ".json";

    public DeserializeLeagueObjectModel(String jsonFilePath){
        jsonFilePath = jsonFilePath;
    }

    public ILeagueObjectModel deserializeLeagueObjectJson(String leagueName) throws Exception {
        String leagueObjectModelJsonPath = jsonFilePath + leagueName + jsonExtension;

        FileReader reader = new FileReader(leagueObjectModelJsonPath);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonLeagueObject = (JSONObject) jsonParser.parse(reader);

        IGameConfig gameConfig = null;
        dhl.inputOutput.importJson.CreateLeagueObjectModel createLeagueObjectModel = new dhl.inputOutput.importJson.CreateLeagueObjectModel(updateLeagueObjectModelJson(jsonLeagueObject));
        return createLeagueObjectModel.getLeagueObjectModel();
    }

    public List<IPlayer> deserializePlayers(String leagueName) throws ParseException, IOException {
        String playersJsonPath = jsonFilePath + leagueName + playerFileName;

        FileReader reader = new FileReader(playersJsonPath);
        JSONParser jsonParser = new JSONParser();
        JSONArray arrPlayers = (JSONArray) jsonParser.parse(reader);
        List<IPlayer> playerList = new ArrayList<>();

        Iterator<?> arrPlayersIterator = (arrPlayers).iterator();
        while (arrPlayersIterator.hasNext()) {
            JSONObject existingPlayersJsonObject = (JSONObject) arrPlayersIterator.next();
            JSONObject playerStatsJsonobject = (JSONObject) existingPlayersJsonObject.get("playerStats");
            IPlayerStatistics playerStatistics = new PlayerStatistics
                    ((int) (long) playerStatsJsonobject.get("age"),
                            (int) (long) playerStatsJsonobject.get("skating") ,
                            (int) (long) playerStatsJsonobject.get("shooting"),
                            (int) (long) playerStatsJsonobject.get("checking"),
                            (int) (long) playerStatsJsonobject.get("saving"));
            playerList.add(new Player(
                    (String) existingPlayersJsonObject.get("playerName"),
                    (String) existingPlayersJsonObject.get("position"),
                    (Boolean) existingPlayersJsonObject.get("captain"),
                    playerStatistics
            ));

        }
        return playerList;
    }

    public JSONObject updateLeagueObjectModelJson(JSONObject jsonLeagueObject) {
        jsonLeagueObject.get("conferences");

        JSONArray freeAgentsJsonArray = (JSONArray) jsonLeagueObject.get("freeAgents");
        JSONArray newfreeAgentsJsonArray = new JSONArray();
        Iterator<?> freeAgentsListIterator = (freeAgentsJsonArray).iterator();

        while (freeAgentsListIterator.hasNext()) {
            JSONObject freeAgentsJsonObject = (JSONObject) freeAgentsListIterator.next();

            JSONObject playerStatsJsonobject = (JSONObject) freeAgentsJsonObject.get("playerStats");
            freeAgentsJsonObject.put("age", playerStatsJsonobject.get("age"));
            freeAgentsJsonObject.put("skating", playerStatsJsonobject.get("skating"));
            freeAgentsJsonObject.put("checking", playerStatsJsonobject.get("checking"));
            freeAgentsJsonObject.put("shooting", playerStatsJsonobject.get("shooting"));
            freeAgentsJsonObject.put("saving", playerStatsJsonobject.get("saving"));
            freeAgentsJsonObject.remove("playerStats");
            String position = (String) freeAgentsJsonObject.get("position");
            freeAgentsJsonObject.remove("position");
            freeAgentsJsonObject.put("position", position.toLowerCase());
            newfreeAgentsJsonArray.add(freeAgentsJsonObject);
        }
        jsonLeagueObject.remove("freeAgents");
        jsonLeagueObject.put("freeAgents", newfreeAgentsJsonArray);

        JSONArray conferenceJsonArray = (JSONArray) jsonLeagueObject.get("conferences");
        Iterator<?> conferenceListIterator = (conferenceJsonArray).iterator();

        while (conferenceListIterator.hasNext()) {
            JSONObject conferenceJsonObject = (JSONObject) conferenceListIterator.next();

            JSONArray divisionJsonArray = (JSONArray) conferenceJsonObject.get("divisions");
            Iterator<?> divisionListIterator = (divisionJsonArray).iterator();

            while (divisionListIterator.hasNext()) {
                JSONObject divisionJsonObject = (JSONObject) divisionListIterator.next();

                JSONArray teamJsonArray = (JSONArray) divisionJsonObject.get("teams");
                Iterator<?> teamListIterator = (teamJsonArray).iterator();

                while (teamListIterator.hasNext()) {
                    JSONObject teamJsonObject = (JSONObject) teamListIterator.next();
                    JSONObject newTeamJsonObject = new JSONObject();

                    JSONArray playerJsonArray = (JSONArray) teamJsonObject.get("players");
                    JSONArray newPlayerJsonArray = new JSONArray();
                    Iterator<?> playerListIterator = (playerJsonArray).iterator();

                    while (playerListIterator.hasNext()) {
                        JSONObject playerJsonObject = (JSONObject) playerListIterator.next();
                        JSONObject playerStatsJsonobject = (JSONObject) playerJsonObject.get("playerStats");
                        playerJsonObject.put("age", playerStatsJsonobject.get("age"));
                        playerJsonObject.put("skating", playerStatsJsonobject.get("skating"));
                        playerJsonObject.put("checking", playerStatsJsonobject.get("checking"));
                        playerJsonObject.put("shooting", playerStatsJsonobject.get("shooting"));
                        playerJsonObject.put("saving", playerStatsJsonobject.get("saving"));
                        playerJsonObject.remove("playerStats");
                        String position = (String) playerJsonObject.get("position");
                        playerJsonObject.remove("position");
                        playerJsonObject.put("position", position.toLowerCase());
                        newPlayerJsonArray.add(playerJsonObject);

                    }
                    teamJsonObject.remove("players");
                    teamJsonObject.put("players", newPlayerJsonArray);
                }
            }
        }
        return jsonLeagueObject;
    }
}
