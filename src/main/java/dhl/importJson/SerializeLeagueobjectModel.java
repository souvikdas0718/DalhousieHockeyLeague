package dhl.importJson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dhl.importJson.Interface.ISerializeLeagueobjectModel;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import java.io.FileWriter;

public class SerializeLeagueobjectModel implements ISerializeLeagueobjectModel {

    public String serializeLeagueObjectModel(ILeagueObjectModel objLeagueObjectModel) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(objLeagueObjectModel);
        return jsonString;
    }

    public void WriteSerializedLeagueObjectToFile(ILeagueObjectModel objLeagueObjectModel) throws Exception {
        String content = serializeLeagueObjectModel(objLeagueObjectModel);
        FileWriter fw=new FileWriter("src/main/testout.txt");
        fw.write(content);
        fw.close();
    }

}
