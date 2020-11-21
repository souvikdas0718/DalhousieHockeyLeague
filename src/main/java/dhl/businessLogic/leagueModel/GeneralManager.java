package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.interfaceModel.IGeneralManager;

public class GeneralManager implements IGeneralManager {

    private String generalManagerName;
    private String generalManagerPersonality;

    public GeneralManager() {
        this.generalManagerName = "";
        this.generalManagerPersonality = "";
    }

    // TODO: 20-11-2020 Remove this when rashmi is done with CP
    public GeneralManager(String generalManagerName) {
        this.generalManagerName = generalManagerName;
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

