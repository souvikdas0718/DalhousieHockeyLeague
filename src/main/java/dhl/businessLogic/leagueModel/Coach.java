package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.interfaceModel.ICoach;
import dhl.businessLogic.leagueModel.interfaceModel.IValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Coach implements ICoach {
    private String name;
    private double skating;
    private double shooting;
    private double checking;
    private double saving;
    private static final Logger logger = LogManager.getLogger(Coach.class);

    public Coach() {
        name = "";
    }

    public Coach(String coachName, double skating, double shooting, double checking, double saving) {
        this.name = coachName;
        this.skating = skating;
        this.shooting = shooting;
        this.checking = checking;
        this.saving = saving;
    }

    public String getCoachName() {
        return name;
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

    public boolean checkIfCoachValid(IValidation validation) throws Exception {
        validation.isStringEmpty(name, "Coach name");
        checkCoachStatistics();
        return true;
    }

    public void checkCoachStatistics() throws Exception {
        if (isCoachStatInvalid(saving) || isCoachStatInvalid(checking) || isCoachStatInvalid(shooting) || isCoachStatInvalid(skating)) {
            logger.error( "Coach: " + name + " have invalid statistics" );
            throw new Exception("Coach statistics must be between 0 and 1");
        }
    }

    public boolean isCoachStatInvalid(double statValue) {
        if (statValue < 0 || statValue > 1) {
            return true;
        }
        return false;
    }
}
