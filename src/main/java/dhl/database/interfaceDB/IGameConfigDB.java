package dhl.database.interfaceDB;


import dhl.InputOutput.importJson.Interface.IGameConfig;

public interface IGameConfigDB {

    void insertGamePlayConfig(IGameConfig gameConfig, String leagueName) throws Exception;

    IGameConfig loadGamePlayConfig(String leagueName) throws Exception;

}
