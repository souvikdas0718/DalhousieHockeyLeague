package dhl.businessLogic.simulationStateMachine.states.standings.factory;

import dhl.businessLogic.simulationStateMachine.states.standings.StandingSystem;
import dhl.businessLogic.simulationStateMachine.states.standings.Standings;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandingSystem;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandings;

public class StandingsFactory extends StandingsAbstractFactory {

    public IStandings getStandings() {
        return new Standings();
    }

    public IStandingSystem getStandingSystem() {
        return new StandingSystem();
    }
}
