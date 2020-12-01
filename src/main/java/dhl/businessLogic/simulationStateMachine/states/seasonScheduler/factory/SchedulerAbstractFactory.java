package dhl.businessLogic.simulationStateMachine.states.seasonScheduler.factory;


import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.ISeasonSchedule;

public abstract class SchedulerAbstractFactory {
    private static SchedulerAbstractFactory uniqueInstance = null;

    protected SchedulerAbstractFactory() {

    }

    public static SchedulerAbstractFactory instance() {
        if (null == uniqueInstance) {
            uniqueInstance = new SchedulerFactory();
        }
        return uniqueInstance;
    }

    public abstract IScheduler getScheduler();

    public abstract ISeasonSchedule getSeasonSchedule();

}
