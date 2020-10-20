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
        this.setCoachName(coachName);
        this.setSkating(skating);
        this.setShooting(shooting);
        this.setChecking(checking);
        this.setSaving(saving);
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public double getSkating() {
        return skating;
    }

    public void setSkating(double skating) {
        this.skating = skating;
    }

    public double getShooting() {
        return shooting;
    }

    public void setShooting(double shooting) {
        this.shooting = shooting;
    }

    public double getChecking() {
        return checking;
    }

    public void setChecking(double checking) {
        this.checking = checking;
    }

    public double getSaving() {
        return saving;
    }

    public void setSaving(double saving) {
        this.saving = saving;
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
