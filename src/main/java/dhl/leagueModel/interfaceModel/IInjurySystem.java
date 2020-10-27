package dhl.leagueModel.interfaceModel;

import dhl.importJson.Interface.IGameConfig;

import java.util.Date;

public interface IInjurySystem {
    public boolean isInjured();

    public void setInjured(boolean injured);

    public Date getInjuryDate();

    public void setInjuryDate(Date injuryDate);

    public int getNumberOfDaysInjured();

    public void setNumberOfDaysInjured(int numberOfDaysInjured);

    public IInjurySystem checkIfPlayerInjured(IGameConfig gameConfig, Date currentDate);

}
