package dhl.inputOutput.importJson;

import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Dictionary;

public abstract class GeneralManagerPersonalityListAbstract {
    private static final Logger logger = LogManager.getLogger(GeneralManagerPersonalityListAbstract.class);
    private static GeneralManagerPersonalityListAbstract uniqueInstance = null;

    protected GeneralManagerPersonalityListAbstract() {

    }

    public static GeneralManagerPersonalityListAbstract instance(IGameConfig gameConfig) {
        if (null == uniqueInstance) {
            logger.debug("GeneralManagerPersonalityListAbstract's instance made");
            uniqueInstance = new GeneralManagerPersonalityList(gameConfig);
        }
        logger.debug("GeneralManagerPersonalityListAbstract's instance accessed");
        return uniqueInstance;
    }

    public static void setFactory(GeneralManagerPersonalityListAbstract factory) {
        logger.debug("GeneralManagerPersonalityListAbstract's instance updated");
        uniqueInstance = factory;
    }

    public abstract Dictionary getGeneralManagerPersonalityList();
}
