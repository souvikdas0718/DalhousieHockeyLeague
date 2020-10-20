package dhl.database;

import dhl.database.DatabaseConfigSetup.CallStoredProcedure;
import dhl.database.interfaceDB.IGameConfigDB;
import java.sql.ResultSet;
import java.sql.SQLException;

public class gameConfigDB implements IGameConfigDB {
    public void insertGamePlayConfig(String category, String subCategory,String configValue, String leagueName)  throws Exception {
        try {
            CallStoredProcedure callproc = new CallStoredProcedure("insertGamePlayConfig(?,?,?,?)");
            callproc.setParameter(1, category);
            callproc.setParameter(2, subCategory);
            callproc.setParameter(3, configValue);
            callproc.setParameter(4, leagueName);
            callproc.execute();
            callproc.cleanup();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String loadGamePlayConfig(String category, String subCategory, String leagueName)  throws Exception {
        String configValue = "";
        try {
            CallStoredProcedure callproc = new CallStoredProcedure("loadGamePlayConfig(?,?,?)");
            callproc.setParameter(1, category);
            callproc.setParameter(2, subCategory);
            callproc.setParameter(3, leagueName);
            ResultSet results = callproc.executeWithResults();

            if (null != results) {
                while (results.next()) {
                    configValue = results.getString("configValue");
                }
            }
            else {
                throw new Exception("Game config not loaded properly");
            }

            callproc.cleanup();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return configValue;
    }
}
