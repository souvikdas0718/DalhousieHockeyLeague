package dhl.leagueModel;

import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.leagueModel.interfaceModel.IInjurySystem;

import java.util.Date;
import java.util.HashMap;

public class InjurySystem implements IInjurySystem {
    private boolean isInjured;
    private Date injuryDate;
    private int numberOfDaysInjured;

    public InjurySystem(){
        this.isInjured=false;
    }

    public InjurySystem(Date injuryDate, int numberOfDaysInjured){
        this.isInjured=true;
        this.injuryDate=injuryDate;
        this.numberOfDaysInjured=numberOfDaysInjured;
    }

    public boolean isInjured() {
        return isInjured;
    }

    public void setInjured(boolean injured) {
        isInjured = injured;
    }

    public Date getInjuryDate() {
        return injuryDate;
    }

    public void setInjuryDate(Date injuryDate) {
        this.injuryDate = injuryDate;
    }

    public int getNumberOfDaysInjured() {
        return numberOfDaysInjured;
    }

    public void setNumberOfDaysInjured(int numberOfDaysInjured) {
        this.numberOfDaysInjured = numberOfDaysInjured;
    }

    public IInjurySystem checkIfPlayerInjured(IGameConfig gameConfig, Date currentDate){
        IInjurySystem injurySystem=this;
        HashMap injuryConfig=gameConfig.getHashMap("injuries");
        double randomInjuryChance=(double)injuryConfig.get("randomInjuryChance")*100;
        int injuryDaysLow=(int)(long)injuryConfig.get("injuryDaysLow");
        int injuryDaysHigh=(int)(long)injuryConfig.get("injuryDaysHigh");

        double ramdomNumber =  Math.random();
        ramdomNumber = ramdomNumber * 100;
        if(ramdomNumber <= randomInjuryChance){
            int numberOfDaysInjured = (int )(Math.random() * (injuryDaysHigh - injuryDaysLow + 1) + injuryDaysLow);
            injurySystem=new InjurySystem(currentDate,numberOfDaysInjured);
        }
        return injurySystem;
    }

}
