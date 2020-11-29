package dhl.businessLogic.simulationStateMachine;

import dhl.businessLogic.leagueModel.PlayerPosition;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.interfaces.ITeamRosterUpdater;
import dhl.inputOutput.ui.interfaces.IListFormat;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import dhl.inputOutput.ui.PlayerListFormat;

import java.util.ArrayList;
import java.util.List;

public class UpdateUserTeamRoster implements ITeamRosterUpdater {

    private static final int TOTAL_GOALIES = 4;
    private static final int TOTAL_FORWARDS = 16;
    private static final int TOTAL_DEFENSE = 10;

    IUserInputOutput ioObject;
    IListFormat listDisplay;

    public UpdateUserTeamRoster(IUserInputOutput ioObject) {
        this.ioObject = ioObject;
        listDisplay = PlayerListFormat.getInstance();
    }

    public void validateTeamRoster(ITeam team, ILeagueObjectModel leagueObjectModel) {
        int totalForwards = 0;
        int totalDefense = 0;
        int totalGoalies = 0;
        ArrayList<IPlayer> players = (ArrayList<IPlayer>) team.getPlayers();

        for (IPlayer player : players) {
            String position = player.getPosition();
            if (position.equals(PlayerPosition.FORWARD.toString())){
                totalForwards = totalForwards + 1;
            }
            else if (position.equals(PlayerPosition.DEFENSE.toString())){
                totalDefense = totalDefense + 1;
            }
            else if (position.equals(PlayerPosition.GOALIE.toString())) {
                totalGoalies = totalGoalies + 1;
            }
        }
        if(totalDefense > TOTAL_DEFENSE || totalDefense < TOTAL_DEFENSE){
            updatePlayers(totalDefense, PlayerPosition.DEFENSE.toString(), TOTAL_DEFENSE, team, leagueObjectModel);
        }
        if(totalForwards > TOTAL_FORWARDS || totalForwards < TOTAL_FORWARDS){
            updatePlayers(totalForwards, PlayerPosition.FORWARD.toString(), TOTAL_FORWARDS, team, leagueObjectModel);
        }
        if(totalGoalies > TOTAL_GOALIES || totalGoalies < TOTAL_GOALIES){
            updatePlayers(totalGoalies, PlayerPosition.GOALIE.toString(), TOTAL_GOALIES, team, leagueObjectModel);
        }
        team.setRoster();
    }

    public void updatePlayers(int currentCount, String playerPosition, int validCount, ITeam team, ILeagueObjectModel leagueObjectModel) {
        if (currentCount < validCount){
            while (currentCount < validCount){
                addPlayer(playerPosition, team, leagueObjectModel);
                currentCount = currentCount + 1;
            }
        }
        else if (currentCount > validCount) {
            while (currentCount > validCount) {
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