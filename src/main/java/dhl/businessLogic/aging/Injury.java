package dhl.businessLogic.aging;

import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Injury implements IInjury {
    private static final Logger logger = LogManager.getLogger(Injury.class);
    private static final int DEFAULTINJURYDAYS = -1;
    private static final int BESTPLAYERINDEX = 0;
    private static final int PLAYERHEALED = 0;

    public void checkTeamInjury(IGameConfig gameConfig, ITeam team) {
        logger.debug("Check Injury for team" + team.getTeamName());
        for (IPlayer player : team.getPlayers()) {
            checkIfPlayerInjured(gameConfig, player, team);
        }
    }

    public boolean checkIfPlayerInjured(IGameConfig gameConfig, IPlayer player, ITeam team) {
        logger.debug("Check Player if injured" + player.getPlayerName());
        double randomInjuryChance = Double.parseDouble(gameConfig.getValueFromOurObject(gameConfig.getInjuries(), gameConfig.getRandomInjuryChance())) * 100;
        int injuryDaysLow = Integer.parseInt(gameConfig.getValueFromOurObject(gameConfig.getInjuries(), gameConfig.getInjuryDaysLow()));
        int injuryDaysHigh = Integer.parseInt(gameConfig.getValueFromOurObject(gameConfig.getInjuries(), gameConfig.getInjuryDaysHigh()));

        double ramdomNumber = Math.random();
        ramdomNumber = ramdomNumber * 100;
        if (ramdomNumber <= randomInjuryChance && player.getPlayerInjuredDays() > 0) {
            int numberOfDaysInjured = (int) (Math.random() * (injuryDaysHigh - injuryDaysLow + 1) + injuryDaysLow);
            player.setPlayerInjuredDays(numberOfDaysInjured);
            swapInjuredPlayer(player, team);
            return true;
        }
        return false;
    }

    public void healInjuredPlayersInTeam(IPlayer player, ITeam team) {
        logger.debug("Healing injured players in team" + team.getTeamName());
        int injuryDays = player.getPlayerInjuredDays();
        healInjuredPlayers(player);
        if (injuryDays == PLAYERHEALED) {
            swapRecoveredPlayer(player, team);
        }
    }

    public void healInjuredPlayers(IPlayer player) {
        logger.debug("Healing injured if injured" + player.getPlayerName());
        if (player.getPlayerInjuredDays() >= 1) {
            player.setPlayerInjuredDays(player.getPlayerInjuredDays() - 1);
        } else {
            player.setPlayerInjuredDays(DEFAULTINJURYDAYS);
        }
    }

    public void swapInjuredPlayer(IPlayer player, ITeam team) {
        logger.debug("Swap injured player" + player.getPlayerName());
        List<IPlayer> reservePlayers = team.getInactiveRoster();
        List<IPlayer> reservePlayersInSamePosition = team.filterPlayersInTeam(player.getPosition(), reservePlayers);
        if (reservePlayersInSamePosition.size() > 0) {
            player.setActive(false);
            team.sortPlayersInTeamByStrength(reservePlayersInSamePosition);
            IPlayer replacementPlayer = reservePlayersInSamePosition.get(BESTPLAYERINDEX);
            replacementPlayer.setActive(true);
        }
    }

    public void swapRecoveredPlayer(IPlayer player, ITeam team) {
        logger.debug("Swap recovered player" + player.getPlayerName());
        List<IPlayer> activePlayers = team.getActiveRoster();
        List<IPlayer> activePlayersInSamePosition = team.filterPlayersInTeam(player.getPosition(), activePlayers);
        if (activePlayersInSamePosition.size() > 0) {
            for (IPlayer activePlayer : activePlayersInSamePosition) {
                if (player.getPlayerStrength() > activePlayer.getPlayerStrength()) {
                    player.setActive(true);
                    activePlayer.setActive(false);
                }
            }
        }
    }
}
