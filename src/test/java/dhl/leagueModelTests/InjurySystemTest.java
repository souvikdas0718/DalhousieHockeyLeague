package dhl.leagueModelTests;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.leagueModel.InjurySystem;
import dhl.leagueModel.Player;
import dhl.leagueModel.interfaceModel.IInjurySystem;
import dhl.leagueModel.interfaceModel.IPlayer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;


public class InjurySystemTest {
    IGameConfig gameConfig;
    IInjurySystem injurySystem;
    @BeforeEach()
    public void initObject(){
        LeagueObjectModelMocks leagueMock= new LeagueObjectModelMocks();
        gameConfig=leagueMock.getGameConfig();
        injurySystem=new InjurySystem();
    }

    @Test
    public void InjurySystemTest(){
       Assertions.assertFalse(injurySystem.isInjured());
    }

    @Test
    public void setInjuredTest(){
        injurySystem.setInjured(true);
        Assertions.assertTrue(injurySystem.isInjured());
    }

    @Test
    public void setNumberOfDaysInjured(){
        injurySystem.setNumberOfDaysInjured(10);
        Assertions.assertEquals(10,injurySystem.getNumberOfDaysInjured());
    }

    @Test
    public void setInjuryDateTest(){
        Date date=new Date();
        injurySystem.setInjuryDate(date);
        Assertions.assertEquals(date.getTime(),injurySystem.getInjuryDate().getTime());
    }

    @Test
    public void  checkIfPlayerInjuredTest(){
        IPlayer player=new Player();
        IInjurySystem injurySystem=player.getInjurySystem();
        injurySystem =injurySystem.checkIfPlayerInjured(gameConfig,new Date());
        Assertions.assertEquals(1,injurySystem.getNumberOfDaysInjured());
    }

    @AfterEach()
    public void destroyObject(){
        injurySystem=null;
        gameConfig= null;
    }


}
