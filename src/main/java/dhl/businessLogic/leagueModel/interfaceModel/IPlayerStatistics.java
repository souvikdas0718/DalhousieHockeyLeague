package dhl.businessLogic.leagueModel.interfaceModel;

import java.time.LocalDate;

public interface IPlayerStatistics {

    int getAge();

    void setAge(int age);

    int getSkating();

    void setSkating(int skating);

    int getShooting();

    void setShooting(int shooting);

    int getChecking();

    void setChecking(int checking);

    int getSaving();

    void setSaving(int saving);

    boolean isStatValueInvalid(double statValue);

    void setDateOfBirth(int birthDay, int birthMonth, int birthYear);

    LocalDate getDateOfBirth();

    void calculateCurrentAge(LocalDate date);

    void checkStatDecayChance(IGameConfig gameConfig);
}
