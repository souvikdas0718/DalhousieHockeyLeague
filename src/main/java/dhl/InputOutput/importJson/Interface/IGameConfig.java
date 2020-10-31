package dhl.InputOutput.importJson.Interface;

import java.util.HashMap;

public interface IGameConfig {

    HashMap getHashMap(String key);

    public void setRequiredObjectFromConfig(String configChildKey);

    public String getValueFromOurObject(String ourObjectKey);
}
