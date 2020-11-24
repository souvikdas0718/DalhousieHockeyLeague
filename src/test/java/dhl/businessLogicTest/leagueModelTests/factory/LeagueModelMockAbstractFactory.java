package dhl.businessLogicTest.leagueModelTests.factory;

import dhl.businessLogic.leagueModel.factory.LeagueModelFactory;
import dhl.businessLogic.leagueModel.interfaceModel.*;
import dhl.businessLogicTest.leagueModelTests.mocks.*;
import org.json.simple.JSONObject;

import java.util.List;

public abstract class LeagueModelMockAbstractFactory {

    private static LeagueModelMockAbstractFactory uniqueInstance = null;

    protected LeagueModelMockAbstractFactory() {

    }

    public static LeagueModelMockAbstractFactory instance() {
        if (null == uniqueInstance)
        {
            uniqueInstance = new LeagueModelMockFactory();
        }
        return uniqueInstance;
    }

    public static void setFactory(LeagueModelMockAbstractFactory factory) {
        uniqueInstance = factory;
    }

    public abstract PlayerMock createPlayerMock();

    public abstract TeamMock createTeamMock();

    public abstract CoachMock createCoachMock();

    public abstract FreeAgentMock createFreeAgentMock();

    public abstract ManagerMock createManagerMock();

    public abstract DivisionMock createDivisionMock();

    public abstract ConferenceMock createConferenceMock();

    public abstract LeagueMock createLeagueMock();

    public abstract GameplayConfigMock createGameplayConfig();


}
