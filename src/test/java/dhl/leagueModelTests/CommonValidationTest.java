package dhl.leagueModelTests;

import dhl.leagueModel.CommonValidation;
import dhl.leagueModel.Player;
import dhl.leagueModel.PlayerStatistics;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.IPlayerStatistics;
import dhl.leagueModel.interfaceModel.IValidation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class CommonValidationTest {
   IValidation commonValidation;

    @BeforeEach()
    public void initObject(){
        commonValidation = new CommonValidation();
    }

    @Test
    public void isStringEmptyTest(){
        Exception error= Assertions.assertThrows(Exception.class,() ->{
            commonValidation.isStringEmpty("","Team");
        });
        Assertions.assertTrue(error.getMessage().contains("Team name cannot be empty"));
    }

    @Test
    public void isListEmptyTest(){
        Exception error= Assertions.assertThrows(Exception.class,() ->{
            List<IPlayer> players=new ArrayList<>();
            commonValidation.isListEmpty(players,"players");
        });
        Assertions.assertTrue(error.getMessage().contains("Please add players"));
    }

    @Test
    public void isListNotEmptyTest() throws Exception{
        List<IPlayer> players=new ArrayList<>();
        IPlayerStatistics playerStatistics =new PlayerStatistics(20,10,10,10,0);
        IPlayer player1=new Player("Rhea","forward",false,playerStatistics);
        players.add(player1);
        IPlayer player2=new Player("Noah","defense",true,playerStatistics);
        players.add(player2);
        commonValidation.isListEmpty(players,"players");
       Assertions.assertTrue(players.size()!=0);
    }

    @AfterEach()
    public void destroyObject(){
        commonValidation = null;
    }

}
