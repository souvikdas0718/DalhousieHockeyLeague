package dhl.inputOutput.importJson;

import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Dictionary;
import java.util.Hashtable;

public class GeneralManagerPersonalityList extends GeneralManagerPersonalityListAbstract {

    private static final Logger logger = LogManager.getLogger(GeneralManagerPersonalityList.class);
    String TRADING_NAME_IN_GAMECONFIG = "trading";
    String GMTABLE_NAME_IN_GAMECONFIG = "gmTable";
    Dictionary generalManagerPersonalityDictionary;
    IGameConfig ourGameConfig;
    IUserInputOutput ioObject;

    public GeneralManagerPersonalityList(IGameConfig gameConfig) {
        ioObject = IUserInputOutput.getInstance();
        generalManagerPersonalityDictionary = new Hashtable();
        this.ourGameConfig = gameConfig;
    }

    public Dictionary getGeneralManagerPersonalityList() {
        logger.info("Getting GeneralManager's PersonalityList");
        String managerPersonalities = ourGameConfig.getValueFromOurObject(TRADING_NAME_IN_GAMECONFIG, GMTABLE_NAME_IN_GAMECONFIG);
        JSONParser jsonParser = new JSONParser();
        try {
            logger.info("Trying to parse get list from game config");
            Object genricObject = jsonParser.parse(managerPersonalities);
            JSONObject managerPersonalitiesJsonObject = (JSONObject) genricObject;
            for (Object keyObject : managerPersonalitiesJsonObject.keySet()) {
                generalManagerPersonalityDictionary.put(String.valueOf(keyObject), managerPersonalitiesJsonObject.get(keyObject));
            }
            return generalManagerPersonalityDictionary;
        } catch (ParseException e) {
            logger.error("ERROR WHILE PARSING JSON");
            ioObject.printMessage("ERROR WHILE PARSING JSON");
            ioObject.printMessage(e.getMessage());
        }
        return null;
    }
}
