package dhl.InputOutput.importJson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dhl.InputOutput.importJson.Interface.ISerializeLeagueobjectModel;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import java.io.FileWriter;

public class SerializeLeagueobjectModel implements ISerializeLeagueobjectModel {

    final String filepath = "testout.txt";

    public String serializeLeagueObjectModel(ILeagueObjectModel objLeagueObjectModel) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(objLeagueObjectModel);
        return jsonString;
    }

    public void WriteSerializedLeagueObjectToFile(ILeagueObjectModel objLeagueObjectModel) throws Exception {
        String serializedLeagueObjectModel = serializeLeagueObjectModel(objLeagueObjectModel);
        FileWriter filewriter = new FileWriter(filepath);
        filewriter.write(serializedLeagueObjectModel);
        filewriter.close();
    }
}
