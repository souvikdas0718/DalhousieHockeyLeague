package dhl.businessLogicTest.leagueModelTests.mocks;

import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.factory.LeagueModelFactory;
import dhl.businessLogic.leagueModel.interfaceModel.IGeneralManager;

import java.util.ArrayList;
import java.util.List;

public class ManagerMock {
    LeagueModelAbstractFactory factory;

    public ManagerMock(){
        LeagueModelAbstractFactory.setFactory(new LeagueModelFactory());
        factory = LeagueModelAbstractFactory.instance();
    }

    public IGeneralManager getManager(){
        return factory.createGeneralManager("Mathew Jacob","normal");
    }

    public IGeneralManager getManagerWithoutName(){
        return factory.createGeneralManager("","normal");
    }

    public List<IGeneralManager> getManagers(){
        List<IGeneralManager> managers = new ArrayList<>();
        managers.add(getManager());
        return managers;
    }

}
