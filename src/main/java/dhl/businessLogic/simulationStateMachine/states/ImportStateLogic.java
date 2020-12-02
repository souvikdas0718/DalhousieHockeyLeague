package dhl.businessLogic.simulationStateMachine.states;

import dhl.businessLogic.leagueModel.interfaceModel.IConference;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.states.interfaces.IImportStateLogic;
import dhl.businessLogic.traning.Training;
import dhl.inputOutput.importJson.CreateLeagueObjectModel;
import dhl.inputOutput.importJson.ImportJsonFile;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.simple.JSONObject;

import java.io.IOException;

public class ImportStateLogic implements IImportStateLogic {
    private static final String SCHEMAFILEPATH = "src/main/java/dhl/inputOutput/importJson/jsonSchema/schema.json";
    private static final Logger logger = LogManager.getLogger(Training.class);
    IUserInputOutput userInputPutput = IUserInputOutput.getInstance();

    public ILeagueObjectModel importAndGetLeagueObject(String validFilePath) throws IOException {
        logger.debug("Importing League");
        JSONObject leagueJsonObject = new ImportJsonFile(validFilePath).getJsonObject();

        ImportJsonFile importJsonFile = new ImportJsonFile(SCHEMAFILEPATH);
        String schemaJson = importJsonFile.getJsonIntoString(SCHEMAFILEPATH);
        String leagueJson = importJsonFile.getJsonIntoString(validFilePath);

        jsonSchemaValidation(leagueJson, schemaJson);

        CreateLeagueObjectModel createLeagueObjectModel = new CreateLeagueObjectModel(leagueJsonObject);
        ILeagueObjectModel leagueObjectModel = createLeagueObjectModel.getLeagueObjectModel();

        return leagueObjectModel;
    }

    public ITeam findTeam(ILeagueObjectModel inMemoryLeague, String teamName) {
        ITeam teamObject = null;

        for (IConference conference : inMemoryLeague.getConferences()) {
            for (IDivision division : conference.getDivisions()) {
                for (ITeam team : division.getTeams()) {
                    if (team.getTeamName().equals(teamName)) {
                        teamObject = team;
                    }
                }
            }
        }
        return teamObject;
    }

    public boolean jsonSchemaValidation(String leagueModel, String schemaJson) {
        boolean status = false;

        try {
            org.json.JSONObject jsonObject = new org.json.JSONObject(schemaJson);
            Schema schema = SchemaLoader.load(jsonObject);
            schema.validate(new org.json.JSONObject(leagueModel));
            status = true;
        } catch (ValidationException e) {
            userInputPutput.printMessage("Values are incorrect. Please fix the errors:");
            for (String schemaViolation : e.getAllMessages()) {
                userInputPutput.printMessage(schemaViolation);
            }
        } catch (JSONException e) {
            userInputPutput.printMessage("Incorrect JSON file" + e.getMessage());
        }
        return status;
    }
}
