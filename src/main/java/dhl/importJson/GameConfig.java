package dhl.importJson;

import dhl.importJson.Interface.IGameConfig;
import org.json.simple.JSONObject;
import java.util.HashMap;

public class GameConfig implements IGameConfig {

    JSONObject ourJsonObject;
    public GameConfig(JSONObject jsonObject){
        ourJsonObject = jsonObject;
        ourJsonObject = (JSONObject) ourJsonObject.get("gameplayConfig");
    }

    @Override
    public HashMap getHashMap(String key){
        HashMap mapToReturn = new HashMap();
        JSONObject subObject = (JSONObject) ourJsonObject.get(key);
        if (subObject != null){
            for (Object subObjectkey:subObject.keySet()){
                if (subObjectkey!= null){
                    if (subObject.get(subObjectkey.toString())!= null){
                        mapToReturn.put(subObjectkey.toString() ,subObject.get(subObjectkey.toString()) );
                    }
                }
            }
        }
        return mapToReturn;
    }

}
