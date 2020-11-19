package dhl.businessLogic.simulationStateMachine;

import dhl.inputOutput.ui.IListFormat;
import dhl.inputOutput.ui.IUserInputOutput;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.interfaces.IUpdateUserTeamRoster;
import dhl.inputOutput.ui.PlayerListFormat;

import java.util.ArrayList;
import java.util.List;

public class UpdateUserTeamRoster implements IUpdateUserTeamRoster {

    IUserInputOutput ioObject;
    IListFormat listDisplay;

    public UpdateUserTeamRoster(IUserInputOutput ioObject) {
        this.ioObject = ioObject;
        listDisplay = PlayerListFormat.getInstance();
    }

    public void dropSkater(ITeam team, ILeagueObjectModel leagueObjectModel) {
        ioObject.printMessage("Enter ID of Skater to drop");
        ArrayList<IPlayer> skaters = new ArrayList<>();
        for (IPlayer player : team.getPlayers()) {
            String position = player.getPosition();
            if (position.equals("forward") || position.equals("defense")) {
                skaters.add(player);
            }
        }
        listDisplay.showList(skaters);
        int playerId = Integer.parseInt(ioObject.getUserInput());
        IPlayer playerToDrop = skaters.get(playerId);
        List<IPlayer> players = team.getPlayers();
        players.remove(playerToDrop);
        leagueObjectModel.getFreeAgents().add(playerToDrop);
    }

    public void dropGoalie(ITeam team, ILeagueObjectModel leagueObjectModel) {
        ioObject.printMessage("Enter ID of goalie to drop");
        ArrayList<IPlayer> goalie = new ArrayList<>();
        for (IPlayer player : team.getPlayers()) {
            String position = player.getPosition();
            if (position.equals("goalie")) {
                goalie.add(player);
            }
        }
        listDisplay.showList(goalie);
        int playerId = Integer.parseInt(ioObject.getUserInput());
        IPlayer playerToDrop = goalie.get(playerId);
        List<IPlayer> players = team.getPlayers();
        players.remove(playerToDrop);
        List<IPlayer> freeAgents = leagueObjectModel.getFreeAgents();
        freeAgents.add(playerToDrop);
    }

    public void addGoalie(ITeam team, ILeagueObjectModel leagueObjectModel) {
        ioObject.printMessage("Enter ID of goalie to add");
        ArrayList<IPlayer> goalie = new ArrayList<>();
        for (IPlayer player : leagueObjectModel.getFreeAgents()) {
            String position = player.getPosition();
            if (position.equals("goalie")) {
                goalie.add(player);
            }
        }
        listDisplay.showList(goalie);
        int playerId = Integer.parseInt(ioObject.getUserInput());
        IPlayer playerToDrop = goalie.get(playerId);
        List<IPlayer> players = team.getPlayers();
        players.add(playerToDrop);
        List<IPlayer> freeAgents = leagueObjectModel.getFreeAgents();
        freeAgents.remove(playerToDrop);
    }

    public void addSkater(ITeam team, ILeagueObjectModel leagueObjectModel) {
        ioObject.printMessage("Enter ID of Skater to add");
        ArrayList<IPlayer> skater = new ArrayList<>();
        for (IPlayer player : leagueObjectModel.getFreeAgents()) {
            String position = player.getPosition();
            if (position.equals("forward") || position.equals("defense")) {
                skater.add(player);
            }
        }
        listDisplay.showList(skater);
        int playerId = Integer.parseInt(ioObject.getUserInput());
        IPlayer playerToDrop = skater.get(playerId);
        List<IPlayer> players = team.getPlayers();
        players.add(playerToDrop);
        List<IPlayer> freeAgents = leagueObjectModel.getFreeAgents();
        freeAgents.remove(playerToDrop);
    }
}