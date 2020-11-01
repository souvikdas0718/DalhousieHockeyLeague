package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.ICoach;
import dhl.leagueModel.interfaceModel.IValidation;

public class Coach implements ICoach {
    private String coachName;
    private double skating;
    private double shooting;
    private double checking;
    private double saving;

    public Coach(){
        coachName="";
    }

    public Coach(String coachName,double skating,double shooting,double checking,double saving){
       this.coachName=coachName;
        this.skating=skating;
        this.shooting=shooting;
        this.checking=checking;
        this.saving=saving;
    }

    public String getCoachName() {
        return coachName;
    }

    public double getSkating() {
        return skating;
    }

    public double getShooting() {
        return shooting;
    }

    public double getChecking() {
        return checking;
    }

    public double getSaving() {
        return saving;
    }

    public boolean checkIfCoachValid(IValidation validation) throws Exception{
        validation.isStringEmpty(coachName,"Coach name");
        checkCoachStatistics();
        return true;
    }

    public void checkCoachStatistics() throws Exception{
        if(isCoachStatInvalid(saving) || isCoachStatInvalid(checking) || isCoachStatInvalid(shooting) || isCoachStatInvalid(skating)){
            throw new Exception("Coach statistics must be between 0 and 1");
        }
    }

    public boolean isCoachStatInvalid(double statValue){
        if(statValue<0 || statValue >1){
            return true;
        }
        return false;
    }
}
