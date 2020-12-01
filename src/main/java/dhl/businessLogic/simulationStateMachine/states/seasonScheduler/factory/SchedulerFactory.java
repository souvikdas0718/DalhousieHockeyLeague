package dhl.businessLogic.simulationStateMachine.states.seasonScheduler.factory;

import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.Scheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.SeasonSchedule;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.IScheduler;
import dhl.businessLogic.simulationStateMachine.states.seasonScheduler.interfaces.ISeasonSchedule;

public class SchedulerFactory extends SchedulerAbstractFactory {

    public IScheduler getScheduler() {
        return new Scheduler();
    }

    public ISeasonSchedule getSeasonSchedule() {
        return new SeasonSchedule();
    }
}