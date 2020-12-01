package dhl.businessLogic.aging;

import dhl.businessLogic.aging.interfaceAging.IRetirement;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Retirement implements IRetirement {
    private static final int BESTPLAYERINDEX = 0;
    private ILeagueObjectModel leagueObjectModel;
    private ISerializeLeagueObjectModel serializeModel;
    private static final Logger logger = LogManager.getLogger(Retirement.class);
    LeagueModelAbstractFactory leagueFactory;

    public Retirement(ISerializeLeagueObjectModel serializeModel) {
        logger.info("Retirement Constructor Object created");
        this.serializeModel = serializeModel;
        leagueFactory = LeagueModelAbstractFactory.instance();
    }

    public ILeagueObjectModel getLeagueObjectModel() {
        return leagueObjectModel;
    }

    public void setLeagueObjectModel(ILeagueObjectModel leagueObjectModel) {
        this.leagueObjectModel = leagueObjectModel;
    }

    public void initiateRetirement(Map<String, List<IPlayer>> playersToRetire, List<IPlayer> freeAgentsToRetire, ILeagueObjectModel leagueObjectModel) throws IOException, ParseException {
        logger.debug("Executing retirement algorithm for players");
        this.leagueObjectModel = leagueObjectModel;
        retireFreeAgents(freeAgentsToRetire, leagueObjectModel.getFreeAgents());
        for (IConference conference : this.leagueObjectModel.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    if (playersToRetire.containsKey(team.getTeamName())) {
                        if (playersToRetire.get(team.getTeamName()).size() > 0) {
                            retirePLayers(playersToRetire.get(team.getTeamName()), team, this.leagueObjectModel.getFreeAgents());
                        }
                    }
                }
            }
        }
        if (playersToRetire.keySet().size() > 0 || freeAgentsToRetire.size() > 0) {
            insertVeterans(playersToRetire, freeAgentsToRetire);
        }
    }

    public void retireFreeAgents(List<IPlayer> agentsSelectedToRetire, List<IPlayer> freeAgents) {
        logger.debug("Executing retirement algorithm for free agents");
        if (agentsSelectedToRetire.size() > 0) {
            List<String> retiringPlayerNames = new ArrayList<>();
            for (IPlayer retiringAgent : agentsSelectedToRetire) {
                retiringPlayerNames.add(retiringAgent.getPlayerName());
            }

            List<IPlayer> updatedFreeAgents = new ArrayList<>();
            updatedFreeAgents.addAll(freeAgents);
            for (IPlayer freeAgent : updatedFreeAgents) {
                if (retiringPlayerNames.contains(freeAgent.getPlayerName())) {
                    removeSelectedAgentFromFreeAgents(freeAgents, freeAgent);
                }
            }

        }
    }

    public void retirePLayers(List<IPlayer> playersSelectedToRetire, ITeam team, List<IPlayer> freeAgents) {
        List<String> retiringPlayerNames = new ArrayList<>();
        List<IPlayer> newlyAddedPlayers = new ArrayList<>();
        for (IPlayer retiringPlayer : playersSelectedToRetire) {
            retiringPlayerNames.add(retiringPlayer.getPlayerName());
        }
        for (IPlayer player : team.getPlayers()) {
            if (retiringPlayerNames.indexOf(player.getPlayerName()) >= 0) {
                List<IPlayer> freeAgentsOfSamePosition = freeAgents.stream().filter((IPlayer agent) -> agent.getPosition() == player.getPosition()).collect(Collectors.toList());
                if (freeAgentsOfSamePosition.size() > 0) {
                    sortFreeAgentsByStrength(freeAgentsOfSamePosition);
                    newlyAddedPlayers.add(selectPlayerFromFreeAgent(player, freeAgentsOfSamePosition, freeAgents));
                } else if (freeAgents.size() > 0) {
                    sortFreeAgentsByStrength(freeAgents);
                    newlyAddedPlayers.add(selectPlayerFromFreeAgent(player, freeAgents, freeAgents));
                }
            }
        }

        removeRetiredPlayersFromTeam(retiringPlayerNames, team);
        for (IPlayer newPlayer : newlyAddedPlayers) {
            List<IPlayer> players = team.getPlayers();
            players.add(newPlayer);
        }
    }

    public IPlayer selectPlayerFromFreeAgent(IPlayer player, List<IPlayer> freeAgentsAvailable, List<IPlayer> allFreeAgents) {
        logger.debug("Select players to replace from free agent");
        IPlayer selectedAgent;

        int indexOfBestPlayer = BESTPLAYERINDEX;
        selectedAgent = freeAgentsAvailable.get(indexOfBestPlayer);

        removeSelectedAgentFromFreeAgents(allFreeAgents, selectedAgent);
        player = leagueFactory.createPlayer(selectedAgent.getPlayerName(), selectedAgent.getPosition(), player.getCaptain(), selectedAgent.getPlayerStats());
        return player;
    }

    public void sortFreeAgentsByStrength(List<IPlayer> freeAgents) {
        logger.debug("Sort players by free agent by player strength");
        Collections.sort(freeAgents, (player1, player2) -> (int) Math.round(player2.getPlayerStrength()) - (int) Math.round(player1.getPlayerStrength()));
    }

    public void removeSelectedAgentFromFreeAgents(List<IPlayer> freeAgents, IPlayer selectedAgent) {
        logger.debug("Remove selected free agent" + selectedAgent.getPlayerName());
        freeAgents.removeIf((IPlayer agent) -> (agent.getPlayerName() == selectedAgent.getPlayerName()));

    }

    public void removeRetiredPlayersFromTeam(List<String> playerNames, ITeam team) {
        logger.debug("Remove retired players");
        List<IPlayer> playersInTeam = team.getPlayers();
        playersInTeam.removeIf((IPlayer player) -> (playerNames.contains(player.getPlayerName())));

    }

    public void insertVeterans(Map<String, List<IPlayer>> playersToRetire, List<IPlayer> freeAgentsToRetire) throws IOException, ParseException {
        logger.debug("Inserting retired players");
        List<IPlayer> retiredPlayers = new ArrayList<>();
        retiredPlayers.addAll(freeAgentsToRetire);
        for (String teamName : playersToRetire.keySet()) {
            retiredPlayers.addAll(playersToRetire.get(teamName));
        }
        serializeModel.updateSerializedPlayerListToJsonFile(retiredPlayers, leagueObjectModel.getLeagueName());
    }
}
