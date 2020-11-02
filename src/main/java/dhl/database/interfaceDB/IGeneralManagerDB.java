package dhl.database.interfaceDB;

import dhl.leagueModel.interfaceModel.IGeneralManager;

import java.util.List;

public interface IGeneralManagerDB {
    void insertGeneralManagers(String name, Integer leagueId) throws Exception;

    List<IGeneralManager> getManagersList(int leagueId) throws Exception;
}
