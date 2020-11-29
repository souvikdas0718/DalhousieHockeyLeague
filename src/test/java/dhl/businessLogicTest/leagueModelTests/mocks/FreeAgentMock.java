package dhl.businessLogicTest.leagueModelTests.mocks;

import dhl.businessLogic.leagueModel.PlayerPosition;
import dhl.businessLogic.leagueModel.PlayerStatistics;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayerStatistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class FreeAgentMock {
    LeagueModelAbstractFactory factory;

    public FreeAgentMock(){ ;
        factory = LeagueModelAbstractFactory.instance();
    }

    public IPlayer getPlayer(){
        IPlayerStatistics playerStatistics = getPlayerStats();
        return factory.createFreeAgent("Noah", "forward", playerStatistics);
    }

    public IPlayer getPlayerWithoutName(){
        IPlayerStatistics playerStatistics = getPlayerStats();
        return factory.createFreeAgent("", "forward", playerStatistics);
    }

    public IPlayer getPlayerInvalidPosition(){
        IPlayerStatistics playerStatistics = getPlayerStats();
        return factory.createFreeAgent("Harry", "Leg side", playerStatistics);
    }

    public IPlayerStatistics getPlayerStats(){
        IPlayerStatistics playerStats= factory.createPlayerStatistics( 10, 10, 10, 10);
        playerStats.setAge(20);
        return playerStats;
    }

    public List<IPlayer> getFreeAgents(){
        List<IPlayer> freeAgents = new ArrayList<>();
        Random rand = new Random();

        for(int i=0;i<30;i++){
            IPlayerStatistics playerStatistics= new PlayerStatistics(rand.nextInt((20 - 1) + 1) + 1,rand.nextInt((20 - 1) + 1) + 1,rand.nextInt((20 - 1) + 1) + 1,rand.nextInt((20 - 1) + 1) + 1);
            playerStatistics.setDateOfBirth(14,11,1995);
            IPlayer freeAgent;
            if(i==0){
                freeAgent= factory.createFreeAgent("Player"+i, PlayerPosition.FORWARD.toString(),playerStatistics);
            }
            else if(i< 16){
                freeAgent= factory.createFreeAgent("Player"+i,PlayerPosition.FORWARD.toString(),playerStatistics);
            }
            else if(i< (30- 4)){
                freeAgent = factory.createFreeAgent("Player"+i,PlayerPosition.DEFENSE.toString(),playerStatistics);
            }
            else{
                freeAgent= factory.createFreeAgent("Player"+i,PlayerPosition.GOALIE.toString(),playerStatistics);
            }
            freeAgents.add(freeAgent);
        }
        return freeAgents;
    }

}
