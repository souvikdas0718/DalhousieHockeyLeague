package dhl.businessLogicTest.agingTest.factory;

import dhl.mocks.factory.MockAbstractFactory;
import dhl.businessLogic.aging.agingFactory.AgingAbstractFactory;
import dhl.businessLogic.aging.interfaceAging.IAging;
import dhl.businessLogic.aging.interfaceAging.ILeagueSchedule;
import dhl.businessLogic.aging.interfaceAging.IRetirement;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogicTest.agingTest.mocks.AgingMock;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.GameplayConfigMock;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AgingFactoryTest {
    AgingAbstractFactory agingFactory;
    LeagueModelAbstractFactory leagueFactory;
    MockAbstractFactory mockFactory;
    ISerializeLeagueObjectModel serializeLeagueObjectModel;

    @BeforeEach
    public void initialize()  {
        leagueFactory= LeagueModelAbstractFactory.instance();
        mockFactory = MockAbstractFactory.instance();
        agingFactory = AgingAbstractFactory.instance();
        serializeLeagueObjectModel = mockFactory.getMockSerialize();

    }

    @Test
    public void createRetirementTest(){
        IRetirement retirement = agingFactory.createRetirement(serializeLeagueObjectModel);
        Assertions.assertNotNull(retirement);
    }

    @Test
    public void createAgingTest(){
        LeagueModelMockAbstractFactory leagueMockFactory = LeagueModelMockAbstractFactory.instance();
        GameplayConfigMock gameplayConfigMock = leagueMockFactory.createGameplayConfig();
        IAging aging = agingFactory.createAging(gameplayConfigMock.getAgingGameConfig());
        Assertions.assertNotNull(aging);
    }

    @Test
    public void createLeagueScheduleTest(){
        LeagueModelMockAbstractFactory leagueMockFactory = LeagueModelMockAbstractFactory.instance();
        AgingTestAbstractFactory agingMockFactory = AgingTestAbstractFactory.instance();
        AgingMock agingMock = agingMockFactory.createAgingMock();
        ILeagueSchedule leagueSchedule = agingFactory.createLeagueSchedule(agingMock.retirementLeagueMock());
        Assertions.assertNotNull(leagueSchedule);
    }
}
