package dhl.businessLogicTest.tradeTest;

import dhl.businessLogic.leagueModel.*;
import dhl.businessLogic.leagueModel.interfaceModel.ICoach;
import dhl.businessLogic.leagueModel.interfaceModel.IGeneralManager;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TradeMock {

    public ITeam getTeamWithGoodPlayer() {
        ArrayList<IPlayer> strongPlayers = new ArrayList<>();
        strongPlayers.add(getStrongPlayer("Player1"));
        strongPlayers.add(getStrongPlayer("Player2"));
        strongPlayers.add(getStrongPlayer("Player3"));

        ICoach headCoach = new Coach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        IGeneralManager manager = new GeneralManager("Mock Manager A", "normal");
        ITeam team = new Team("TeamWithGoodPlayer", manager, headCoach, strongPlayers);
        team.setLossPoint(10);
        return team;
    }

    public ITeam getTeamWithBadPlayer() {
        ArrayList<IPlayer> weakPlayers = new ArrayList<>();
        weakPlayers.add(getWeakPlayer("WeakPlayer1"));
        weakPlayers.add(getWeakPlayer("WeakPlayer2"));
        weakPlayers.add(getWeakPlayer("WeakPlayer3"));
        weakPlayers.add(getWeakPlayer("WeakPlayer4"));

        ICoach headCoach = new Coach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        IGeneralManager manager = new GeneralManager("Mock Manager A", "normal");
        ITeam team = new Team("TeamWithBadPlayer", manager, headCoach, weakPlayers);
        team.setLossPoint(10);

        return team;
    }

    public IPlayer getStrongPlayer(String playerName) {
        IPlayer player = new Player(playerName, "defense", false,
                new PlayerStatistics( 10, 10, 10, 0));

        return player;
    }

    public IPlayer getWeakPlayer(String playerName) {
        IPlayer player = new Player(playerName, PlayerPosition.DEFENSE.toString(), false,
                new PlayerStatistics( 1, 1, 1, 0));

        return player;
    }

    public ArrayList<IPlayer> get50FreeAgents() {
        List<IPlayer> freeAgents = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 250; i++) {
            if (i % 2 == 0) {
                freeAgents.add(new FreeAgent("Henry" + i, PlayerPosition.GOALIE.toString(),
                        new PlayerStatistics( r.nextInt(10), r.nextInt(10), r.nextInt(10), r.nextInt(10))));
            }
            else if(i % 3 == 0){
                freeAgents.add(new FreeAgent("Henry" + i, PlayerPosition.FORWARD.toString(),
                        new PlayerStatistics( r.nextInt(10), r.nextInt(10), r.nextInt(10), 0)));
            }
            else {
                freeAgents.add(new FreeAgent("Henry" + i, PlayerPosition.DEFENSE.toString(),
                        new PlayerStatistics( r.nextInt(10), r.nextInt(10), r.nextInt(10), 0)));
            }
        }
        return (ArrayList<IPlayer>) freeAgents;
    }
}

