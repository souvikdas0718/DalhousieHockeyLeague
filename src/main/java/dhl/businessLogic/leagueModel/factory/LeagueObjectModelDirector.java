package dhl.businessLogic.leagueModel.factory;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.businessLogic.leagueModel.factory.interfaceFactory.ILeagueObjectModelBuilder;
import dhl.businessLogic.leagueModel.factory.interfaceFactory.ILeagueObjectModelDirector;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LeagueObjectModelDirector implements ILeagueObjectModelDirector {
    private ILeagueObjectModelBuilder builder;
    private LeagueModelFactory factory;

    public LeagueObjectModelDirector(ILeagueObjectModelBuilder builder) {
        this.builder = builder;
        factory = LeagueModelFactory.instance();
    }

    public ILeagueObjectModel construct(String leagueName,List<IConference> conferences,List<IPlayer> freeAgents, List<ICoach> coaches, List<IGeneralManager> managers, IGameConfig gameConfig) {
        builder.addLeagueName(leagueName);
        builder.addConferences(conferences);
        builder.addFreeAgents(freeAgents);
        builder.addCoaches(coaches);
        builder.addManagers(managers);
        builder.addGameConfig(gameConfig);
        return builder.getResult();

    }

    public ILeagueObjectModel constructFromJson(JSONObject jsonLeague) {
        String leagueName = (String) jsonLeague.get("leagueName");

        JSONArray conferencesJson = (JSONArray) jsonLeague.get("conferences");
        List<IConference> conferences = getConferences(conferencesJson);

        JSONArray agentsJson = (JSONArray) jsonLeague.get("freeAgents");
        List<IPlayer> freeAgents = getFreeAgents(agentsJson);

        JSONArray managersJson = (JSONArray) jsonLeague.get("generalManagers");
        List<IGeneralManager> managers = getGeneralManagers(managersJson);

        JSONArray coachesJson = (JSONArray) jsonLeague.get("coaches");
        List<ICoach> coaches = getCoaches(coachesJson);

        return construct(leagueName,conferences,freeAgents,coaches,managers,getGameConfig(jsonLeague));
    }

    public IGameConfig getGameConfig(JSONObject jsonLeague){
        JSONObject gameConfigJson = (JSONObject) jsonLeague.get("gameplayConfig");
        return factory.createGameConfig(gameConfigJson);
    }

    public List<IConference> getConferences(JSONArray conferenceJsonArray)  {
        Iterator<?> conferenceIterator =conferenceJsonArray.iterator();
        List<IConference> conferences = new ArrayList<>();

        while (conferenceIterator.hasNext()) {
            JSONObject conferenceJson = (JSONObject)conferenceIterator.next();
            JSONArray divisionsJsonArray = (JSONArray) conferenceJson.get("divisions");
            List<IDivision> divisions = getDivisions(divisionsJsonArray);
            IConference conference = factory.createConference((String) conferenceJson.get("conferenceName"),divisions);
            conferences.add(conference);
        }
        return conferences;
    }

    public List<IDivision> getDivisions(JSONArray divisionsJsonArray) {
        Iterator<?> divisionIterator = divisionsJsonArray.iterator();
        List<IDivision> divisons = new ArrayList<>();

        while (divisionIterator.hasNext()) {
            JSONObject divisionJson = (JSONObject)divisionIterator.next();
            JSONArray teamJsonArray = (JSONArray) divisionJson.get("teams");
            List<ITeam> teams = getTeams(teamJsonArray);
            IDivision division = factory.createDivision((String) divisionJson.get("divisionName"),teams);
            divisons.add(division);

        }
        return divisons;
    }

    public List<ITeam> getTeams(JSONArray teamJsonArray) {
        Iterator<?> teamIterator = teamJsonArray.iterator();
        List<ITeam> teams = new ArrayList<>();

        while (teamIterator.hasNext()) {
            JSONObject teamJson = (JSONObject) teamIterator.next();
            JSONArray playerJsonArray = (JSONArray) teamJson.get("players");
            JSONObject coachJson = (JSONObject) teamJson.get("headCoach");

            ICoach coach = getCoach(coachJson);
            List<IPlayer> players = getPlayers(playerJsonArray);
            ITeam team = factory.createTeam((String) teamJson.get("teamName"), (String) teamJson.get("generalManager"),coach,players);
            teams.add(team);
        }
        return teams;
    }

    public ICoach getCoach(JSONObject coachJson) {
        return factory.createCoach( (String) coachJson.get("name"), (double) coachJson.get("skating"), (double) coachJson.get("shooting"), (double) coachJson.get("checking"), (double) coachJson.get("saving"));
    }

    public List<IPlayer> getPlayers(JSONArray playerJsonArray) {
        Iterator<?> playerIterator = playerJsonArray.iterator();
        List<IPlayer> players = new ArrayList<>();

        while (playerIterator.hasNext()) {
            JSONObject playerJson = (JSONObject) playerIterator.next();

            IPlayerStatistics playerStatistics = getPlayerStatistics(playerJson);
            IPlayer playerOb = factory.createPlayer((String) playerJson.get("playerName"), (String) playerJson.get("position"), (Boolean) playerJson.get("captain"), playerStatistics);
            players.add(playerOb);
        }
        return players;
    }

    public IPlayerStatistics getPlayerStatistics(JSONObject playerJson) {
        return factory.createPlayerStatistics((int) (long) playerJson.get("age"), (int) (long) playerJson.get("skating"), (int) (long) playerJson.get("shooting"), (int) (long) playerJson.get("checking"), (int) (long) playerJson.get("saving"));
    }

    public List<IPlayer> getFreeAgents(JSONArray freeAgentsJsonArray) {
        Iterator<?> agentIterator = freeAgentsJsonArray.iterator();
        List<IPlayer> freeAgents = new ArrayList<>();

        while (agentIterator.hasNext()) {
            JSONObject freeAgentJson = (JSONObject) agentIterator.next();

            IPlayerStatistics playerStatistics = getPlayerStatistics(freeAgentJson);
            IPlayer freeAgent = factory.createFreeAgent((String) freeAgentJson.get("playerName"), (String) freeAgentJson.get("position"), playerStatistics);
            freeAgents.add(freeAgent);
        }
        return freeAgents;
    }

    public List<ICoach> getCoaches(JSONArray coachesJsonArray){
        Iterator<?> coachListIterator = coachesJsonArray.iterator();
        List<ICoach> coaches = new ArrayList<>();

        while (coachListIterator.hasNext()) {
            JSONObject coachJson = (JSONObject) coachListIterator.next();
            ICoach coach = getCoach(coachJson);
            coaches.add(coach);
        }
        return coaches;
    }

    public List<IGeneralManager> getGeneralManagers(JSONArray managerJsonArray) {
        Iterator<?> managerIterator = managerJsonArray.iterator();
        List<IGeneralManager> generalManagers = new ArrayList<>();

        while (managerIterator.hasNext()) {
            String managerName = managerIterator.next().toString();
            IGeneralManager generalManager = factory.createGeneralManager(managerName);
            generalManagers.add(generalManager);
        }
        return generalManagers;
    }

}