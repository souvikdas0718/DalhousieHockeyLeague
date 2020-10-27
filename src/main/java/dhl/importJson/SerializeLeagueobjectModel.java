package dhl.importJson;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonObject;
import dhl.importJson.Interface.ISerializeLeagueobjectModel;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;

import java.io.FileWriter;
import java.io.IOException;

public class SerializeLeagueobjectModel implements ISerializeLeagueobjectModel {
    public String serializeLeagueObjectModel(ILeagueObjectModel objLeagueObjectModel) throws Exception {
       // Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = "test";
        //jsonString = gson.toJson(objLeagueObjectModel);
        WriteToFile("src//main//testout.txt", jsonString);
        return jsonString;
    }
    public void WriteToFile(String path, String content) throws Exception {
        FileWriter fw=new FileWriter(path);
        fw.write(content);
        fw.close();
    }
}
