package dhl.database.interfaceDB;


import dhl.InputOutput.importJson.Interface.IGameConfig;

public interface IGameConfigDB {

    public void insertGamePlayConfig(IGameConfig gameConfig, String leagueName)  throws Exception;

    public IGameConfig loadGamePlayConfig(String leagueName) throws Exception;

}
