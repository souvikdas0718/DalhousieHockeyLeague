package dhl.inputOutput.importJson;


import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import org.json.simple.parser.ParseException;

import java.util.Dictionary;

public abstract class GeneralManagerPersonalityListAbstract {

    private static GeneralManagerPersonalityListAbstract uniqueInstance = null;

    protected GeneralManagerPersonalityListAbstract() {

    }

    public static GeneralManagerPersonalityListAbstract instance(IGameConfig gameConfig) {
        if (null == uniqueInstance) {
            uniqueInstance = new GeneralManagerPersonalityList(gameConfig);
        }
        return uniqueInstance;
    }

    public static void setFactory(GeneralManagerPersonalityListAbstract factory) {
        uniqueInstance = factory;
    }

    public abstract Dictionary getGeneralManagerPersonalityList();
}
