package dhl.leagueModel;

import dhl.database.interfaceDB.IPlayerDB;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.IConference;
import dhl.leagueModel.interfaceModel.IDivision;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.IRetirementSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RetirementSystem implements IRetirementSystem {
    private ILeagueObjectModel leagueObjectModel;
    private IPlayerDB playerDB;

    public RetirementSystem(IPlayerDB playerDB, ILeagueObjectModel leagueObjectModel) {
        this.leagueObjectModel = leagueObjectModel;
        this.playerDB = playerDB;
    }

    public ILeagueObjectModel getLeagueObjectModel() {
        return leagueObjectModel;
    }

    public void setLeagueObjectModel(ILeagueObjectModel leagueObjectModel) {
        this.leagueObjectModel = leagueObjectModel;
    }

    public void initiateRetirement(Map<String, List<IPlayer>> playersToRetire, List<IPlayer> freeAgentsToRetire) throws Exception {
        retireFreeAgents(freeAgentsToRetire, leagueObjectModel.getFreeAgents());
        for (IConference conference : leagueObjectModel.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    if (playersToRetire.get(team.getTeamName()).size() > 0) {
                        retirePLayers(playersToRetire.get(team.getTeamName()), team, leagueObjectModel.getFreeAgents());
                    }
                }
            }
        }
        if (playersToRetire.keySet().size() > 0 || freeAgentsToRetire.size() > 0) {
            insertVeterans(playersToRetire, freeAgentsToRetire);
        }
    }

    public void retireFreeAgents(List<IPlayer> agentsSelectedToRetire, List<IPlayer> freeAgents) {
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
                newlyAddedPlayers.add(selectPlayerFromFreeAgent(player, freeAgents));
            }
        }

        removeRetiredPlayersFromTeam(retiringPlayerNames, team);
        for (IPlayer newPlayer : newlyAddedPlayers) {
            List<IPlayer> players = team.getPlayers();
            players.add(newPlayer);
        }
    }

    public IPlayer selectPlayerFromFreeAgent(IPlayer player, List<IPlayer> freeAgents) {
        IPlayer selectedAgent;
        String playerPosition = player.getPosition();
        List<IPlayer> freeAgentsOfSamePosition = freeAgents.stream().filter((IPlayer agent) -> { return agent.getPosition() == playerPosition; }).collect(Collectors.toList());
        sortFreeAgentsByStrength(freeAgentsOfSamePosition);
        int indexOfBestPlayer = 0;
        selectedAgent = freeAgentsOfSamePosition.get(indexOfBestPlayer);

        removeSelectedAgentFromFreeAgents(freeAgents, selectedAgent);
        player = new Player(selectedAgent.getPlayerName(), selectedAgent.getPosition(), player.getCaptain(), selectedAgent.getPlayerStats());
        return player;
    }

    public void sortFreeAgentsByStrength(List<IPlayer> freeAgents) {
        Collections.sort(freeAgents, (player1, player2) -> (int) Math.round(player2.getPlayerStrength()) - (int) Math.round(player1.getPlayerStrength()));
    }

    public void removeSelectedAgentFromFreeAgents(List<IPlayer> freeAgents, IPlayer selectedAgent) {
        freeAgents.removeIf(agent -> (agent.getPlayerName() == selectedAgent.getPlayerName()));

    }

    public void removeRetiredPlayersFromTeam(List<String> playerNames, ITeam team) {
        List<IPlayer> playersInTeam = team.getPlayers();
        playersInTeam.removeIf(player -> (playerNames.contains(player.getPlayerName())));

    }

    public void insertVeterans(Map<String, List<IPlayer>> playersToRetire, List<IPlayer> freeAgentsToRetire) throws Exception {
        playerDB.insertRetiredPlayers(leagueObjectModel, playersToRetire, freeAgentsToRetire);
    }
}
