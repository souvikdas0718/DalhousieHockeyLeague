package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.interfaceModel.IGameConfig;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.Period;

public class PlayerStatistics implements IPlayerStatistics {
    private static final Logger logger = LogManager.getLogger(PlayerStatistics.class);
    private int age;
    private int skating;
    private int shooting;
    private int checking;
    private int saving;
    private LocalDate dateOfBirth;

    public PlayerStatistics(int skating, int shooting, int checking, int saving) {
        logger.info("Creating player statistics object");
        this.age = age;
        this.skating = skating;
        this.shooting = shooting;
        this.checking = checking;
        this.saving = saving;
    }

    public int getAge() {
        return age;
    }

    public void setDateOfBirth(int birthDay, int birthMonth, int birthYear) {
        dateOfBirth = LocalDate.of(birthYear, birthMonth, birthDay);
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void calculateCurrentAge(LocalDate currentDate) {
        logger.debug("Calculating player age based on birthday. Current date is" + currentDate);
        this.age = Period.between(dateOfBirth, currentDate).getYears();
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

    public boolean isStatValueInvalid(double statValue) {
        if (statValue < 1 || statValue > 20) {
            logger.debug("Stat value is invalid" + statValue);
            return true;
        }
        logger.debug("Stat value is valid");
        return false;
    }

    public void checkStatDecayChance(IGameConfig gameConfig) {
        logger.info("Checking if age affects player stat value");
        double statDecayChance = Double.parseDouble(gameConfig.getValueFromOurObject(gameConfig.getAging(), gameConfig.getStatDecayChance())) * 100;
        double ramdomNumber = Math.random();
        ramdomNumber = ramdomNumber * 100;
        if (ramdomNumber <= statDecayChance) {
            logger.debug("Stat decreased");
            saving = decreaseStat(saving);
            shooting = decreaseStat(shooting);
            skating = decreaseStat(skating);
            checking = decreaseStat(checking);
        }
    }

    public int decreaseStat(int statValue) {
        if (statValue > 1) {
            statValue = statValue - 1;
        }
        return statValue;
    }
}
