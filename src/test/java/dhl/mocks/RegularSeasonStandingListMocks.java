package dhl.mocks;

import dhl.businessLogic.leagueModel.GeneralManager;
import dhl.businessLogic.leagueModel.Team;
import dhl.businessLogic.leagueModel.interfaceModel.ICoach;
import dhl.businessLogic.leagueModel.interfaceModel.IGeneralManager;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogic.simulationStateMachine.states.standings.Standings;
import dhl.businessLogic.simulationStateMachine.states.standings.interfaces.IStandings;

import java.util.ArrayList;
import java.util.List;

public class RegularSeasonStandingListMocks {

    LeagueObjectModelMocks mockLeagueObjectModel;
    ICoach coach;
    List<IPlayer> players;
    IGeneralManager manager;

    public RegularSeasonStandingListMocks() {
        mockLeagueObjectModel = new LeagueObjectModelMocks();
        coach = mockLeagueObjectModel.getSingleCoach();
        players = mockLeagueObjectModel.get20FreeAgentArrayMock();
        manager = new GeneralManager("Harry", "normal");
    }

    public List<IStandings> generalSeasonStandings() {
        ITeam team = new Team("Maverick", manager, coach, players);
        ITeam team2 = new Team("Denver", manager, coach, players);
        ITeam team3 = new Team("Vine", manager, coach, players);
        ITeam team4 = new Team("Loki", manager, coach, players);

        List<IStandings> standings = new ArrayList<>();

        IStandings standings1 = new Standings();
        standings1.setTeam(team);
        standings1.setPoints(10);
        standings1.setWins(5);
        standings1.setLoss(4);
        standings1.setGamesPlayed(9);

        IStandings standings2 = new Standings();
        standings2.setTeam(team2);
        standings2.setPoints(10);
        standings2.setWins(5);
        standings2.setLoss(5);
        standings2.setGamesPlayed(10);

        IStandings standings3 = new Standings();
        standings3.setTeam(team3);
        standings3.setPoints(8);
        standings3.setWins(4);
        standings3.setLoss(5);
        standings3.setGamesPlayed(9);

        IStandings standings4 = new Standings();
        standings4.setTeam(team4);
        standings4.setPoints(8);
        standings4.setWins(4);
        standings4.setLoss(5);
        standings4.setGamesPlayed(9);

        standings.add(standings1);
        standings.add(standings2);
        standings.add(standings3);
        standings.add(standings4);

        return standings;
    }
}
