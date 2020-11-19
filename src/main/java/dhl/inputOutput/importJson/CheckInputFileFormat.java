package dhl.inputOutput.importJson;

import dhl.inputOutput.importJson.interfaces.ICheckInputFileFormat;
import org.json.JSONObject;

public class CheckInputFileFormat implements ICheckInputFileFormat {

    public boolean isCorrectFormated(String inputFileIntoSting) throws Exception {
        try {
            JSONObject jsonFormat = new JSONObject(inputFileIntoSting);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
