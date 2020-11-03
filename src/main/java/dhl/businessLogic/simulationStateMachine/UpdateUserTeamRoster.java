package dhl.businessLogic.simulationStateMachine;

import dhl.InputOutput.UI.IUserInputOutput;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.Interface.IUpdateUserTeamRoster;

import java.util.ArrayList;
import java.util.List;

public class UpdateUserTeamRoster implements IUpdateUserTeamRoster {

    IUserInputOutput ioObject;

    public UpdateUserTeamRoster(IUserInputOutput ioObject) {
        this.ioObject = ioObject;
    }

    @Override
    public void dropSkater(ITeam team, ILeagueObjectModel leagueObjectModel) {
        ioObject.printMessage("Enter ID of Skater to drop");
        ArrayList<IPlayer> skaters = new ArrayList<>();
        for (IPlayer player : team.getPlayers()) {
            String position = player.getPosition();
            if (position.equals("forward") || position.equals("defense")) {
                skaters.add(player);
            }
        }
        displayPlayerList(skaters);
        int playerId = Integer.parseInt(ioObject.getUserInput());
        IPlayer playerToDrop = skaters.get(playerId);
        List<IPlayer> players = team.getPlayers();
        players.remove(playerToDrop);
        leagueObjectModel.getFreeAgents().add(playerToDrop);
    }

    @Override
    public void dropGoalie(ITeam team, ILeagueObjectModel leagueObjectModel) {
        ioObject.printMessage("Enter ID of goalie to drop");
        ArrayList<IPlayer> goalie = new ArrayList<>();
        for (IPlayer player : team.getPlayers()) {
            String position = player.getPosition();
            if (position.equals("goalie")) {
                goalie.add(player);
            }
        }
        displayPlayerList(goalie);
        int playerId = Integer.parseInt(ioObject.getUserInput());
        IPlayer playerToDrop = goalie.get(playerId);
        List<IPlayer> players = team.getPlayers();
        players.remove(playerToDrop);
        List<IPlayer> freeAgents = leagueObjectModel.getFreeAgents();
        freeAgents.add(playerToDrop);
    }

    @Override
    public void addGoalie(ITeam team, ILeagueObjectModel leagueObjectModel) {
        ioObject.printMessage("Enter ID of goalie to add");
        ArrayList<IPlayer> goalie = new ArrayList<>();
        for (IPlayer player : leagueObjectModel.getFreeAgents()) {
            String position = player.getPosition();
            if (position.equals("goalie")) {
                goalie.add(player);
            }
        }
        displayPlayerList(goalie);
        int playerId = Integer.parseInt(ioObject.getUserInput());
        IPlayer playerToDrop = goalie.get(playerId);
        List<IPlayer> players = team.getPlayers();
        players.add(playerToDrop);
        List<IPlayer> freeAgents = leagueObjectModel.getFreeAgents();
        freeAgents.remove(playerToDrop);
    }

    @Override
    public void addSkater(ITeam team, ILeagueObjectModel leagueObjectModel) {
        ioObject.printMessage("Enter ID of Skater to add");
        ArrayList<IPlayer> skater = new ArrayList<>();
        for (IPlayer player : leagueObjectModel.getFreeAgents()) {
            String position = player.getPosition();
            if (position.equals("forward") || position.equals("defense")) {
                skater.add(player);
            }
        }
        displayPlayerList(skater);
        int playerId = Integer.parseInt(ioObject.getUserInput());
        IPlayer playerToDrop = skater.get(playerId);
        List<IPlayer> players = team.getPlayers();
        players.add(playerToDrop);
        List<IPlayer> freeAgents = leagueObjectModel.getFreeAgents();
        freeAgents.remove(playerToDrop);
    }

    public void displayPlayerList(ArrayList<IPlayer> playerArrayList) {
        String freeAgentListHeader = String.format("%10s %20s %20s %10s %10s %10s %10s %10s %10s", "ID", "Name", "Position", "Age", "Checking", "Saving", "Shooting", "Skating", "Strength");
        ioObject.printMessage(freeAgentListHeader);
        int i = 0;
        for (IPlayer player : playerArrayList) {
            double playerStrength = player.getPlayerStrength();
            String formattedFreeAgentList = String.format("%10s %20s %20s %10d %10d %10d %10d %10d %10s", Integer.toString(i), player.getPlayerName(), player.getPosition(), player.getPlayerStats().getAge(), player.getPlayerStats().getChecking(), player.getPlayerStats().getSaving(), player.getPlayerStats().getShooting(), player.getPlayerStats().getSkating(), Double.toString(playerStrength));
            ioObject.printMessage(formattedFreeAgentList);
            i = i + 1;
        }


    }
}