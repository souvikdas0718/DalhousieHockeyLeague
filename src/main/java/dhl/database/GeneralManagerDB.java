package dhl.database;

import dhl.database.DatabaseConfigSetup.CallStoredProcedure;
import dhl.database.interfaceDB.IGeneralManagerDB;

import java.sql.SQLException;

public class GeneralManagerDB implements IGeneralManagerDB {
    public void insertGeneralManagers(String name, Integer leagueId)  throws Exception {
        try {
            CallStoredProcedure callproc = new CallStoredProcedure("insertGeneralManager(?,?)");
            callproc.setParameter(1, name);
            callproc.setParameter(2, leagueId);
            callproc.execute();
            callproc.cleanup();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
