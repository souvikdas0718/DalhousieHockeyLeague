package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.Mocks.GameConfigMock;
import dhl.Mocks.LeagueObjectModel20TeamMocks;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.ExecuteTradesState;
import dhl.businessLogicTest.tradeTest.TradeMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExecuteTradesStateTest {

    SimulationContext simulationContext;
    ExecuteTradesState executeTradesState;
    GameContext gameState;
    LeagueObjectModelMocks mockLeagueObjectModel;
    LeagueObjectModel20TeamMocks model20TeamMocks;
    GameConfigMock gameConfig;
    IGameConfig iGameConfig;
    IScheduler scheduler;
    ITeam goodTeamMock;
    ITeam badTeamMock;
    TradeMock tradeMock;

    @BeforeEach
    public void initObject() {
        gameState = new GameContext();
        simulationContext = new SimulationContext(gameState);
//        simulationContext.setInMemoryLeague(mockLeagueObjectModel.getLeagueObjectMock());
        executeTradesState = new ExecuteTradesState(simulationContext);
        mockLeagueObjectModel = new LeagueObjectModelMocks();
        model20TeamMocks = new LeagueObjectModel20TeamMocks();
        model20TeamMocks.leagueModel20TeamGeneralStandings();
        gameConfig = new GameConfigMock();
        iGameConfig = gameConfig.getGameConfigMock();
        scheduler = model20TeamMocks.leagueModel20TeamPlayoffsSchedules();
        tradeMock = new TradeMock();
        badTeamMock = tradeMock.getTeamWithBadPlayer();


    }

    @Test
    public void getSimulationContextTest() {
        executeTradesState = new ExecuteTradesState(simulationContext);
        Assertions.assertNotNull(executeTradesState.getSimulationContext());
    }

    @Test
    public void setSimulationContextTest() {
        executeTradesState = new ExecuteTradesState(simulationContext);
        simulationContext = new SimulationContext(gameState);
        simulationContext.setYear(2014);
        executeTradesState.setSimulationContext(simulationContext);
        Assertions.assertTrue(executeTradesState.getSimulationContext().getYear() == 2014);
    }

    @Test
    public void seasonStateEntryProcessTest() {

    }

    @Test
    public void seasonStateProcessTest() {
//        simulationContext = new SimulationContext(gameState);
////        System.out.println("Hello: "+iGameConfig.getTrading());
////        System.out.println("Hello: "+iGameConfig.getLossPoint());
////        System.out.println(Long.parseLong(iGameConfig.getValueFromOurObject(iGameConfig.getTrading(), iGameConfig.getLossPoint())));
////        System.out.println(Double.parseDouble(iGameConfig.getValueFromOurObject(iGameConfig.getTrading(), iGameConfig.getRandomTradeOfferChance())));
//        double badTeamStrengthBeforeTrade = badTeamMock.calculateTeamStrength();
//        executeTradesState = new ExecuteTradesState(simulationContext);
////        testClassObject.startEngine();
//        executeTradesState.seasonStateProcess();
//        Assertions.assertTrue(badTeamStrengthBeforeTrade < badTeamMock.calculateTeamStrength());

//        long configLossPoint = Long.parseLong(gameConfig.getValueFromOurObject(gameConfig.getTrading(), gameConfig.getLossPoint()));
//        double configRandomTradeChance = Double.parseDouble(gameConfig.getValueFromOurObject(gameConfig.getTrading(), gameConfig.getRandomTradeOfferChance()));
//        try {
//            for (IConference conference : leagueObjectModel.getConferences()) {
//                for (IDivision division : conference.getDivisions()) {
//                    for (ITeam team : division.getTeams()) {
//                        if (findLossPointOfTheTeam(team) > configLossPoint) {
//                            double randomNumber = Math.random();
//                            if (randomNumber > configRandomTradeChance) {
//                                tradeEngine.startEngine();
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            log.error("Error occured while trading"+ e.getMessage());
//            e.printStackTrace();
//        }

    }

    @Test
    public void seasonStateExitProcessTest() {
        executeTradesState.seasonStateExitProcess();
        Assertions.assertTrue(executeTradesState.getSimulationContext().getCurrentSimulation() == executeTradesState.getSimulationContext().getAging());
    }

//    public ITeam getUserTeam() {
//        return userTeam;
//    }
//
//    public int findLossPointOfTheTeam(ITeam team) {
//        int teamLossPoint = team.getLossPoint();
//        return teamLossPoint;
//    }
}
