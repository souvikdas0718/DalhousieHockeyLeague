package dhl.simulationStateMachine;

import dhl.simulationStateMachine.Interface.ICheckInputFileFormat;
import org.json.JSONObject;

public class CheckInputFileFormat implements ICheckInputFileFormat {
    String inputFileIntoSting;

    public CheckInputFileFormat(String inputFileIntoSting){
        this.inputFileIntoSting = inputFileIntoSting;
    }

    public boolean isCorrectFormated() throws Exception {
        try{
            JSONObject jsonFormat = new JSONObject(inputFileIntoSting);
            return true;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
