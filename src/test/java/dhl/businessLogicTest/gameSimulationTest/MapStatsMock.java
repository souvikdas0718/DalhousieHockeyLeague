package dhl.businessLogicTest.gameSimulationTest;

import java.util.HashMap;

public class MapStatsMock {
    public HashMap<String, Integer> mapStatsPreferForwardTeamOne(){
        HashMap<String, Integer> mapStats = new HashMap<>();
        mapStats.put("TeamOneFSkating",2);
        mapStats.put("TeamTwoFSkating",1);
        mapStats.put("TeamOneFChecking",2);
        mapStats.put("TeamTwoDChecking",1);
        mapStats.put("TeamOneFSaving",2);
        mapStats.put("TeamTwoGSaving",1);

        mapStats.put("TeamTwoFChecking",1);
        mapStats.put("TeamOneDChecking",1);
        mapStats.put("TeamTwoFSaving",1);
        mapStats.put("TeamOneGSaving",1);

        return mapStats;
    }

    public HashMap<String, Integer> mapStatsPreferSavingGoalieTeamOne(){
        HashMap<String, Integer> mapStats = new HashMap<>();
        mapStats.put("TeamOneFSkating",2);
        mapStats.put("TeamTwoFSkating",1);
        mapStats.put("TeamOneFChecking",2);
        mapStats.put("TeamTwoDChecking",1);
        mapStats.put("TeamOneFSaving",1);
        mapStats.put("TeamTwoGSaving",2);

        mapStats.put("TeamTwoFChecking",1);
        mapStats.put("TeamOneDChecking",1);
        mapStats.put("TeamTwoFSaving",1);
        mapStats.put("TeamOneGSaving",1);

        return mapStats;
    }

    public HashMap<String, Integer> mapStatsPreferDefenceTeamTwo(){
        HashMap<String, Integer> mapStats = new HashMap<>();
        mapStats.put("TeamOneFSkating",2);
        mapStats.put("TeamTwoFSkating",1);
        mapStats.put("TeamOneFChecking",1);
        mapStats.put("TeamTwoDChecking",2);
        mapStats.put("TeamOneFSaving",2);
        mapStats.put("TeamTwoGSaving",1);

        mapStats.put("TeamTwoFChecking",1);
        mapStats.put("TeamOneDChecking",1);
        mapStats.put("TeamTwoFSaving",1);
        mapStats.put("TeamOneGSaving",1);

        return mapStats;
    }

    public HashMap<String, Integer> mapStatsPreferForwardTeamTwo(){
        HashMap<String, Integer> mapStats = new HashMap<>();
        mapStats.put("TeamOneFSkating",1);
        mapStats.put("TeamTwoFSkating",2);
        mapStats.put("TeamOneFChecking",2);
        mapStats.put("TeamTwoDChecking",1);
        mapStats.put("TeamOneFSaving",2);
        mapStats.put("TeamTwoGSaving",1);

        mapStats.put("TeamTwoFChecking",2);
        mapStats.put("TeamOneDChecking",1);
        mapStats.put("TeamTwoFSaving",2);
        mapStats.put("TeamOneGSaving",1);

        return mapStats;
    }

    public HashMap<String, Integer> mapStatsPreferForwardCheckingTeamTwo(){
        HashMap<String, Integer> mapStats = new HashMap<>();
        mapStats.put("TeamOneFSkating",1);
        mapStats.put("TeamTwoFSkating",2);
        mapStats.put("TeamOneFChecking",2);
        mapStats.put("TeamTwoDChecking",1);
        mapStats.put("TeamOneFSaving",2);
        mapStats.put("TeamTwoGSaving",1);

        mapStats.put("TeamTwoFChecking",1);
        mapStats.put("TeamOneDChecking",2);
        mapStats.put("TeamTwoFSaving",1);
        mapStats.put("TeamOneGSaving",1);

        return mapStats;
    }

    public HashMap<String, Integer> mapStatsPreferForwardSavingTeamTwo(){
        HashMap<String, Integer> mapStats = new HashMap<>();
        mapStats.put("TeamOneFSkating",1);
        mapStats.put("TeamTwoFSkating",2);
        mapStats.put("TeamOneFChecking",2);
        mapStats.put("TeamTwoDChecking",1);
        mapStats.put("TeamOneFSaving",2);
        mapStats.put("TeamTwoGSaving",1);

        mapStats.put("TeamTwoFChecking",2);
        mapStats.put("TeamOneDChecking",1);
        mapStats.put("TeamTwoFSaving",1);
        mapStats.put("TeamOneGSaving",2);

        return mapStats;
    }
}
