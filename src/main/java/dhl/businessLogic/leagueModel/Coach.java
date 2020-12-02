package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.interfaceModel.ICoach;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Coach implements ICoach {
    private static final Logger logger = LogManager.getLogger(Coach.class);
    private String name;
    private double skating;
    private double shooting;
    private double checking;
    private double saving;

    public Coach() {
        name = "";
    }

    public Coach(String coachName, double skating, double shooting, double checking, double saving) {
        this();
        logger.info("Creating coach object" + coachName);
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

    public boolean isCoachStatInvalid(double statValue) {
        if (statValue < 0 || statValue > 1) {
            logger.debug("Coach: " + name + " have invalid statistics");
            return true;
        }
        return false;
    }
}
