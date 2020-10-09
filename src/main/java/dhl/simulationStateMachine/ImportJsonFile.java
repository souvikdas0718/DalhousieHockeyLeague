package dhl.simulationStateMachine;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImportJsonFile {

    private String filePath = "";

    public ImportJsonFile(String filePath){
        this.filePath = filePath;
    }

    public JSONObject getJsonObject() throws Exception {

        JSONObject JsonLeagueObject = null;
        JSONParser jsonParser = new JSONParser();
        String jsonFileIntoString = getJsonIntoString(filePath);

        try{
            new CheckJsonFormat(jsonFileIntoString).isJsonFormated();
            System.out.println("Valid JSON file Received");
            FileReader JsonReader = new FileReader(filePath);
            Object genricObject = jsonParser.parse(JsonReader);
            JsonLeagueObject = (JSONObject) genricObject;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return JsonLeagueObject;
    }

    public String getJsonIntoString(String filePath) throws IOException {

        String jsonFileIntoString = new String(Files.readAllBytes(Paths.get(filePath)));

        return jsonFileIntoString;
    }
}
