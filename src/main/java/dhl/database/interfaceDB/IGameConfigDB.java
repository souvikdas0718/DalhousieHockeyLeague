package dhl.database.interfaceDB;

import java.util.ArrayList;
import java.util.HashMap;

public interface IGameConfigDB {

    public void insertGamePlayConfig(String category, String subCategory,String configValue, String leagueName)  throws Exception;

    public String loadGamePlayConfig(String category, String subCategory, String leagueName) throws Exception;

}
