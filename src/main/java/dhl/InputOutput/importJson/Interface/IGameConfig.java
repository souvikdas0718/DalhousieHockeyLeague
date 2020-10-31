package dhl.InputOutput.importJson.Interface;

import java.util.HashMap;

public interface IGameConfig {

    HashMap getHashMap(String key);

    public String getValueFromOurObject(String configChildKey , String ourObjectKey);
}
