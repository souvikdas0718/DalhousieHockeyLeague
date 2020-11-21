package dhl.businessLogic.trade;

import dhl.businessLogic.leagueModel.PlayerPosition;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.trade.interfaces.ITradeOffer;
import dhl.businessLogic.trade.interfaces.ITradeType;

import java.util.ArrayList;
import java.util.List;

public class AiAiTrade implements ITradeType {

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
        int totalSkaters = 0;
        int totalGoalies = 0;
        ArrayList<IPlayer> players = (ArrayList<IPlayer>) team.getPlayers();

        for (IPlayer player : players) {
            String position = player.getPosition();
            if (position.equals(PlayerPosition.FORWARD.toString()) || position.equals(PlayerPosition.DEFENSE.toString())) {
                totalSkaters = totalSkaters + 1;
            }
            if (position.equals(PlayerPosition.GOALIE.toString())) {
                totalGoalies = totalGoalies + 1;
            }
        }
        if (totalSkaters < 18 || totalSkaters > 18) {
            updateSkaters(totalSkaters, team, leagueObjectModel);
        }
        if (totalGoalies < 2 || totalGoalies > 2) {
            updateGoalies(totalGoalies, team, leagueObjectModel);
        }
    }

    public void updateSkaters(int totalSkaters, ITeam team, ILeagueObjectModel leagueObjectModel) throws Exception {
        if (totalSkaters < 18) {
            while (totalSkaters < 18) {
                IPlayer skater = findBestSkater(leagueObjectModel.getFreeAgents());
                team.getPlayers().add(skater);
                leagueObjectModel.getFreeAgents().remove(skater);
                totalSkaters = totalSkaters + 1;
            }
        } else if (totalSkaters > 18) {
            while (totalSkaters > 18) {
                IPlayer skater = findWeakSkater(team.getPlayers());
                team.getPlayers().remove(skater);
                leagueObjectModel.getFreeAgents().add(skater);
                totalSkaters = totalSkaters - 1;
            }
        }
    }

    public IPlayer findBestSkater(List<IPlayer> playerList) throws Exception {

        IPlayer bestSkater = null;
        double bestPlayerStrength = 0.0;
        for (IPlayer player : playerList) {
            String position = player.getPosition();
            if (position.equals(PlayerPosition.FORWARD.toString()) || position.equals(PlayerPosition.DEFENSE.toString())) {
                if (player.getPlayerStrength() > bestPlayerStrength) {
                    bestSkater = player;
                    bestPlayerStrength = player.getPlayerStrength();
                }
            }
        }
        if (bestSkater == null) {
            throw new Exception("No Skater found in List");
        }
        return bestSkater;
    }

    public IPlayer findWeakSkater(List<IPlayer> playerList) throws Exception {
        IPlayer skater = null;
        double skaterStrength = 1000.0;
        for (IPlayer player : playerList) {
            String position = player.getPosition();
            if (position.equals(PlayerPosition.FORWARD.toString()) || position.equals(PlayerPosition.DEFENSE.toString())) {

                if (player.getPlayerStrength() < skaterStrength) {
                    skater = player;
                    skaterStrength = player.getPlayerStrength();
                }
            }
        }
        if (skater == null) {
            throw new Exception("No Skater found in List");
        }
        return skater;
    }

    public void updateGoalies(int totalGoalies, ITeam team, ILeagueObjectModel leagueObjectModel) throws Exception {
        if (totalGoalies < 2) {
            while (totalGoalies < 2) {
                IPlayer skater = findBestGolie(leagueObjectModel.getFreeAgents());
                team.getPlayers().add(skater);
                leagueObjectModel.getFreeAgents().remove(skater);
                totalGoalies = totalGoalies + 1;
            }
        } else if (totalGoalies > 2) {
            while (totalGoalies > 2) {
                IPlayer skater = findWeakGolie(team.getPlayers());
                team.getPlayers().remove(skater);
                leagueObjectModel.getFreeAgents().add(skater);
                totalGoalies = totalGoalies - 1;
            }
        }
    }

    public IPlayer findBestGolie(List<IPlayer> playerList) throws Exception {

        IPlayer bestPlayer = null;
        double bestPlayerStrength = 0.0;
        for (IPlayer player : playerList) {
            String position = player.getPosition();
            if (position.equals(PlayerPosition.GOALIE.toString())) {
                if (player.getPlayerStrength() > bestPlayerStrength) {
                    bestPlayer = player;
                    bestPlayerStrength = player.getPlayerStrength();
                }
            }
        }
        if (bestPlayer == null) {
            throw new Exception("No Golie found in List");
        }
        return bestPlayer;
    }

    public IPlayer findWeakGolie(List<IPlayer> playerList) throws Exception {
        IPlayer weakplayer = null;
        double playerStrength = 1200.0;
        for (IPlayer player : playerList) {
            String position = player.getPosition();
            if (position.equals(PlayerPosition.GOALIE.toString())) {
                if (player.getPlayerStrength() < playerStrength) {
                    weakplayer = player;
                    playerStrength = player.getPlayerStrength();
                }
            }
        }
        if (weakplayer == null) {
            throw new Exception("No Golie found in List");
        }
        return weakplayer;
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
