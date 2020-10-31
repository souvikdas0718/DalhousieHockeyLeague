package dhl.leagueModel;

import dhl.importJson.Interface.IGameConfig;
import dhl.leagueModel.interfaceModel.IInjurySystem;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;

import java.util.HashMap;

public class InjurySystem implements IInjurySystem {

    public void checkTeamInjury(IGameConfig  gameConfig, ITeam team){
        for(IPlayer player: team.getPlayers()){
                checkIfPlayerInjured(gameConfig,player) ;
        }
    }

    public boolean checkIfPlayerInjured(IGameConfig gameConfig, IPlayer player) {
        HashMap injuryConfig = gameConfig.getHashMap("injuries");
        double randomInjuryChance = (double) injuryConfig.get("randomInjuryChance") * 100;
        int injuryDaysLow = (int) (long) injuryConfig.get("injuryDaysLow");
        int injuryDaysHigh = (int) (long) injuryConfig.get("injuryDaysHigh");

        double ramdomNumber = Math.random();
        ramdomNumber = ramdomNumber * 100;
        if (ramdomNumber <= randomInjuryChance && player.getPlayerInjuredDays() > 0) {
            int numberOfDaysInjured = (int) (Math.random() * (injuryDaysHigh - injuryDaysLow + 1) + injuryDaysLow);
            player.setPlayerInjuredDays(numberOfDaysInjured);
            return true;
        }
        return false;
    }

    public void healInjuredPlayers(IPlayer player){
        if(player.getPlayerInjuredDays()>=1){
            player.setPlayerInjuredDays(player.getPlayerInjuredDays()-1);
        }
        else{
            player.setPlayerInjuredDays(-1);
        }
    }
}
