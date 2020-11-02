package dhl.leagueModelTests;

import dhl.leagueModel.PlayerStatistics;
import dhl.leagueModel.interfaceModel.IPlayerStatistics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerStatisticsTest {
    IPlayerStatistics playerStatistics;

    @BeforeEach()
    public void initObject() {
        playerStatistics = new PlayerStatistics(20, 10, 10, 10, 10);
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
    void checkPlayerStatisticsTest() throws Exception {
        IPlayerStatistics playerStat = new PlayerStatistics(20, 10, 19, 0, 10);
        Exception errorMsg = Assertions.assertThrows(Exception.class, () -> {
            playerStat.checkPlayerStatistics();
        });
        Assertions.assertTrue(errorMsg.getMessage().contains("Player statistics must be between 1 and 20"));
    }

    @Test
    void isStatValueValidTest() throws Exception {
        Assertions.assertDoesNotThrow(() -> playerStatistics.checkPlayerStatistics());
    }

    @AfterEach()
    public void destroyObject() {
        playerStatistics = null;
    }
}
