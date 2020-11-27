package dhl.businessLogic.simulationStateMachine;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.interfaces.ITeamRosterUpdater;

import java.util.List;

public class AiTeamRosterUpdater implements ITeamRosterUpdater {

    public void dropPlayer(String playerPosition, ITeam team, ILeagueObjectModel league) {
        IPlayer player = findWeakestPlayerInList(playerPosition, team.getPlayers());
        team.getPlayers().remove(player);
        league.getFreeAgents().add(player);
    }

    public void addPlayer(String playerPosition, ITeam team, ILeagueObjectModel league) {
        IPlayer player = findBestPlayerInList(playerPosition, league.getFreeAgents());
        team.getPlayers().add(player);
        league.getFreeAgents().remove(player);
    }

    public IPlayer findWeakestPlayerInList(String neededPosition, List playerList){
        IPlayer weakPlayer = null;
        double skaterStrength = 10000.0;
        for (Object ob : playerList) {
            IPlayer player = (IPlayer) ob;
            String position = player.getPosition();
            if (position.equals(neededPosition)) {
                if (player.getPlayerStrength() < skaterStrength) {
                    weakPlayer = player;
                    skaterStrength = player.getPlayerStrength();
                }
            }
        }
        return weakPlayer;
    }

    public IPlayer findBestPlayerInList(String playerPosition, List playerList) {
        IPlayer bestPlayer = null;
        double bestPlayerStrength = 0.0;
        for (Object ob : playerList) {
            IPlayer player = (IPlayer) ob;
            String position = player.getPosition();
            if ( position.equals(playerPosition) ) {
                if (player.getPlayerStrength() > bestPlayerStrength) {
                    bestPlayer = player;
                    bestPlayerStrength = player.getPlayerStrength();
                }
            }
        }
        return bestPlayer;
    }
}
