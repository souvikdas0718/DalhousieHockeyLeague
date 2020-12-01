package dhl.businessLogicTest.leagueModelTests.factory;

import dhl.businessLogicTest.leagueModelTests.mocks.*;


public class LeagueModelMockFactory extends LeagueModelMockAbstractFactory {

    public PlayerMock createPlayerMock() {
        return new PlayerMock();
    }

    public TeamMock createTeamMock() {
        return new TeamMock();
    }

    public CoachMock createCoachMock() {
        return new CoachMock();
    }

    public FreeAgentMock createFreeAgentMock(){
        return new FreeAgentMock();
    }

    public ManagerMock createManagerMock(){
        return new ManagerMock();
    }

    public DivisionMock createDivisionMock(){
        return new DivisionMock();
    }

    public ConferenceMock createConferenceMock(){
        return new ConferenceMock();
    }

    public LeagueMock createLeagueMock(){
        return new LeagueMock();
    }

    public GameplayConfigMock createGameplayConfig(){
        return new GameplayConfigMock();
    }


}
