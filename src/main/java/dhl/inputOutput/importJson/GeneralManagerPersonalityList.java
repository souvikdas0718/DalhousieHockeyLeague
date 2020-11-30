package dhl.inputOutput.importJson;

import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.Dictionary;
import java.util.Hashtable;

public class GeneralManagerPersonalityList extends GeneralManagerPersonalityListAbstract {
    String TRADING_NAME_IN_GAMECONFIG = "trading";
    String GMTABLE_NAME_IN_GAMECONFIG = "gmTable";
    Dictionary generalManagerPersonalityDictionary;
    IGameConfig ourGameConfig;
    IUserInputOutput ioObject;

    public GeneralManagerPersonalityList(IGameConfig gameConfig){
        ioObject = IUserInputOutput.getInstance();
        generalManagerPersonalityDictionary = new Hashtable();
        this.ourGameConfig = gameConfig;
    }

    public Dictionary getGeneralManagerPersonalityList(){
        String managerPersonalities = ourGameConfig.getValueFromOurObject(TRADING_NAME_IN_GAMECONFIG, GMTABLE_NAME_IN_GAMECONFIG);
        JSONParser jsonParser = new JSONParser();
        try {
            Object genricObject = jsonParser.parse(managerPersonalities);
            JSONObject managerPersonalitiesJsonObject = (JSONObject) genricObject;
            for (Object keyObject : managerPersonalitiesJsonObject.keySet()) {
                generalManagerPersonalityDictionary.put(String.valueOf(keyObject), managerPersonalitiesJsonObject.get(keyObject));
            }
            return generalManagerPersonalityDictionary;
        }catch (ParseException e){
            ioObject.printMessage("ERROR WHILE PARSING JSON");
            ioObject.printMessage(e.getMessage());
        }
        return null;
    }
}
