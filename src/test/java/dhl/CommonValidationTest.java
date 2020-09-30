package dhl;

import dhl.leagueModel.CommonValidation;
import dhl.leagueModel.InitializeObjectFactory;
import dhl.leagueModel.Player;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.IValidation;
import org.junit.Assert;
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
        Assert.assertTrue(error.getMessage().contains("Team name cannot be empty"));
    }


    @Test
    public void isListEmpty(){
        Exception error= Assertions.assertThrows(Exception.class,() ->{
            List<IPlayer> players=new ArrayList<>();
            commonValidation.isListEmpty(players,"players");
        });
        Assert.assertTrue(error.getMessage().contains("Please add players"));
    }

    @AfterEach()
    public void destroyObject(){
        commonValidation = null;
    }
}
