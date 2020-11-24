package dhl.inputOutput.importJson.serializeDeserialize;

import dhl.businessLogic.leagueModel.Player;
import dhl.businessLogic.leagueModel.PlayerStatistics;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;
import dhl.inputOutput.importJson.CreateLeagueObjectModel;
import dhl.inputOutput.importJson.interfaces.ICreateLeagueObjectModel;
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

public class DeserializeLeagueObjectModel implements IDeserializeLeagueObjectModel {
    final String playerFileName = "--RetiredPlayersInLeague.json";
    final String jsonExtension = ".json";
    String jsonFilePath;

    public DeserializeLeagueObjectModel(String inputJsonFilePath) {
        jsonFilePath = inputJsonFilePath;
    }

    public ILeagueObjectModel deserializeLeagueObjectJson(String leagueName) throws Exception {
        String leagueObjectModelJsonPath = jsonFilePath + leagueName + jsonExtension;
        FileReader reader = new FileReader(leagueObjectModelJsonPath);
        ICreateLeagueObjectModel createLeagueObjectModel;

        try {

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonLeagueObject = (JSONObject) jsonParser.parse(reader);

            IGameConfig gameConfig = null;
            JSONObject jsonLeagueObjectModel = updateLeagueObjectModelJson(jsonLeagueObject);
            createLeagueObjectModel = new CreateLeagueObjectModel(jsonLeagueObjectModel);
        } finally {
            reader.close();
        }
        return createLeagueObjectModel.getLeagueObjectModel();
    }

    public List<IPlayer> deserializePlayers(String leagueName) throws ParseException, IOException {
        String playersJsonPath = jsonFilePath + leagueName + playerFileName;
        List<IPlayer> playerList = new ArrayList<>();
        FileReader reader = new FileReader(playersJsonPath);
        JSONParser jsonParser = new JSONParser();

        try {
            JSONArray arrPlayers = (JSONArray) jsonParser.parse(reader);

            Iterator<?> arrPlayersIterator = (arrPlayers).iterator();
            while (arrPlayersIterator.hasNext()) {
                JSONObject existingPlayersJsonObject = (JSONObject) arrPlayersIterator.next();
                JSONObject playerStatsJsonobject = (JSONObject) existingPlayersJsonObject.get("playerStats");
                IPlayerStatistics playerStatistics = new PlayerStatistics
                        ((int) (long) playerStatsJsonobject.get("age"),
                                (int) (long) playerStatsJsonobject.get("skating"),
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
        } finally {
            reader.close();
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
