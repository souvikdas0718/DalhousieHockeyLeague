package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.interfaceModel.IGeneralManager;

public class GeneralManager implements IGeneralManager {

    private String generalManagerName;
    private String generalManagerPersonality;

    public GeneralManager() {
        this.generalManagerName = "";
        this.generalManagerPersonality = "";
    }

    public GeneralManager(String generalManagerName) {
        this.generalManagerName = generalManagerName;
        this.generalManagerPersonality = "";
    }

    public GeneralManager(String generalManagerName, String personality) {
        this.generalManagerName = generalManagerName;
        this.generalManagerPersonality = personality;
    }

    public String getGeneralManagerName() {
        return generalManagerName;
    }

    public String getGeneralManagerPersonality() {
        return generalManagerPersonality;
    }
}

