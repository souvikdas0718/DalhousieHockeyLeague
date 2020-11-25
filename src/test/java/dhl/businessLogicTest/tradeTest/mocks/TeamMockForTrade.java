package dhl.businessLogicTest.tradeTest.mocks;

import dhl.businessLogic.leagueModel.*;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.factory.LeagueModelFactory;
import dhl.businessLogic.leagueModel.interfaceModel.ICoach;
import dhl.businessLogic.leagueModel.interfaceModel.IGeneralManager;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogicTest.tradeTest.mocks.factory.TradeMockAbstractFactory;

import java.util.ArrayList;

public class TeamMockForTrade {

    LeagueModelAbstractFactory leagueFactory;
    TradeMockAbstractFactory tradeMockFactory;

    public TeamMockForTrade(){
        LeagueModelAbstractFactory.setFactory(new LeagueModelFactory());
        leagueFactory = LeagueModelAbstractFactory.instance();

        tradeMockFactory = TradeMockAbstractFactory.instance();
    }

    public ITeam getTeamWithGoodPlayer() {
        ArrayList<IPlayer> strongPlayers = new ArrayList<>();
        strongPlayers.add(tradeMockFactory.createPlayerMockForTrade().getStrongPlayer("Player1", PlayerPosition.DEFENSE.toString()));
        strongPlayers.add(tradeMockFactory.createPlayerMockForTrade().getStrongPlayer("Player2", PlayerPosition.DEFENSE.toString()));
        strongPlayers.add(tradeMockFactory.createPlayerMockForTrade().getStrongPlayer("Player3", PlayerPosition.DEFENSE.toString()));

        ICoach headCoach = leagueFactory.createCoach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        IGeneralManager manager = leagueFactory.createGeneralManager("Mock Manager A", "normal");
        ITeam team = leagueFactory.createTeam("TeamWithGoodPlayer", manager, headCoach, strongPlayers);
        team.setLossPoint(10);
        return team;
    }

    public ITeam getTeamWithBadPlayer() {
        ArrayList<IPlayer> weakPlayers = new ArrayList<>();
        weakPlayers.add(tradeMockFactory.createPlayerMockForTrade().getWeakPlayer("WeakPlayer1", PlayerPosition.DEFENSE.toString()));
        weakPlayers.add(tradeMockFactory.createPlayerMockForTrade().getWeakPlayer("WeakPlayer2", PlayerPosition.DEFENSE.toString()));
        weakPlayers.add(tradeMockFactory.createPlayerMockForTrade().getWeakPlayer("WeakPlayer3", PlayerPosition.DEFENSE.toString()));
        weakPlayers.add(tradeMockFactory.createPlayerMockForTrade().getWeakPlayer("WeakPlayer4", PlayerPosition.DEFENSE.toString()));

        ICoach headCoach = leagueFactory.createCoach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        IGeneralManager manager = leagueFactory.createGeneralManager("Mock Manager A", "normal");
        ITeam team = leagueFactory.createTeam("TeamWithBadPlayer", manager, headCoach, weakPlayers);
        team.setLossPoint(10);

        return team;
    }

    public ITeam getTeamWithBadPosition(String position){
        ArrayList<IPlayer> playersList = new ArrayList<>();

        for (int i =0; i<10; i++){

            if (position.equals(PlayerPosition.GOALIE.toString())){
                IPlayer player = leagueFactory.createPlayer("Goalie"+i , position,false,
                        new PlayerStatistics( 1, 1, 1, 1));
                playersList.add(player);
                playersList.add(tradeMockFactory.createPlayerMockForTrade().getStrongPlayer("defence"+i, PlayerPosition.DEFENSE.toString()));
                playersList.add(tradeMockFactory.createPlayerMockForTrade().getStrongPlayer("forward"+i,PlayerPosition.FORWARD.toString()));
            }else if (position.equals(PlayerPosition.DEFENSE.toString())){
                IPlayer player = leagueFactory.createPlayer("defence"+i , position,false,
                        new PlayerStatistics( 1, 1, 1, 0));
                playersList.add(player);
                playersList.add(tradeMockFactory.createPlayerMockForTrade().getStrongPlayer("goalie"+i, PlayerPosition.GOALIE.toString()));
                playersList.add(tradeMockFactory.createPlayerMockForTrade().getStrongPlayer("forward"+i,PlayerPosition.FORWARD.toString()));
            }else if (position.equals(PlayerPosition.FORWARD.toString())){
                IPlayer player = leagueFactory.createPlayer("forward"+i , position,false,
                        new PlayerStatistics( 1, 1, 1, 0));
                playersList.add(player);
                playersList.add(tradeMockFactory.createPlayerMockForTrade().getStrongPlayer("defence"+i, PlayerPosition.DEFENSE.toString()));
                playersList.add(tradeMockFactory.createPlayerMockForTrade().getStrongPlayer("goalie"+i,PlayerPosition.GOALIE.toString()));
            }

        }

        ICoach headCoach = leagueFactory.createCoach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        IGeneralManager manager = leagueFactory.createGeneralManager("Mock Manager A", "normal");
        ITeam team = leagueFactory.createTeam("Bad_"+position+"_Team", manager, headCoach, playersList);
        team.setLossPoint(10);

        return team;
    }

}
