package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.IGeneralManager;

public class GeneralManager implements IGeneralManager {

    private String generalManagerName;

    public GeneralManager(){
        generalManagerName="";
    }

    public GeneralManager(String generalManagerName){
        this.generalManagerName = generalManagerName;
    }

    public String getGeneralManagerName() {  return generalManagerName;   }

}

