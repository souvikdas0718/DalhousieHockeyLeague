package dhl.businessLogic.GameSimulation;

import dhl.businessLogic.leagueModel.LeagueObjectModel;
import dhl.businessLogic.leagueModel.Player;
import dhl.businessLogic.leagueModel.PlayerStatistics;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;
import dhl.inputOutput.importJson.serializeDeserialize.DeserializeLeagueObjectModel;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.IDeserializeLeagueObjectModel;
import org.apache.logging.log4j.core.util.ArrayUtils;

import java.util.*;
import java.util.stream.Collectors;

public class GameSimulation {
    static Integer shotsA = 0;
    static Integer shotsB = 0;
    static Integer goalsA = 0;
    static Integer goalsB = 0;
    static Integer savesA = 0;
    static Integer savesB = 0;
    static Integer countToCheckOldPlayers = 0;
    static int[] forwardTeamA = new int[]{};
    static int[] forwardTeamB = new int[]{};
    static int[] defenceTeamA = new int[]{};
    static int[] defenceTeamB = new int[]{};
    static Integer gRandomNumber = 0;

    public static void main(String[] args) {
        test();
        System.out.println("shotsA: " + shotsA);
        System.out.println("shotsB: " + shotsB);
        System.out.println("goalsA: " + goalsA);
        System.out.println("goalsB: " + goalsB);
        System.out.println("savesA: " + savesA);
        System.out.println("savesB: " + savesB);
    }

    public static void createTeam(){
        List<IPlayer> listPlayerTeamA = generateListOfPlayersForTeamAFromJson();
        List<IPlayer> listForwardPlayerA = getPlayers("forward", listPlayerTeamA, forwardTeamA);
        List<IPlayer> listDefensePlayerA = getPlayers("defense", listPlayerTeamA, defenceTeamA);
        Optional<IPlayer> listgoaliePlayerA = listPlayerTeamA.stream().filter(c->c.getPosition()=="goalie").findFirst();

        List<IPlayer> listPlayerTeamB = generateListOfPlayersForTeamBFromJson();
        List<IPlayer> listForwardPlayerB = getPlayers("forward", listPlayerTeamB, forwardTeamB);
        List<IPlayer> listDefensePlayerB = getPlayers("defense", listPlayerTeamB, defenceTeamB);
        Optional<IPlayer> listgoaliePlayerB = listPlayerTeamB.stream().filter(c->c.getPosition()=="goalie").findFirst();

        Optional<IPlayer> maxSkatingFwdPlayerTeamA = getMaxStat(listForwardPlayerA, "skating");
        Optional<IPlayer> maxSkatingFwdPlayerTeamB = getMaxStat(listForwardPlayerB, "skating");
        Optional<IPlayer> maxCheckingDefPlayerTeamA = getMaxStat(listDefensePlayerA, "checking");
        Optional<IPlayer> maxCheckingDefPlayerTeamB = getMaxStat(listDefensePlayerB, "checking");

        Integer forwardTeamASkating = maxSkatingFwdPlayerTeamA.get().getPlayerStats().getSkating();
        Integer forwardTeamBSkating = maxSkatingFwdPlayerTeamB.get().getPlayerStats().getSkating();
        Integer forwardSkatTeamAChecking = maxSkatingFwdPlayerTeamA.get().getPlayerStats().getChecking();
        Integer defenseTeamBChecking = maxCheckingDefPlayerTeamB.get().getPlayerStats().getChecking();
        
        if(forwardTeamASkating > forwardTeamBSkating){
            shotsA = shotsA + 1;
            if(forwardSkatTeamAChecking > defenseTeamBChecking){
                if(maxSkatingFwdPlayerTeamA.get().getPlayerStats().getSaving() > listgoaliePlayerB.get().getPlayerStats().getSaving()){
                    goalsA = goalsA + 1;
                }
                else {
                    savesB = savesB + 1;
                }
            }
            else {
                savesB = savesB + 1;
                //TODO: penalty
            }
        }
        else if(maxSkatingFwdPlayerTeamB.get().getPlayerStats().getSkating() > maxSkatingFwdPlayerTeamA.get().getPlayerStats().getSkating()){
            shotsB = shotsB + 1;
            if(maxSkatingFwdPlayerTeamB.get().getPlayerStats().getChecking() > maxCheckingDefPlayerTeamA.get().getPlayerStats().getChecking()){

                if(maxSkatingFwdPlayerTeamB.get().getPlayerStats().getSaving() > listgoaliePlayerA.get().getPlayerStats().getSaving()){
                    goalsB = goalsB + 1;
                }
                else {
                    savesA = savesA + 1;
                }
            }
            else {
                savesA = savesA + 1;
                //TODO: penalty
            }
        }
    }

    public static Optional<IPlayer> getMaxStat(List<IPlayer> statsList, String stat){
        List<Integer> statsListTeamA = new ArrayList<>();
        for(int i=0;i<statsList.size();i++){
            if(stat == "shooting") {
                statsListTeamA.add(statsList.get(i).getPlayerStats().getShooting());
            }
            else if (stat == "checking") {
                statsListTeamA.add(statsList.get(i).getPlayerStats().getChecking());
            }
            else if (stat == "saving") {
                statsListTeamA.add(statsList.get(i).getPlayerStats().getSaving());
            }
            else if (stat == "skating") {
                statsListTeamA.add(statsList.get(i).getPlayerStats().getSkating());
            }
        }

        Collections.sort(statsListTeamA, Collections.reverseOrder());
        Integer maxValue = statsListTeamA.get(0);
        Optional<IPlayer> player = Optional.empty();

        if(stat == "shooting") {
            player = statsList.stream().filter(c->c.getPlayerStats().getShooting() == maxValue).findFirst();
        }
        else if (stat == "checking") {
            player = statsList.stream().filter(c->c.getPlayerStats().getChecking() == maxValue).findFirst();
        }
        else if (stat == "saving") {
            player = statsList.stream().filter(c->c.getPlayerStats().getSaving() == maxValue).findFirst();
        }
        else if (stat == "skating") {
            player = statsList.stream().filter(c->c.getPlayerStats().getSkating() == maxValue).findFirst();
        }

        return player;
    }

    public static List<IPlayer> getPlayers(String type, List<IPlayer> listPlayer, int[] oldPlayer){
        List<IPlayer> listForwardPlayer = new ArrayList<>();
        listForwardPlayer = listPlayer.stream().filter(c->c.getPosition()==type).collect(Collectors.toList());

        HashMap<Integer, IPlayer> mapPlayer = new HashMap<>();

        for(int i=0; i< listForwardPlayer.size();i++) {
            mapPlayer.put(i+1,listForwardPlayer.get(i));
        }

        List<Integer> arrNewRandomNumbers = new ArrayList<Integer>();
        Integer playerCount = 0;

        if(type == "forward") {
            playerCount = 3;
        }
        else if(type =="defense"){
            playerCount = 2;
        }

        arrNewRandomNumbers = getRandomNumbers(oldPlayer, playerCount);

        StringBuilder stringOldRandomNumbers = new StringBuilder();
        List<IPlayer> listFilteredPlayer = new ArrayList<>();

        for(int i=0; i<arrNewRandomNumbers.size();i++) {
            stringOldRandomNumbers.append(arrNewRandomNumbers.get(i)+ ",");
            listFilteredPlayer.add(mapPlayer.get(arrNewRandomNumbers.get(i)));
        }

        System.out.println(stringOldRandomNumbers.toString());
        stringOldRandomNumbers.deleteCharAt(stringOldRandomNumbers.length()-1);
        String[] oldNumberArr = stringOldRandomNumbers.toString().split(",");
        int [] newNumberarr = new int [oldNumberArr.length];
        for(int i=0; i<oldNumberArr.length; i++) {
            newNumberarr[i] = Integer.parseInt(oldNumberArr[i]);
        }

        countToCheckOldPlayers = countToCheckOldPlayers + 1;

        if (countToCheckOldPlayers == 1){
            forwardTeamA = newNumberarr;
        }
        else if (countToCheckOldPlayers == 2){
            defenceTeamA = newNumberarr;
        }
        else if (countToCheckOldPlayers == 3){
            forwardTeamB = newNumberarr;
        }
        else if (countToCheckOldPlayers == 4){
            defenceTeamB = newNumberarr;
        }

        return listFilteredPlayer;
    }

    public static List<Integer> getRandomNumbers(int[] arrOldRandomNumbers, int playerCount ){
        if (arrOldRandomNumbers.length == 0){
            arrOldRandomNumbers = new int[]{1, 2, 3};
        }
        Random random = new Random();
        List<Integer> arrNewRandomNumbers = new ArrayList<Integer>();
        StringBuilder existingNumbers = new StringBuilder();
        int randomNumber = 0;

        for (int k = 0; k < arrOldRandomNumbers.length; k++) {
            existingNumbers.append(arrOldRandomNumbers[k] + ",");
        }

        for(int i =0;i<playerCount;i++) {
            String[] arrSplitExistingNumbers = existingNumbers.toString().substring(0, existingNumbers.length() - 1).split(",");
            int [] arrExistingNumbers = new int [arrSplitExistingNumbers.length];
            for(int j=0; j<arrSplitExistingNumbers.length; j++) {
                arrExistingNumbers[j] = Integer.parseInt(arrSplitExistingNumbers[j]);
            }

            Integer endNumber = 0;
            if(playerCount == 3){
                endNumber = 12;
            }else{
                endNumber = 6;
            }
            generateRandomNumberTest(endNumber,arrExistingNumbers);
            randomNumber = gRandomNumber;
            arrNewRandomNumbers.add(randomNumber);
            existingNumbers.append(randomNumber + ",");
        }
        return arrNewRandomNumbers;
    }


    public static void generateRandomNumberTest(Integer end,int... exclude) {
        Random rnd = new Random();
        gRandomNumber = rnd.nextInt(end);
        for (int ex : exclude) {
            if (gRandomNumber == ex || gRandomNumber == 0) {
                generateRandomNumberTest(end, exclude);
            }
        }
    }

    public static void test(){
        int shifts = 40;

        for (int i=0; i<shifts; i++){
            int shotsInAShift =1;
            if(i%2==0){
                shotsInAShift =2;
            }
            for (int j=0; j<shotsInAShift;j++){
                createTeam();
            }
        }
    }

    public static List<IPlayer> generateListOfPlayersForTeamBFromJson(){
        List<IPlayer> playersList = new ArrayList<>();
        IDeserializeLeagueObjectModel deserializeleagueObjectModel = new DeserializeLeagueObjectModel("src/test/java/dhl/Mocks/");
        try {
            ILeagueObjectModel leagueObjectModel = deserializeleagueObjectModel.deserializeLeagueObjectJson("jsonMock3");
            playersList = leagueObjectModel.getConferences().get(0).getDivisions().get(0).getTeams().get(0).getPlayers();
        } catch (Exception e) {

        }
        return playersList;
    }

    public static List<IPlayer> generateListOfPlayersForTeamAFromJson(){
        List<IPlayer> playersList = new ArrayList<>();
        IDeserializeLeagueObjectModel deserializeleagueObjectModel = new DeserializeLeagueObjectModel("src/test/java/dhl/Mocks/");
        try {
            ILeagueObjectModel leagueObjectModel = deserializeleagueObjectModel.deserializeLeagueObjectJson("jsonMock3");
            playersList = leagueObjectModel.getConferences().get(0).getDivisions().get(0).getTeams().get(1).getPlayers();
        } catch (Exception e) {

        }
        return playersList;
    }
}


