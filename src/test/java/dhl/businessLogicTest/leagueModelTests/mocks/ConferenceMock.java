package dhl.businessLogicTest.leagueModelTests.mocks;

import dhl.businessLogic.leagueModel.*;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;

import java.util.ArrayList;
import java.util.List;

public class ConferenceMock {

    LeagueModelAbstractFactory factory;
    LeagueModelMockAbstractFactory mockFactory;
    DivisionMock divisionMock;

    public ConferenceMock(){
        factory = LeagueModelAbstractFactory.instance();
        mockFactory = LeagueModelMockAbstractFactory.instance();
        divisionMock = mockFactory.createDivisionMock();
    }

    public IConference getConference(){
        List<IDivision> divisionsList = new ArrayList<>();
        divisionsList.add(divisionMock.getDivision());
        return factory.createConference("Western", divisionsList);
    }

    public IConference getConferenceWithName(String conferenceName){
        List<IDivision> divisionsList = new ArrayList<>();
        divisionsList.add(divisionMock.getDivision());
        divisionsList.add(divisionMock.getDivisionWithName("Pacific"));
        return factory.createConference(conferenceName, divisionsList);
    }

    public IConference getConferenceWithTwoDivisions(){
        List<IDivision> divisionsList = new ArrayList<>();
        divisionsList.add(divisionMock.getDivision());
        divisionsList.add(divisionMock.getDivisionWithName("Pacifica"));
        return factory.createConference("Western", divisionsList);
    }

    public IConference getConferenceWithSameDivisions(){
        List<IDivision> divisionsList = new ArrayList<>();
       for(int i=0;i<2;i++){
           divisionsList.add(divisionMock.getDivisionWithName("Pacific"));
       }
        return factory.createConference("Western", divisionsList);
    }

    public List<IConference> getConferences(){
        List<IConference> conferences = new ArrayList<>();
        conferences.add(getConferenceWithTwoDivisions());
        conferences.add(getConferenceWithName("Eastern"));
        return conferences;
    }

    public IConference getConferenceOddDivision(){
        List<IDivision> divisionsList = new ArrayList<>();
        for(int i=0;i<3;i++){
            divisionsList.add(divisionMock.getDivisionWithName("Pacific"+i));
        }
        return factory.createConference("Western", divisionsList);
    }

}
