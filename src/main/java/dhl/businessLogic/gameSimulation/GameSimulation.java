package dhl.businessLogic.gameSimulation;

import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

import java.util.HashMap;

public class GameSimulation implements IGameSimulation {

    public String winner;

    public String getWinner() {
        return this.winner;
    }

    public void simulateGame(ITeam teamOne, ITeam teamTwo) {
        Subject subject = new Subject();

        GoalObserver goals = new GoalObserver(subject);
        SaveObserver saves = new SaveObserver(subject);
        PenaltyObserver penalities = new PenaltyObserver(subject);
        ShotObserver shots = new ShotObserver(subject);

        GameSimulationAbstractFactory factory = GameSimulationAbstractFactory.instance();
        IGameSimulationAlgorithm gameSimulationAlgorithm = factory.createGameSimulationAlgorithm();
        HashMap<String, Integer> mapResult = gameSimulationAlgorithm.getResultOfGame(teamOne, teamTwo);
        subject.setState(mapResult);

        winner = mapResult.get("Winner").toString();

        goals.print();
        saves.print();
        penalities.print();
        shots.print();
    }
}

