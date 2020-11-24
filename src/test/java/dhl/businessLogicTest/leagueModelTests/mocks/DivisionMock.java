package dhl.businessLogicTest.leagueModelTests.mocks;

import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.IDivision;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;

import java.util.ArrayList;
import java.util.List;

public class DivisionMock {

    LeagueModelAbstractFactory factory;
    LeagueModelMockAbstractFactory mockFactory;

    public DivisionMock(){
        factory = LeagueModelAbstractFactory.instance();
        mockFactory = LeagueModelMockAbstractFactory.instance();
    }

    public IDivision getDivision(){
        List<ITeam> teamArrayList = new ArrayList<>();
        TeamMock teamMock = mockFactory.createTeamMock();
        teamArrayList.add(teamMock.getTeam());
        return factory.createDivision("Atlantic", teamArrayList);
    }

    public IDivision getDivisionWithName(String divisionName){
        List<ITeam> teamArrayList = new ArrayList<>();
        TeamMock teamMock = mockFactory.createTeamMock();
        teamArrayList.add(teamMock.getTeam());
        return factory.createDivision(divisionName, teamArrayList);
    }

    public IDivision getDivisionWithSameTeamName(){
        List<ITeam> teamArrayList = new ArrayList<>();
        TeamMock teamMock = mockFactory.createTeamMock();
        for(int i=0;i<2;i++){
            teamArrayList.add(teamMock.getTeam());
        }
        return factory.createDivision("Atlantic", teamArrayList);
    }

}
