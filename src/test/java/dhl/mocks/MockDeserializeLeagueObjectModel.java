package dhl.mocks;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.LeagueMock;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.IDeserializeLeagueObjectModel;

import java.util.List;

public class MockDeserializeLeagueObjectModel implements IDeserializeLeagueObjectModel {
    LeagueModelMockAbstractFactory leagueMockFactory;
    LeagueMock leagueMock;


    public MockDeserializeLeagueObjectModel(){
        leagueMockFactory= LeagueModelMockAbstractFactory.instance();
        leagueMock =  leagueMockFactory.createLeagueMock();

    }
    public ILeagueObjectModel deserializeLeagueObjectJson(String leagueName) {
        return leagueMock.getLeagueObjectModel();
    }

    public List<IPlayer> deserializePlayers(String leagueName)  {
        return null;
    }
}
