package dhl.businessLogicTest.simulationStateMachineTest.states.seasonSimulationTest;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.GameContext;
import dhl.businessLogic.simulationStateMachine.SimulationContext;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.SimulateGameState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SimulateGameStateTest {
    SimulationContext simulationContext;
    SimulateGameState simulateGameState;
    GameContext gameState;
    List<ITeam> injuryCheckTeams;
    LeagueObjectModelMocks modelMocks;

    @BeforeEach
    public void initObject() {
        gameState = new GameContext();
        simulationContext = new SimulationContext(gameState);
        modelMocks = new LeagueObjectModelMocks();
        injuryCheckTeams = modelMocks.getTeamArrayMock();
    }

    @Test
    public void SimulateGameStateTest() {
        simulateGameState = new SimulateGameState(simulationContext);
        Assertions.assertNotNull(simulateGameState.getSimulationContext());
    }

    @Test
    public void getSimulationContextTest() {
        simulateGameState = new SimulateGameState(simulationContext);
        Assertions.assertNotNull(simulateGameState.getSimulationContext());
    }

    @Test
    public void setSimulationContextTest() {
        simulateGameState = new SimulateGameState(simulationContext);
        simulationContext = new SimulationContext(gameState);
        simulationContext.setYear(2021);
        simulateGameState.setSimulationContext(simulationContext);
        Assertions.assertTrue(simulateGameState.getSimulationContext().getYear() == 2021);
    }


    @Test
    public void seasonStateEntryProcessTest() {

    }

    @Test
    public void seasonStateProcessTest() {

    }

    @Test
    public void seasonStateExitProcessTest() {
//        LocalDate startOfSimulation = LocalDate.of(2020, 9, 30);
//        LocalDate currentDate = LocalDate.now();
//        long numberOfDays = DAYS.between(startOfSimulation, currentDate);
//        simulationContext.setStartOfSimulation(startOfSimulation);
//        simulationContext.setNumberOfDays((int)numberOfDays);
//        simulationContext.setYear(2020);
//        simulationContext.setTeamsPlayingInGame(injuryCheckTeams);
        simulateGameState = new SimulateGameState(simulationContext);
        simulateGameState.seasonStateExitProcess();
        Assertions.assertNotNull(simulateGameState.getSimulationContext().getTeamsPlayingInGame());
        Assertions.assertTrue(simulateGameState.getSimulationContext().getCurrentSimulation() == simulateGameState.getSimulationContext().getInjuryCheck());

//        this.simulationContext.setTeamsPlayingInGame(injuryCheckTeams);
//        simulationContext.setCurrentSimulation(simulationContext.getInjuryCheck());

    }
}
