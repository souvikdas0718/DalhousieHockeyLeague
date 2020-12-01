package dhl.businessLogic.teamRosterUpdater;

import dhl.businessLogic.teamRosterUpdater.interfaces.ITeamRosterUpdater;
import dhl.inputOutput.ui.interfaces.IUserInputOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class RosterUpdaterAbstractFactory {

    private static RosterUpdaterAbstractFactory uniqueInstance = null;
    private static final Logger logger = LogManager.getLogger(RosterUpdaterAbstractFactory.class);

    protected RosterUpdaterAbstractFactory() {

    }

    public static RosterUpdaterAbstractFactory instance() {
        if (null == uniqueInstance) {
            logger.debug("RosterUpdaterAbstractFactory's instance created");
            uniqueInstance = new RosterUpdaterConcreteFactory();
        }
        logger.debug("RosterUpdaterAbstractFactory's instance accessed");
        return uniqueInstance;
    }

    public static void setFactory(RosterUpdaterAbstractFactory factory) {
        logger.debug("RosterUpdaterAbstractFactory's instance updated");
        uniqueInstance = factory;
    }

    public abstract ITeamRosterUpdater createAiTeamRosterUpdater();

    public abstract ITeamRosterUpdater createUpdateUserTeamRoster(IUserInputOutput ioObject);
}
