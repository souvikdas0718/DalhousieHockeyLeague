package dhl.businessLogic.teamRosterUpdater;

import dhl.businessLogic.leagueModel.PlayerPosition;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.teamRosterUpdater.interfaces.ITeamRosterUpdater;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

public class AiTeamRosterUpdater implements ITeamRosterUpdater {

    // TODO: 30-11-2020 get this from team
    private static final int TOTAL_GOALIES = 4;
    private static final int TOTAL_FORWARDS = 16;
    private static final int TOTAL_DEFENSE = 10;

    private static final Logger logger = LogManager.getLogger(AiTeamRosterUpdater.class);

    public void validateTeamRoster(ITeam team, ILeagueObjectModel leagueObjectModel) {
        logger.info("Validating team: "+ team.getTeamName());
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
        if (totalForwards < TOTAL_FORWARDS || totalForwards > TOTAL_FORWARDS) {
            updatePlayers(totalForwards, PlayerPosition.FORWARD.toString(), TOTAL_FORWARDS, team, leagueObjectModel);
        }
        if (totalDefense < TOTAL_DEFENSE || totalDefense > TOTAL_DEFENSE) {
            updatePlayers(totalDefense, PlayerPosition.DEFENSE.toString(), TOTAL_DEFENSE, team, leagueObjectModel);
        }
        if (totalGoalies < TOTAL_GOALIES || totalGoalies > TOTAL_GOALIES) {
            updatePlayers(totalGoalies, PlayerPosition.GOALIE.toString(), TOTAL_GOALIES, team, leagueObjectModel);
        }
    }

    public void updatePlayers(int currentCount, String playerPosition, int validCount, ITeam team, ILeagueObjectModel leagueObjectModel) {
        logger.info("Updating team: "+ team.getTeamName());
        if (currentCount < validCount){
            while (currentCount < validCount){
                logger.info("Adding Players from team: "+ team.getTeamName());
                addPlayer(playerPosition, team, leagueObjectModel);
                currentCount = currentCount + 1;
            }
        }
        else if (currentCount > validCount) {
            while (currentCount > validCount) {
                logger.info("Droping Players from team: "+ team.getTeamName());
                dropPlayer(playerPosition, team, leagueObjectModel);
                currentCount = currentCount - 1;
            }
        }
    }

    public void dropPlayer(String playerPosition, ITeam team, ILeagueObjectModel league) {
        IPlayer player = findWeakestPlayerInList(playerPosition, team.getPlayers());
        logger.info("Player "+ player.getPlayerName()+ " Dropped from team: "+ team.getTeamName());
        team.getPlayers().remove(player);
        league.getFreeAgents().add(player);
    }

    public void addPlayer(String playerPosition, ITeam team, ILeagueObjectModel league) {
        IPlayer player = findBestPlayerInList(playerPosition, league.getFreeAgents());
        logger.info("Player added to team ");
        team.getPlayers().add(player);
        league.getFreeAgents().remove(player);
    }

    public IPlayer findWeakestPlayerInList(String neededPosition, List playerList){
        logger.debug("Finding weakest player");
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
        logger.debug("Finding Strongest player");
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
