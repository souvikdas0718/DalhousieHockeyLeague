package dhl.businessLogic.gameSimulation;

import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class GameSimulationAlgorithm implements IGameSimulationAlgorithm {
    final String typeGoalie = "goalie";
    final String typeForward = "forward";
    final String typeDefense = "defense";
    final String statShooting = "shooting";
    final String statChecking = "checking";
    final String statSkating = "skating";
    final String teamOneSelected = "One";
    final String teamTwoSelected = "Two";
    final Integer oneShotInAShift = 1;
    final Integer twoShotsInAShift = 2;
    final Integer totalShifts = 40;
    final Integer winningTeamOne = 1;
    final Integer winningTeamTwo = 2;
    final Integer forwardTeamOneOldPlayer = 1;
    final Integer defenseTeamOneOldPlayer = 2;
    final Integer forwardTeamTwoOldPlayer = 3;
    final Integer defenseTeamTwoOldPlayer = 4;
    final Integer forwardPlayerCount = 3;
    final Integer defensePlayerCount = 2;
    final Integer comparePenalty = 1;
    final Integer penaltyForShifts = 1;
    final Integer totalForwardPlayers = 12;
    final Integer totalDefensePlayers = 6;
    final Integer defaultExcludeNumber = 1;

    Integer shotsA = 0;
    Integer shotsB = 0;
    Integer goalsA = 0;
    Integer goalsB = 0;
    Integer savesA = 0;
    Integer savesB = 0;
    Integer penaltyA = 0;
    Integer penaltyB = 0;

    Integer countToCheckOldPlayers = 0;

    List<Integer> forwardTeamOne = new ArrayList<>();
    List<Integer> forwardTeamTwo = new ArrayList<>();
    List<Integer> defenceTeamOne = new ArrayList<>();
    List<Integer> defenceTeamTwo = new ArrayList<>();

    Integer gRandomNumber = 0;
    Integer penalty = 0;
    List<Integer> allForwardPlayersTeamOneMap = new ArrayList<>();
    List<Integer> allDefensePlayersTeamOneMap = new ArrayList<>();
    List<Integer> allForwardPlayersTeamTwoMap = new ArrayList<>();
    List<Integer> allDefensePlayersTeamTwoMap = new ArrayList<>();
    String teamSelected = "One";

    private static final Logger logger = LogManager.getLogger(GameSimulationAlgorithm.class);

    public Integer getGoalsA() {
        return goalsA;
    }

    public Integer getSavesB() {
        return savesB;
    }

    public Integer getGoalsB() {
        return goalsB;
    }

    public Integer getSavesA() {
        return savesA;
    }

    public Integer getShotsA() {
        return shotsA;
    }

    public Integer getShotsB() {
        return shotsB;
    }

    public Integer getgRandomNumber() {
        return gRandomNumber;
    }

    public HashMap<String, Integer> getResultOfGame(ITeam teamOne, ITeam teamTwo) {
        logger.debug("Game Simulation Starting for" + teamOne.getTeamName() + " and " + teamTwo.getTeamName());

        List<IPlayer> listPlayerTeamOne = teamOne.getActiveRoster();
        List<IPlayer> listPlayerTeamTwo = teamTwo.getActiveRoster();

        for (int i = 0; i < totalShifts; i++) {
            int shotsInAShift = oneShotInAShift;

            if (i % 2 == 0) {
                shotsInAShift = twoShotsInAShift;
            }

            for (int j = 0; j < shotsInAShift; j++) {
                createTeam(listPlayerTeamOne, listPlayerTeamTwo);
            }
        }

        Integer winner = checkWinner(goalsA, goalsB);

        HashMap<String, Integer> mapResult = new HashMap<>();
        mapResult.put("Shots", shotsA + shotsB);
        mapResult.put("Goals", goalsA + goalsB);
        mapResult.put("Penalties", penaltyA + penaltyB);
        mapResult.put("Saves", savesA + savesB);
        mapResult.put("Winner", winner);

        logger.info("Game simulated successfully");
        return mapResult;
    }

    public Integer checkWinner(Integer goalsA, Integer goalsB) {
        Integer winner = winningTeamOne;
        if (goalsB > goalsA) {
            winner = winningTeamTwo;
        }
        return winner;
    }

    public void createTeam(List<IPlayer> listPlayerTeamOne, List<IPlayer> listPlayerTeamTwo) {
        teamSelected = teamOneSelected;
        List<IPlayer> listForwardPlayerA = getPlayers(typeForward, listPlayerTeamOne, forwardTeamOne);
        List<IPlayer> listDefensePlayerA = getPlayers(typeDefense, listPlayerTeamOne, defenceTeamOne);
        Optional<IPlayer> listgoaliePlayerA = listPlayerTeamOne.stream().filter(c -> c.getPosition() == typeGoalie).findAny();

        teamSelected = teamTwoSelected;
        List<IPlayer> listForwardPlayerB = getPlayers(typeForward, listPlayerTeamTwo, forwardTeamTwo);
        List<IPlayer> listDefensePlayerB = getPlayers(typeDefense, listPlayerTeamTwo, defenceTeamTwo);
        Optional<IPlayer> listgoaliePlayerB = listPlayerTeamTwo.stream().filter(c -> c.getPosition() == typeGoalie).findAny();

        Integer maxSkatingFwdPlayerTeamOne = getMaxStat(listForwardPlayerA, "skating");
        Integer maxSkatingFwdPlayerTeamTwo = getMaxStat(listForwardPlayerB, "skating");
        Integer maxCheckingDefPlayerTeamOne = getMaxStat(listDefensePlayerA, "checking");
        Integer maxCheckingDefPlayerTeamTwo = getMaxStat(listDefensePlayerB, "checking");

        HashMap<String, Integer> mapStats = new HashMap<>();
        mapStats.put("TeamOneFSkating", maxSkatingFwdPlayerTeamOne);
        mapStats.put("TeamTwoFSkating", maxSkatingFwdPlayerTeamTwo);
        mapStats.put("TeamOneFChecking", maxSkatingFwdPlayerTeamOne);
        mapStats.put("TeamTwoDChecking", maxCheckingDefPlayerTeamTwo);
        mapStats.put("TeamOneFSaving", listForwardPlayerA.get(1).getPlayerStats().getSaving());
        mapStats.put("TeamTwoGSaving", listgoaliePlayerB.get().getPlayerStats().getSaving());

        mapStats.put("TeamTwoFChecking", maxSkatingFwdPlayerTeamTwo);
        mapStats.put("TeamOneDChecking", maxCheckingDefPlayerTeamOne);
        mapStats.put("TeamTwoFSaving", listForwardPlayerB.get(2).getPlayerStats().getSaving());
        mapStats.put("TeamOneGSaving", listgoaliePlayerA.get().getPlayerStats().getSaving());

        incrementShotsGoalsSavesPenalties(mapStats);
    }

    public void incrementShotsGoalsSavesPenalties(HashMap<String, Integer> mapStats) {
        if (mapStats.get("TeamOneFSkating") > mapStats.get("TeamTwoFSkating")) {
            shotsA = shotsA + 1;
            if (mapStats.get("TeamOneFChecking") > mapStats.get("TeamTwoDChecking")) {
                if (mapStats.get("TeamOneFSaving") > mapStats.get("TeamTwoGSaving")) {
                    goalsA = goalsA + 1;
                } else {
                    savesB = savesB + 1;
                }
            } else {
                savesB = savesB + 1;
                penaltyA = penaltyA + 1;
            }
        } else {
            shotsB = shotsB + 1;
            if (mapStats.get("TeamTwoFChecking") > mapStats.get("TeamOneDChecking")) {

                if (mapStats.get("TeamTwoFSaving") > mapStats.get("TeamOneGSaving")) {
                    goalsB = goalsB + 1;
                } else {
                    savesA = savesA + 1;
                }
            } else {
                savesA = savesA + 1;
                penaltyB = penaltyB + 1;
            }
        }
    }

    public Integer getMaxStat(List<IPlayer> statsList, String stat) {
        Integer max = 0;

        for (int i = 0; i < statsList.size(); i++) {
            if (stat == statShooting) {
                max = max + statsList.get(i).getPlayerStats().getShooting();
            } else if (stat == statChecking) {
                max = max + statsList.get(i).getPlayerStats().getChecking();
            } else if (stat == statSkating) {
                max = max + statsList.get(i).getPlayerStats().getSkating();
            }
        }
        return max;
    }

    public List<IPlayer> getPlayers(String type, List<IPlayer> listPlayer, List<Integer> oldPlayer) {
        List<IPlayer> listForwardPlayer = listPlayer.stream().filter(c -> c.getPosition() == type).collect(Collectors.toList());

        HashMap<Integer, IPlayer> mapPlayer = new HashMap<>();
        for (int i = 0; i < listForwardPlayer.size(); i++) {
            mapPlayer.put(i + 1, listForwardPlayer.get(i));
        }

        Integer playerCount = getPlayerCount(type, penalty);

        List<Integer> listOldPlayers = new ArrayList<>();
        listOldPlayers.addAll(oldPlayer);
        List<Integer> arrNewRandomNumbers = getRandomNumbers(listOldPlayers, playerCount, type);

        List<IPlayer> listFilteredPlayer = new ArrayList<>();

        for (int i = 0; i < arrNewRandomNumbers.size(); i++) {
            listFilteredPlayer.add(mapPlayer.get(arrNewRandomNumbers.get(i)));
        }

        countToCheckOldPlayers = countToCheckOldPlayers + 1;
        if (countToCheckOldPlayers == forwardTeamOneOldPlayer) {
            forwardTeamOne = arrNewRandomNumbers;
        } else if (countToCheckOldPlayers == defenseTeamOneOldPlayer) {
            defenceTeamOne = arrNewRandomNumbers;
        } else if (countToCheckOldPlayers == forwardTeamTwoOldPlayer) {
            forwardTeamTwo = arrNewRandomNumbers;
        } else if (countToCheckOldPlayers == defenseTeamTwoOldPlayer) {
            defenceTeamTwo = arrNewRandomNumbers;
        }

        return listFilteredPlayer;
    }

    public Integer getPlayerCount(String type, Integer penalty) {
        Integer playerCount = 0;
        if (type == typeForward) {
            playerCount = forwardPlayerCount;
        } else if (type == typeDefense) {
            if (penalty == comparePenalty) {
                playerCount = penaltyForShifts;
                penalty = 0;
            } else {
                playerCount = defensePlayerCount;
            }
        }
        return playerCount;
    }

    public List<Integer> getRandomNumbers(List<Integer> arrOldRandomNumbers, int playerCount, String type) {
        List<Integer> arrNewRandomNumbers = new ArrayList<Integer>();
        int randomNumber = 0;

        for (int i = 0; i < playerCount; i++) {
            Integer endNumber = 0;

            if (playerCount == forwardPlayerCount) {
                endNumber = totalForwardPlayers;
            } else {
                endNumber = totalDefensePlayers;
            }

            generateRandomNumber(endNumber, arrOldRandomNumbers);
            randomNumber = gRandomNumber;
            arrNewRandomNumbers.add(randomNumber);
            arrOldRandomNumbers.add(randomNumber);

            if (teamSelected == teamOneSelected) {
                if (type.toLowerCase() == typeForward) {
                    allForwardPlayersTeamOneMap.add(randomNumber);
                } else {
                    allDefensePlayersTeamOneMap.add(randomNumber);
                }
            } else {
                if (type.toLowerCase() == typeForward) {
                    allForwardPlayersTeamTwoMap.add(randomNumber);
                } else {
                    allDefensePlayersTeamTwoMap.add(randomNumber);
                }
            }
        }
        return arrNewRandomNumbers;
    }

    public void generateRandomNumber(Integer end, List<Integer> exclude) {
        if (exclude.size() == 0) {
            exclude.add(defaultExcludeNumber);
        }

        Random rnd = new Random();
        gRandomNumber = rnd.nextInt(end);

        for (int ex : exclude) {
            if (gRandomNumber == ex || gRandomNumber == 0) {
                generateRandomNumber(end, exclude);
            }
        }
    }
}
