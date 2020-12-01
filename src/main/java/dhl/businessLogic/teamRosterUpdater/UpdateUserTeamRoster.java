package dhl.businessLogic.teamRosterUpdater;

import dhl.businessLogic.leagueModel.PlayerPosition;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.teamRosterUpdater.interfaces.ITeamRosterUpdater;
import dhl.inputOutput.ui.PlayerListFormat;
import dhl.inputOutput.ui.interfaces.IListFormat;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class UpdateUserTeamRoster implements ITeamRosterUpdater {

    private int expectedTotalGoalies;
    private int expectedTotalDefence;
    private int expectedTotalForward;
    IUserInputOutput ioObject;
    IListFormat listDisplay;

    private static final Logger logger = LogManager.getLogger(UpdateUserTeamRoster.class);

    public UpdateUserTeamRoster(IUserInputOutput ioObject) {
        this.ioObject = ioObject;
        listDisplay = PlayerListFormat.getInstance();
    }

    public void validateTeamRoster(ITeam team, ILeagueObjectModel leagueObjectModel) {
        expectedTotalDefence = team.getTotalDefense();
        expectedTotalForward = team.getTotalForwards();
        expectedTotalGoalies = team.getTotalGoalies();
        logger.info("Validating team: " + team.getTeamName());
        int totalForwards = 0;
        int totalDefense = 0;
        int totalGoalies = 0;
        ArrayList<IPlayer> players = (ArrayList<IPlayer>) team.getPlayers();

        for (IPlayer player : players) {
            String position = player.getPosition();
            if (position.equals(PlayerPosition.FORWARD.toString())) {
                totalForwards = totalForwards + 1;
            } else if (position.equals(PlayerPosition.DEFENSE.toString())) {
                totalDefense = totalDefense + 1;
            } else if (position.equals(PlayerPosition.GOALIE.toString())) {
                totalGoalies = totalGoalies + 1;
            }
        }
        if (totalDefense > expectedTotalDefence || totalDefense < expectedTotalDefence) {
            updatePlayers(totalDefense, PlayerPosition.DEFENSE.toString(), expectedTotalDefence, team, leagueObjectModel);
        }
        if (totalForwards > expectedTotalForward || totalForwards < expectedTotalForward) {
            updatePlayers(totalForwards, PlayerPosition.FORWARD.toString(), expectedTotalForward, team, leagueObjectModel);
        }
        if (totalGoalies > expectedTotalGoalies || totalGoalies < expectedTotalGoalies) {
            updatePlayers(totalGoalies, PlayerPosition.GOALIE.toString(), expectedTotalGoalies, team, leagueObjectModel);
        }
        team.setRoster();
    }

    public void updatePlayers(int currentCount, String playerPosition, int validCount, ITeam team, ILeagueObjectModel leagueObjectModel) {
        logger.info("Updating team: " + team.getTeamName());
        if (currentCount < validCount) {
            while (currentCount < validCount) {
                logger.info("Adding Players from team: " + team.getTeamName());
                addPlayer(playerPosition, team, leagueObjectModel);
                currentCount = currentCount + 1;
            }
        } else if (currentCount > validCount) {
            while (currentCount > validCount) {
                logger.info("Droping Players from team: " + team.getTeamName());
                dropPlayer(playerPosition, team, leagueObjectModel);
                currentCount = currentCount - 1;
            }
        }
    }

    public void addPlayer(String playerPosition, ITeam team, ILeagueObjectModel league) {
        ioObject.printMessage("Enter ID of " + playerPosition + " you want to add");
        ArrayList<IPlayer> players = new ArrayList<>();
        for (IPlayer player : league.getFreeAgents()) {
            String position = player.getPosition();
            if (position.equals(playerPosition)) {
                players.add(player);
            }
        }
        logger.debug("Showing Free Agent list to user");
        listDisplay.showList(players);

        int playerId = Integer.parseInt(ioObject.getUserInput());
        IPlayer newPlayerForTeam = players.get(playerId);
        logger.info("Player " + newPlayerForTeam.getPlayerName() + " added to team: " + team.getTeamName());
        List<IPlayer> teamPlayers = team.getPlayers();
        teamPlayers.add(newPlayerForTeam);
        List<IPlayer> freeAgents = league.getFreeAgents();
        freeAgents.remove(newPlayerForTeam);
    }

    public void dropPlayer(String playerPosition, ITeam team, ILeagueObjectModel league) {
        ioObject.printMessage("Enter ID of " + playerPosition + " you want to drop");
        ArrayList<IPlayer> playerList = new ArrayList<>();
        for (IPlayer player : team.getPlayers()) {
            String position = player.getPosition();
            if (position.equals(playerPosition)) {
                playerList.add(player);
            }
        }
        logger.debug("Showing Team player's list to user");
        listDisplay.showList(playerList);

        int playerId = Integer.parseInt(ioObject.getUserInput());
        IPlayer playerToDropFromTeam = playerList.get(playerId);
        logger.info("Player " + playerToDropFromTeam.getPlayerName() + " Dropped from team: " + team.getTeamName());
        List<IPlayer> players = team.getPlayers();
        players.remove(playerToDropFromTeam);
        List<IPlayer> freeAgents = league.getFreeAgents();
        freeAgents.add(playerToDropFromTeam);
    }

}