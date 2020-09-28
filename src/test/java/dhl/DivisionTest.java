package dhl;

import dhl.leagueModel.InitializeObjectFactory;
import dhl.leagueModel.interfaceModel.IDivision;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DivisionTest {
    InitializeObjectFactory initObj;
    IDivision division;
    @BeforeEach()
    public void initObject(){
        initObj = new InitializeObjectFactory();
        division= initObj.createDivision();
    }

    @Test
    public void DivisionDefaultConstructorTest(){
        Assert.assertTrue(division.getDivisionName().isEmpty());
    }

    @Test
    public void getDivisionNameTest(){
        division.setDivisionName("Atlantic");
        Assert.assertEquals("Atlantic",division.getDivisionName());
    }
    @Test
    public void setDivisionNameTest(){
        division.setDivisionName("Pacific");
        Assert.assertEquals("Pacific",division.getDivisionName());
    }

    @AfterEach()
    public void destroyObject(){
        initObj = null;
        division= null;
    }
}
