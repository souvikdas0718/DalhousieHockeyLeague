package dhl.simulationStateMachine;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ImportJsonFile {

    private String filePath = "";
    private JSONObject JsonLeagueObject = null;

    public ImportJsonFile(String filePath){
        this.filePath = filePath;
    }

    public JSONObject getJsonObject(){

        JSONParser jsonParser = new JSONParser();

        try (FileReader JsonReader = new FileReader(filePath))
        {
            Object genricObject = jsonParser.parse(JsonReader);
            JsonLeagueObject = (JSONObject) genricObject;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File Path is Wrong");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Json Structure is invalid");
        }
        return JsonLeagueObject;
    }
}
