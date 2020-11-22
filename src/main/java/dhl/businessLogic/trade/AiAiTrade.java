package dhl.businessLogic.trade;

import dhl.businessLogic.leagueModel.PlayerPosition;
import dhl.inputOutput.importJson.interfaces.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.trade.interfaces.ITradeOffer;
import dhl.businessLogic.trade.interfaces.ITradeType;
import java.util.ArrayList;
import java.util.List;

public class AiAiTrade implements ITradeType {


    private static final int TOTAL_GOALIES = 4;
    private static final int TOTAL_FORWARDS = 16;
    private static final int TOTAL_DEFENSE = 10;

    ITradeOffer tradeOffer;
    IGameConfig gameConfig;

    public AiAiTrade(ITradeOffer tradeOffer, IGameConfig gameConfig) {
        this.tradeOffer = tradeOffer;
        this.gameConfig = gameConfig;
    }

    public boolean isTradeAccepted() {
        double configRandomAcceptanceChance = Double.parseDouble(gameConfig.getValueFromOurObject(gameConfig.getTrading(), gameConfig.getRandomAcceptanceChance()));
        double randomValue = Math.random();
        if (isTradeGoodForReceivingTeam(tradeOffer)) {
            return true;
        } else if (randomValue > configRandomAcceptanceChance) {
            return true;
        }
        return false;
    }

    public void validateTeamRosterAfterTrade(ITeam team, ILeagueObjectModel leagueObjectModel) throws Exception {
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

    public void updatePlayers(int currentCount, String playerPosition, int validCount, ITeam team, ILeagueObjectModel leagueObjectModel) throws Exception {
        if (currentCount < validCount){
            while (currentCount < validCount){
                IPlayer player = findBestPlayerInList(playerPosition, leagueObjectModel.getFreeAgents());
                team.getPlayers().add(player);
                leagueObjectModel.getFreeAgents().remove(player);
                currentCount = currentCount + 1;
            }
        }else if (currentCount > validCount) {
            while (currentCount > validCount) {
                IPlayer player = findWeakestPlayerInList(playerPosition, team.getPlayers());
                team.getPlayers().remove(player);
                leagueObjectModel.getFreeAgents().add(player);
                currentCount = currentCount - 1;
            }
        }
    }

    public IPlayer findWeakestPlayerInList(String neededPosition, List playerList) throws Exception {
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
        if (weakPlayer == null) {
            throw new Exception("No" + neededPosition +" found in List");
        }
        return weakPlayer;
    }

    public IPlayer findBestPlayerInList(String playerPosition, List playerList) throws Exception {
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
        if (bestPlayer == null) {
            throw new Exception("No "+ playerPosition +" found in List");
        }
        return bestPlayer;
    }

    public boolean isTradeGoodForReceivingTeam(ITradeOffer tradeOffer) {
        if (getPlayerCombinedStrength(tradeOffer.getOfferingPlayers()) > getPlayerCombinedStrength(tradeOffer.getPlayersWantedInReturn())) {
            return true;
        }
        return false;
    }

    public double getPlayerCombinedStrength(ArrayList<IPlayer> players) {
        double totalStrength = 0;
        for (int i = 0; i < players.size(); i++) {
            totalStrength += players.get(i).getPlayerStrength();
        }
        return totalStrength;
    }

}
