package dhl.businessLogic.simulationStateMachine;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.interfaces.ITeamRosterUpdater;
import dhl.inputOutput.ui.IListFormat;
import dhl.inputOutput.ui.IUserInputOutput;
import dhl.inputOutput.ui.PlayerListFormat;

import java.util.ArrayList;
import java.util.List;

public class UpdateUserTeamRoster implements ITeamRosterUpdater {

    IUserInputOutput ioObject;
    IListFormat listDisplay;

    public UpdateUserTeamRoster(IUserInputOutput ioObject) {
        this.ioObject = ioObject;
        listDisplay = PlayerListFormat.getInstance();
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
        listDisplay.showList(players);

        int playerId = Integer.parseInt(ioObject.getUserInput());
        IPlayer newPlayerForTeam = players.get(playerId);
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
        listDisplay.showList(playerList);

        int playerId = Integer.parseInt(ioObject.getUserInput());
        IPlayer playerToDropFromTeam = playerList.get(playerId);
        List<IPlayer> players = team.getPlayers();
        players.remove(playerToDropFromTeam);
        List<IPlayer> freeAgents = league.getFreeAgents();
        freeAgents.add(playerToDropFromTeam);
    }

}