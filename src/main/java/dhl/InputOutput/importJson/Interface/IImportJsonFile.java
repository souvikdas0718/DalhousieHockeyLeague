package dhl.InputOutput.importJson.Interface;

import org.json.simple.JSONObject;
import java.io.IOException;


public interface IImportJsonFile {
    public JSONObject getJsonObject() throws Exception;

    public String getJsonIntoString(String filePath) throws IOException;
}
