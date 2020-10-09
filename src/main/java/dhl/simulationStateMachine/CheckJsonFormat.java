package dhl.simulationStateMachine;

import org.json.JSONObject;

public class CheckJsonFormat {
    String jsonFileIntoSting;

    public CheckJsonFormat(String jsonFileIntoSting){
        this.jsonFileIntoSting = jsonFileIntoSting;
    }

    public void isJsonFormated() throws Exception {
        try{
            JSONObject jsonFormat = new JSONObject(jsonFileIntoSting);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
