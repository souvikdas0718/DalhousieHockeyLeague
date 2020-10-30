package dhl.tradeTest;

import dhl.leagueModel.Coach;
import dhl.leagueModel.Player;
import dhl.leagueModel.PlayerStatistics;
import dhl.leagueModel.Team;
import dhl.leagueModel.interfaceModel.ICoach;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;

import java.util.ArrayList;

public class TradeMock {


    public ITeam getTeamWithGoodPlayer(){
        ArrayList<IPlayer> strongPlayers = new ArrayList<>();
        strongPlayers.add(getStrongPlayer("Player1"));
        strongPlayers.add(getStrongPlayer("Player2"));
        strongPlayers.add(getStrongPlayer("Player3"));

        ICoach headCoach = new Coach("Todd McLellan",0.1,0.5,1.0,0.2);

        ITeam team = new Team("TeamWithGoodPlayer", "Mock Manager A", headCoach, strongPlayers );
        team.setLossPoint(10);
        return team;
    }

    public ITeam getTeamWithBadPlayer(){
        ArrayList<IPlayer> weakPlayers = new ArrayList<>();
        weakPlayers.add(getWeakPlayer("WeakPlayer1"));
        weakPlayers.add(getWeakPlayer("WeakPlayer2"));
        weakPlayers.add(getWeakPlayer("WeakPlayer3"));
        weakPlayers.add(getWeakPlayer("WeakPlayer4"));

        ICoach headCoach = new Coach("Todd McLellan",0.1,0.5,1.0,0.2);
        ITeam team = new Team("TeamWithBadPlayer", "Mock Manager A", headCoach, weakPlayers );
        team.setLossPoint(10);
        return team;
    }
    public IPlayer getStrongPlayer(String playerName){
        return new Player(playerName, "defense", false,
                new PlayerStatistics(25,10,10,10,0));
    }
    public IPlayer getWeakPlayer(String playerName){
        return new Player(playerName, "defense", false,
                new PlayerStatistics(25,1,1,1,0));
    }

}
