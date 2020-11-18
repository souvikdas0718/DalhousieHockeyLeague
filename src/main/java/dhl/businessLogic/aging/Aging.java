package dhl.businessLogic.aging;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.businessLogic.aging.interfaceAging.IAging;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

import java.util.*;

public class Aging implements IAging {
    private static final double LIKELIHOODFORGREATERTHANAVGAGE=80.0;
    private static final double LIKELIHOODFORLESSERTHANAVGAGE=20.0;

    private int averageRetirementAge;
    private int maximumAge;
    private Random randomNumberGenerator;

    public Aging(IGameConfig gameConfig) {
        averageRetirementAge = Integer.parseInt(gameConfig.getValueFromOurObject(gameConfig.getAging(), gameConfig.getAverageRetirementAge()));
        maximumAge = Integer.parseInt(gameConfig.getValueFromOurObject(gameConfig.getAging(), gameConfig.getMaximumAge()));
        setRandomNumberGenerator();
    }

    public int getAverageRetirementAge() {
        return averageRetirementAge;
    }

    public int getMaximumAge() {
        return maximumAge;
    }

    public Random getRandomGenerator(){
        return randomNumberGenerator;
    }

    public void setRandomNumberGenerator() {
        this.randomNumberGenerator = new Random();
    }

    public void ageAllPlayers(List<IPlayer> players) {
        for (IPlayer player : players) {
            agePlayer(player.getPlayerStats());
        }
    }

    public void agePlayer(IPlayerStatistics playerStatistics) {
        playerStatistics.setAge(playerStatistics.getAge() + 1);

    }

    public Map<String, List<IPlayer>> selectPlayersToRetire(ITeam team) {
        Map<String, List<IPlayer>> playersSelectedToRetire = new HashMap<>();
        playersSelectedToRetire.put(team.getTeamName(), retirementAlgorithmBasedOnAge(team.getPlayers()));
        return playersSelectedToRetire;
    }

    public List<IPlayer> selectFreeAgentsToRetire(List<IPlayer> freeAgents) {
        return retirementAlgorithmBasedOnAge(freeAgents);
    }

    public List<IPlayer> retirementAlgorithmBasedOnAge(List<IPlayer> players) {
        int rangeOfAge = (maximumAge - averageRetirementAge) /3;
        List<IPlayer> retiringPlayers = new ArrayList<>();
        for (IPlayer player : players) {
            IPlayerStatistics playerStatistics = player.getPlayerStats();
            if (playerStatistics.getAge() == maximumAge) {
                retiringPlayers.add(player);
            } else if (playerStatistics.getAge() >= averageRetirementAge) {
                if (checkLikelihoodOfRetirement(LIKELIHOODFORGREATERTHANAVGAGE)) {
                    retiringPlayers.add(player);
                }
            } else if (playerStatistics.getAge() < averageRetirementAge && playerStatistics.getAge() > averageRetirementAge - rangeOfAge) {
                if (checkLikelihoodOfRetirement(LIKELIHOODFORLESSERTHANAVGAGE)) {
                    retiringPlayers.add(player);
                }
            }
        }
        return retiringPlayers;
    }

    public boolean checkLikelihoodOfRetirement(double likelihood) {
        double randomNumber = randomNumberGenerator.nextDouble();
        randomNumber = randomNumber * 100;
        if (randomNumber <= likelihood) {
            return true;
        }
        return false;
    }

}
