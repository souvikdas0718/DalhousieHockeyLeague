package dhl.database;

import dhl.InputOutput.importJson.GameConfig;
import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.database.DatabaseConfigSetup.CallStoredProcedure;
import dhl.database.interfaceDB.IGameConfigDB;
import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GameConfigDB implements IGameConfigDB {
    String leagueName;
    public void insertGamePlayConfig(IGameConfig gameConfig,String leagueName)  throws Exception {
        this.leagueName=leagueName;

        HashMap<String,String[]> config=new HashMap<>();
        String[] agingValues={gameConfig.getAverageRetirementAge(),gameConfig.getMaximumAge()};
        config.put(gameConfig.getAging(),agingValues);

        String[] injuryValues={gameConfig.getRandomInjuryChance(),gameConfig.getInjuryDaysLow(),gameConfig.getInjuryDaysHigh()};
        config.put(gameConfig.getInjuries(),injuryValues);

        String[] gameResolverValues={gameConfig.getRandomWinChance()};
        config.put(gameConfig.getGameResolver(),gameResolverValues);

        String[] trainingValues={gameConfig.getDaysUntilStatIncreaseCheck()};
        config.put(gameConfig.getTraining(),trainingValues);

        String[] tradingValues={gameConfig.getLossPoint(),gameConfig.getRandomTradeOfferChance(),gameConfig.getMaxPlayersPerTrade(),gameConfig.getRandomAcceptanceChance()};
        config.put(gameConfig.getTrading(),tradingValues);

        for(String key:config.keySet()){
            String[] array=config.get(key);
            for(String subCategory : array){
                insertConfigValues(key, subCategory,gameConfig.getValueFromOurObject(key,subCategory));
            }
        }


    }

    public void insertConfigValues(String category,String subCategory,String configValue) throws Exception {
        try {
            CallStoredProcedure callproc = new CallStoredProcedure("insertGamePlayConfig(?,?,?,?)");
            callproc.setParameter(1, category);
            callproc.setParameter(2, subCategory);
            callproc.setParameter(3, configValue);
            callproc.setParameter(4, leagueName);
            callproc.execute();
            callproc.cleanup();
        }  catch (Exception exception) {
            throw  new Exception("Error inserting config in DB");
        }
    }

    public IGameConfig loadGamePlayConfig(String leagueName)  throws Exception {
        IGameConfig gameConfig=null;
        String configValue = "";
        try {
            CallStoredProcedure callproc = new CallStoredProcedure("loadGamePlayConfig(?)");
            callproc.setParameter(1, this.leagueName);
            ResultSet results = callproc.executeWithResults();
            JSONObject subObj=new JSONObject();
            JSONObject mainObj = new JSONObject();
            String category="";
            if (null != results) {
                while (results.next()) {
                    category=results.getString("category");
                    subObj.put(results.getString("subCategory"),results.getString("configValue"));
                }
                mainObj.put(category,subObj);

//                while (results.next()) {
//                    category=results.getString("category");
//                    subObj.put(results.getString("subCategory"),results.getString("configValue"));
//                }
//                mainObj.put(category,subObj);
            }
            else {
                throw new Exception("Game config not loaded properly");
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("gameplayConfig",mainObj);
            gameConfig=new GameConfig(jsonObject);
            callproc.cleanup();
        } catch (Exception e ) {
            e.printStackTrace();
        }

        return gameConfig;
    }


}
