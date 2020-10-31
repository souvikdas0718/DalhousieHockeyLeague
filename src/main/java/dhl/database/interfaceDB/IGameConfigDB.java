package dhl.database.interfaceDB;


import dhl.InputOutput.importJson.Interface.IGameConfig;

public interface IGameConfigDB {

    public void insertGamePlayConfig(IGameConfig gameConfig, String leagueName)  throws Exception;

    public String loadGamePlayConfig(String category, String subCategory, String leagueName) throws Exception;

}
