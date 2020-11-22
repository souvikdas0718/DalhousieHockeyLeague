package dhl.businessLogicTest.leagueModelTests.mocks;

import dhl.businessLogic.leagueModel.PlayerPosition;
import dhl.businessLogic.leagueModel.PlayerStatistics;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.ICoach;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.businessLogicTest.leagueModelTests.factory.LeagueModelMockAbstractFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TeamMock {
    LeagueModelAbstractFactory factory;
    LeagueModelMockAbstractFactory mockFactory;

    public TeamMock(){
        factory = LeagueModelAbstractFactory.instance();
        mockFactory = LeagueModelMockAbstractFactory.instance();
    }

    public ITeam getTeam(){
        ICoach coach = factory.createCoach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        return factory.createTeam("Ontario", "Mathew", coach, getTeamPlayers());
    }

    public ITeam getTeamByName(String teamName){
        ICoach coach = factory.createCoach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        return factory.createTeam(teamName, "Mathew", coach, getTeamPlayers());
    }


    public ITeam getInvalidSizeTeam(){
        ICoach coach = factory.createCoach("Todd McLellan", 0.1, 0.5, 1.0, 0.2);
        List<IPlayer> players = getTeamPlayers();
        PlayerMock playerMock = mockFactory.createPlayerMock();
        players.add(playerMock.getPlayer());
        return factory.createTeam("Ontario", "Mathew", coach,players );
    }

    public List<IPlayer> getTeamPlayers(){
        List<IPlayer> playerList = new ArrayList<>();
        Random rand = new Random();

        for(int i=0;i<30;i++){
            IPlayerStatistics playerStatistics= new PlayerStatistics(20,rand.nextInt((20 - 1) + 1) + 1,rand.nextInt((20 - 1) + 1) + 1,rand.nextInt((20 - 1) + 1) + 1,rand.nextInt((20 - 1) + 1) + 1);
            IPlayer player;
            if(i==0){
                player= factory.createPlayer("Player"+i, PlayerPosition.FORWARD.toString(),true,playerStatistics);
            }
            else if(i< 16){
                player= factory.createPlayer("Player"+i,PlayerPosition.FORWARD.toString(),false,playerStatistics);
            }
            else if(i< (30- 4)){
                player = factory.createPlayer("Player"+i,PlayerPosition.DEFENSE.toString(),false,playerStatistics);
            }
            else{
                player= factory.createPlayer("Player"+i,PlayerPosition.GOALIE.toString(),false,playerStatistics);
            }
            playerList.add(player);
        }
        return playerList;
    }



}
