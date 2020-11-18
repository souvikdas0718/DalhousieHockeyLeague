package dhl.businessLogic.aging;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.businessLogic.aging.interfaceAging.IInjury;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

import java.util.List;

public class Injury implements IInjury {

    public void checkTeamInjury(IGameConfig gameConfig, ITeam team) {
        for (IPlayer player : team.getPlayers()) {
            checkIfPlayerInjured(gameConfig, player,team);
        }
    }

    public boolean checkIfPlayerInjured(IGameConfig gameConfig, IPlayer player,ITeam team) {
        double randomInjuryChance = Double.parseDouble(gameConfig.getValueFromOurObject(gameConfig.getInjuries(), gameConfig.getRandomInjuryChance())) * 100;
        int injuryDaysLow = Integer.parseInt(gameConfig.getValueFromOurObject(gameConfig.getInjuries(), gameConfig.getInjuryDaysLow()));
        int injuryDaysHigh = Integer.parseInt(gameConfig.getValueFromOurObject(gameConfig.getInjuries(), gameConfig.getInjuryDaysHigh()));

        double ramdomNumber = Math.random();
        ramdomNumber = ramdomNumber * 100;
        if (ramdomNumber <= randomInjuryChance && player.getPlayerInjuredDays() > 0) {
            int numberOfDaysInjured = (int) (Math.random() * (injuryDaysHigh - injuryDaysLow + 1) + injuryDaysLow);
            player.setPlayerInjuredDays(numberOfDaysInjured);
            swapInjuredPlayer(player,team);
            return true;
        }
        return false;
    }

    public void healInjuredPlayersInTeam(IPlayer player,ITeam team) {
        int injuryDays=player.getPlayerInjuredDays();
        healInjuredPlayers(player);
        if(injuryDays==0){
            swapRecoveredPlayer(player,team);
        }
    }
    public void healInjuredPlayers(IPlayer player) {
        if (player.getPlayerInjuredDays() >= 1) {
            player.setPlayerInjuredDays(player.getPlayerInjuredDays() - 1);
        } else {
            player.setPlayerInjuredDays(-1);
        }
    }

    public void swapInjuredPlayer(IPlayer player,ITeam team){
        List<IPlayer> reservePlayers = team.getInactiveRoster();
        List<IPlayer> reservePlayersInSamePosition=team.filterPlayersInTeam(player.getPosition(),reservePlayers);
        if(reservePlayersInSamePosition.size()>0){
            player.setActive(false);
            team.sortPlayersInTeamByStrength(reservePlayersInSamePosition);
            IPlayer replacementPlayer = reservePlayersInSamePosition.get(0);
            replacementPlayer.setActive(true);
        }
    }

    public void swapRecoveredPlayer(IPlayer player,ITeam team){
        List<IPlayer> activePlayers = team.getActiveRoster();
        List<IPlayer> activePlayersInSamePosition=team.filterPlayersInTeam(player.getPosition(),activePlayers);
        if(activePlayersInSamePosition.size()>0){
            for(IPlayer activePlayer:activePlayersInSamePosition){
                if(player.getPlayerStrength()>activePlayer.getPlayerStrength()){
                    player.setActive(true);
                    activePlayer.setActive(false);
                }
            }
        }
    }
}
