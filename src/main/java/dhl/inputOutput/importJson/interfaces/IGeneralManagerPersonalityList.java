package dhl.inputOutput.importJson.interfaces;

import dhl.businessLogic.leagueModel.factory.LeagueModelFactory;
import dhl.businessLogic.leagueModel.factory.LomAbstractFactory;
import dhl.inputOutput.importJson.GeneralManagerPersonalityList;

import java.util.Dictionary;

public abstract class IGeneralManagerPersonalityList {

    private static IGeneralManagerPersonalityList uniqueInstance = null;

    protected IGeneralManagerPersonalityList() {

    }

    public static IGeneralManagerPersonalityList instance(IGameConfig gameConfig) {
        if (null == uniqueInstance) {
            uniqueInstance = new GeneralManagerPersonalityList(gameConfig);
        }
        return uniqueInstance;
    }

    public static void setFactory(IGeneralManagerPersonalityList factory) {
        uniqueInstance = factory;
    }

    public abstract Dictionary getGeneralManagerPersonalityList();
}
