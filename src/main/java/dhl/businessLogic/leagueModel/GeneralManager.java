package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.interfaceModel.IGeneralManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GeneralManager implements IGeneralManager {

    private String generalManagerName;
    public String generalManagerPersonality;

    private static final Logger logger = LogManager.getLogger(GeneralManager.class);

    public GeneralManager() {
        this.generalManagerName = "";
        this.generalManagerPersonality = "";
        logger.debug("Creating default manager");
    }

    public GeneralManager(String generalManagerName) {
        this.generalManagerName = generalManagerName;
        this.generalManagerPersonality = "";
        logger.debug("Creating manager " + generalManagerName);

    }

    public GeneralManager(String generalManagerName, String personality) {
        this.generalManagerName = generalManagerName;
        this.generalManagerPersonality = personality;
        logger.debug("Creating manager " + generalManagerName + " Personality: " + personality);
    }

    public String getGeneralManagerName() {
        return generalManagerName;
    }

    public String getGeneralManagerPersonality() {
        return generalManagerPersonality;
    }
}

