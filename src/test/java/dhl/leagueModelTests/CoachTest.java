package dhl.leagueModelTests;

import dhl.leagueModel.Coach;
import dhl.leagueModel.CommonValidation;
import dhl.leagueModel.PlayerStatistics;
import dhl.leagueModel.interfaceModel.ICoach;
import dhl.leagueModel.interfaceModel.IPlayerStatistics;
import dhl.leagueModel.interfaceModel.IValidation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CoachTest {
    ICoach coachDefault;
    ICoach coach;
    IValidation validate;

    @BeforeEach()
    public void initObject(){
        validate=new CommonValidation();
        coachDefault = new Coach();
        coach =new Coach("Mary Smith",0.1,0.2,0.5,1.0);
    }

    @Test
    public void CoachDefaultConstructorTest(){
        Assertions.assertTrue(coachDefault.getCoachName().isEmpty());
    }

    @Test
    void setCoachNameTest() {
        coach.setCoachName("Harry Mathew");
        Assertions.assertEquals("Harry Mathew",coach.getCoachName());
    }

    @Test
    void setSkatingTest() {
        coach.setSkating(0.1);
        Assertions.assertEquals(0.1,coach.getSkating());
    }

    @Test
    void setShootingTest() {
        coach.setShooting(1.0);
        Assertions.assertEquals(1.0,coach.getShooting());
    }

    @Test
    void setCheckingTest() {
        coach.setChecking(0.5);
        Assertions.assertEquals(0.5,coach.getChecking());
    }

    @Test
    void setSavingTest() {
        coach.setSaving(0.2);
        Assertions.assertEquals(0.2,coach.getSaving());
    }

    @Test
    void checkIfCoachValidTest() throws Exception{

       Assertions.assertTrue(coach.checkIfCoachValid(validate));
    }

    @AfterEach()
    public void destroyObject(){
        coach=null;
    }
}
