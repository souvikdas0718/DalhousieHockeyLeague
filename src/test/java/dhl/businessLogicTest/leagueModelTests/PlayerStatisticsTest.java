package dhl.businessLogicTest.leagueModelTests;

import dhl.businessLogic.leagueModel.Player;
import dhl.businessLogic.leagueModel.PlayerStatistics;
import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;
import dhl.businessLogicTest.leagueModelTests.mocks.GameplayConfigMock;
import dhl.businessLogicTest.leagueModelTests.mocks.PlayerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class PlayerStatisticsTest {
    LeagueModelMockAbstractFactory factory;
    PlayerMock playerMock;
    IPlayerStatistics playerStatistics;
    IGameConfig gameConfig;
    LeagueModelMockAbstractFactory leagueMockFactory;

    @BeforeEach()
    public void initObject() {
        factory = LeagueModelMockAbstractFactory.instance();
        playerMock = factory.createPlayerMock();
        playerStatistics = playerMock.getPlayerStats();

        leagueMockFactory = LeagueModelMockAbstractFactory.instance();
        GameplayConfigMock gameplayConfigMock = leagueMockFactory.createGameplayConfig();
        gameConfig = gameplayConfigMock.getAgingGameConfig();
    }

    @Test
    void setAgeTest() {
        playerStatistics.setAge(20);
        Assertions.assertEquals(20, playerStatistics.getAge());
    }

    @Test
    void setSkatingTest() {
        playerStatistics.setSkating(5);
        Assertions.assertEquals(5, playerStatistics.getSkating());
    }

    @Test
    void setShootingTest() {
        playerStatistics.setShooting(10);
        Assertions.assertEquals(10, playerStatistics.getShooting());
    }

    @Test
    void setCheckingTest() {
        playerStatistics.setChecking(10);
        Assertions.assertEquals(10, playerStatistics.getChecking());
    }

    @Test
    void setSavingTest() {
        playerStatistics.setSaving(0);
        Assertions.assertEquals(0, playerStatistics.getSaving());
    }

    @Test
    void checkPlayerStatisticsTest(){
        IPlayerStatistics playerStat = playerMock.getInvalidPlayerStats();
        Assertions.assertTrue(playerStat.isStatValueInvalid(0));
    }

    @Test
    void setDateOfBirthTest(){
        LocalDate dob= LocalDate.of(2012,12,12);
        playerStatistics.setDateOfBirth(12,12,2012);
        Assertions.assertTrue(dob.equals(playerStatistics.getDateOfBirth()));
    }

    @Test
    void isStatValueValidTest()  {
        Assertions.assertFalse(playerStatistics.isStatValueInvalid(3));
    }

    @Test
    void checkStatDecayChanceTest(){
        PlayerMock playerMock = leagueMockFactory.createPlayerMock();
        IPlayerStatistics playerStatistics = playerMock.getPlayerStats();
        int checking = playerStatistics.getChecking();
        playerStatistics.checkStatDecayChance(gameConfig);
        Assertions.assertTrue(playerStatistics.getChecking()<checking);
    }

    @Test
    void calculateCurrentAgeTest(){
        PlayerMock playerMock = leagueMockFactory.createPlayerMock();
        IPlayerStatistics playerStatistics = playerMock.getPlayerStats();
        playerStatistics.setDateOfBirth(14,11,1995);
        LocalDate currentDate = LocalDate.of(2020,11,14);
        playerStatistics.calculateCurrentAge(currentDate);
        Assertions.assertEquals(25,playerStatistics.getAge());
    }

    @AfterEach()
    public void destroyObject() {
        playerStatistics = null;
    }
}
