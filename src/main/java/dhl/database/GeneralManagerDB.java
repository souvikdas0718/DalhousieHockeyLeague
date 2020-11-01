package dhl.database;

import dhl.database.DatabaseConfigSetup.CallStoredProcedure;
import dhl.database.interfaceDB.IGeneralManagerDB;
import dhl.leagueModel.GeneralManager;
import dhl.leagueModel.interfaceModel.IGeneralManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<IGeneralManager> getManagersList(int leagueId) throws Exception {
        List<IGeneralManager> managers = new ArrayList<>();
        CallStoredProcedure callAgentProc = new CallStoredProcedure("loadAllManagers(?)");
        callAgentProc.setParameter(1, leagueId);
        ResultSet managerResultSet = callAgentProc.executeWithResults();

        if (managerResultSet==null){
            throw new Exception("Error loading Managers List");
        }

        while (managerResultSet.next()) {
            IGeneralManager manager=new GeneralManager(managerResultSet.getString("name"));
            managers.add(manager);
        }
        callAgentProc.cleanup();
        return managers;
    }
}
