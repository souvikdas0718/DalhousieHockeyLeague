package dhl.inputOutput.importJson;

import dhl.inputOutput.importJson.interfaces.IImportJsonFile;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImportJsonFile implements IImportJsonFile {

    private String filePath = "";
    IUserInputOutput ioObject;

    public ImportJsonFile(String filePath) {
        this.filePath = filePath;
        ioObject = IUserInputOutput.getInstance();
    }

    public JSONObject getJsonObject(){
        JSONObject JsonLeagueObject = null;
        JSONParser jsonParser = new JSONParser();

        try {
            FileReader JsonReader = new FileReader(filePath);
            Object genricObject = jsonParser.parse(JsonReader);
            JsonLeagueObject = (JSONObject) genricObject;
        }
        catch (FileNotFoundException e) {
            ioObject.printMessage("File not found on path: "+ filePath);
            ioObject.printMessage(e.getMessage());
            ioObject.printMessage("Exiting System");
            System.exit(0);
        }
        catch (ParseException | IOException e) {
            ioObject.printMessage("Error While parsing JSON please check JSON FILE ");
            ioObject.printMessage(e.getMessage());
            ioObject.printMessage("Exiting System");
            System.exit(0);
        }
        return JsonLeagueObject;
    }

    public String getJsonIntoString(String filePath) throws IOException {
        String jsonFileIntoString = new String(Files.readAllBytes(Paths.get(filePath)));
        return jsonFileIntoString;
    }
}
