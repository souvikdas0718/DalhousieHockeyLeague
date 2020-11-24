package dhl.businessLogicTest.leagueModelTests.mocks;

import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.factory.LeagueModelFactory;
import dhl.businessLogic.leagueModel.interfaceModel.ICoach;

import java.util.ArrayList;
import java.util.List;

public class CoachMock {
    LeagueModelAbstractFactory factory;

    public CoachMock(){
        LeagueModelAbstractFactory.setFactory(new LeagueModelFactory());
        factory = LeagueModelAbstractFactory.instance();
    }


    public ICoach getCoach(){
        return factory.createCoach("Todd McLellan", 0.1, 0.2, 0.5, 1.0);
    }

    public ICoach getCoachWithoutName(){
        return factory.createCoach("", 0.1, 0.2, 0.5, 1.0);
    }

    public ICoach getCoachInvalidStat(){
        return factory.createCoach("Todd McLellan", 0.1, 0.2, 3, 1.0);
    }

    public List<ICoach> getCoaches(){
        List<ICoach> coaches = new ArrayList<>();
        for(int i = 0; i<2; i++){
            coaches.add(getCoach());
        }
        return coaches;
    }
}
