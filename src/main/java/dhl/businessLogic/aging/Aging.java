package dhl.businessLogic.aging;

import dhl.businessLogic.aging.interfaceAging.IAging;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.*;

public class Aging implements IAging {
    private static final double LIKELIHOODFORGREATERTHANAVGAGE=80.0;
    private static final double LIKELIHOODFORLESSERTHANAVGAGE=20.0;
    private static final Logger logger = LogManager.getLogger(Aging.class);

    private int averageRetirementAge;
    private int maximumAge;
    private IGameConfig gameConfig;
    private Random randomNumberGenerator;

    public Aging(IGameConfig gameConfig) {
        logger.debug("Aging Constructor created");
        averageRetirementAge = Integer.parseInt(gameConfig.getValueFromOurObject(gameConfig.getAging(), gameConfig.getAverageRetirementAge()));
        maximumAge = Integer.parseInt(gameConfig.getValueFromOurObject(gameConfig.getAging(), gameConfig.getMaximumAge()));
        this.gameConfig= gameConfig;
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

    public void agePlayers(List<IPlayer> players,LocalDate currentDate) {
        logger.debug("Age players on birthday");
        for (IPlayer player : players) {
            IPlayerStatistics playerStatistics = player.getPlayerStats();
            playerStatistics.calculateCurrentAge(currentDate);
            LocalDate dateOfBirth = playerStatistics.getDateOfBirth();
            if(dateOfBirth.getMonth()==currentDate.getMonth() && dateOfBirth.getDayOfMonth() ==currentDate.getDayOfMonth()){
                logger.debug("Checking player decay stats");
                playerStatistics.checkStatDecayChance(gameConfig);
            }
        }
    }

    public Map<String, List<IPlayer>> selectPlayersToRetire(ITeam team) {
        logger.debug("Initiate selection of players to retire");
        Map<String, List<IPlayer>> playersSelectedToRetire = new HashMap<>();
        playersSelectedToRetire.put(team.getTeamName(), retirementAlgorithmBasedOnAge(team.getPlayers()));
        return playersSelectedToRetire;
    }

    public List<IPlayer> selectFreeAgentsToRetire(List<IPlayer> freeAgents) {
        logger.debug("Initiate selection of free agents to retire");
        return retirementAlgorithmBasedOnAge(freeAgents);
    }

    public List<IPlayer> retirementAlgorithmBasedOnAge(List<IPlayer> players) {
        logger.debug("Initiate retirement algorithm");
        logger.info("Retirement algorithm");
        int rangeOfAge = (maximumAge - averageRetirementAge) /3;
        List<IPlayer> retiringPlayers = new ArrayList<>();
        for (IPlayer player : players) {
            IPlayerStatistics playerStatistics = player.getPlayerStats();
            if (playerStatistics.getAge() == maximumAge) {
                logger.debug("Player age is equivalent to maximum");
                retiringPlayers.add(player);
            } else if (playerStatistics.getAge() >= averageRetirementAge) {
                if (checkLikelihoodOfRetirement(LIKELIHOODFORGREATERTHANAVGAGE)) {
                    logger.debug("Player age"+playerStatistics.getAge()+"is greater than average age");
                    retiringPlayers.add(player);
                }
            } else if (playerStatistics.getAge() < averageRetirementAge && playerStatistics.getAge() > averageRetirementAge - rangeOfAge) {
                if (checkLikelihoodOfRetirement(LIKELIHOODFORLESSERTHANAVGAGE)) {
                    logger.debug("Player age"+playerStatistics.getAge()+"is lesser than average age");
                    retiringPlayers.add(player);
                }
            }
        }
        logger.debug("Retiring players"+retiringPlayers.size());
        return retiringPlayers;
    }

    public boolean checkLikelihoodOfRetirement(double likelihood) {
        logger.debug("Check Likelihood Retirement"+likelihood);
        double randomNumber = randomNumberGenerator.nextDouble();
        randomNumber = randomNumber * 100;
        if (randomNumber <= likelihood) {
            return true;
        }
        return false;
    }
}
