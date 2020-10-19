package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.IGeneralManager;

public class GeneralManager implements IGeneralManager {

    private String generalManagerName;

    public GeneralManager(){
        setDefaults();
    }

    public void setDefaults() {
        generalManagerName = "";
    }

    public GeneralManager(String generalManagerName){
        this.setGeneralManagerName(generalManagerName);
    }

    public void setGeneralManagerName(String generalManagerName) {
        this.generalManagerName=generalManagerName;
    }

    public String getGeneralManagerName() {  return generalManagerName;   }

}
