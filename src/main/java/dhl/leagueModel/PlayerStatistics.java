package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.IPlayerStatistics;

public class PlayerStatistics implements IPlayerStatistics {
    private int age;
    private int skating;
    private int shooting;
    private int checking;
    private int saving;

    public PlayerStatistics(int age, int skating,int shooting,int checking, int saving){
        this.age=age;
        this.skating=skating;
        this.shooting=shooting;
        this.checking=checking;
        this.saving=saving;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSkating() {
        return skating;
    }

    public void setSkating(int skating) {
        this.skating = skating;
    }

    public int getShooting() {
        return shooting;
    }

    public void setShooting(int shooting) {
        this.shooting = shooting;
    }

    public int getChecking() {
        return checking;
    }

    public void setChecking(int checking) {
        this.checking = checking;
    }

    public int getSaving() {
        return saving;
    }

    public void setSaving(int saving) {
        this.saving = saving;
    }

    public void checkPlayerStatistics() throws Exception{
        if(isStatValueInvalid(saving) || isStatValueInvalid(checking) || isStatValueInvalid(shooting) || isStatValueInvalid(skating)){
            throw new Exception("Player statistics must be between 1 and 20");
        }
    }

    public boolean isStatValueInvalid(double statValue){
        if(statValue<1 || statValue >20){
            return true;
        }
        return false;
    }
}
