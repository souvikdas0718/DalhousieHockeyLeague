package dhl.businessLogicTest.leagueModelTests;

import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;
import dhl.businessLogic.leagueModel.GenerateDraftPlayers;
import dhl.businessLogic.simulationStateMachine.states.seasonSimulation.factory.SimulationStateAbstractFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class GenerateDraftPlayersTest {
    private static final int[][] FORWARDSTATRANGE = { { 12, 20 }, { 12, 20 },{ 9, 18 },{ 1, 7 } };
    SimulationStateAbstractFactory simulationFactory;
    GenerateDraftPlayers generateDraftPlayers;
    @BeforeEach()
    public void initObject() {
        simulationFactory = SimulationStateAbstractFactory.instance();
        generateDraftPlayers =  (GenerateDraftPlayers)simulationFactory.getGeneratePlayers() ;
    }

    @Test
    public void generateDraftPlayersTest(){
        generateDraftPlayers.generateDraftPlayers();
        Assertions.assertEquals(224,generateDraftPlayers.getDraftPlayers().size());
    }

    @Test
    public void generateStatForPlayerTest(){
        IPlayerStatistics playerStatistics = generateDraftPlayers.generateStatForPlayer("forward");
        playerStatistics.calculateCurrentAge(LocalDate.of(2020,12,1));
        int age = playerStatistics.getAge();
        Assertions.assertTrue(age >=18);
        Assertions.assertTrue(age<=21);
    }

    @Test
    public void getSkewedStatMinValueTest(){
        Assertions.assertEquals(15,generateDraftPlayers.getSkewedStatMinValue(9,18));
    }

    @Test
    public void getPlayerStatisticsTest(){
        IPlayerStatistics playerStatistics = generateDraftPlayers.getPlayerStatistics(FORWARDSTATRANGE);
        Assertions.assertTrue(playerStatistics.getChecking()>=15 && playerStatistics.getChecking()<=18 );
    }

    @Test
    public void generateStatForGoaliePlayerTest(){
        IPlayerStatistics playerStatistics = generateDraftPlayers.generateStatForPlayer("goalie");
        playerStatistics.calculateCurrentAge(LocalDate.of(2020,12,31));
        int age = playerStatistics.getAge();
        Assertions.assertTrue(age >=18 && age<=21);
    }

    @Test
    public void generateStatForDefensePlayerTest(){
        IPlayerStatistics playerStatistics = generateDraftPlayers.generateStatForPlayer("defense");
        playerStatistics.calculateCurrentAge(LocalDate.of(2020,12,31));
        int age = playerStatistics.getAge();
        Assertions.assertTrue(age >=18 && age<=21);
    }
}
