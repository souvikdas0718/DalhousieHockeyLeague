package dhl.businessLogic.simulationStateMachine.states.standings.factory;

import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandingSystem;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandings;

public abstract class StandingsAbstractFactory {

    private static StandingsAbstractFactory uniqueInstance = null;

    protected StandingsAbstractFactory() {

    }

    public static StandingsAbstractFactory instance() {
        if (null == uniqueInstance) {
            uniqueInstance = new StandingsFactory();
        }
        return uniqueInstance;
    }

    public abstract IStandings getStandings();

    public abstract IStandingSystem getStandingSystem();
}
