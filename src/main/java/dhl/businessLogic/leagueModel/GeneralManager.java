package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.interfaceModel.IGeneralManager;

public class GeneralManager implements IGeneralManager {

    private String generalManagerName;

    public GeneralManager() {
        generalManagerName = "";
    }

    public GeneralManager(String generalManagerName) {
        this.generalManagerName = generalManagerName;
    }

    public String getGeneralManagerName() {
        return generalManagerName;
    }

}

