package dhl.businessLogicTest.tradeTest.mocks;

import dhl.businessLogic.leagueModel.FreeAgent;
import dhl.businessLogic.leagueModel.PlayerPosition;
import dhl.businessLogic.leagueModel.PlayerStatistics;
import dhl.businessLogic.leagueModel.factory.LeagueModelAbstractFactory;
import dhl.businessLogic.leagueModel.factory.LeagueModelFactory;
import dhl.businessLogic.leagueModel.interfaceModel.IPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FreeAgentMockForTrade {

    LeagueModelAbstractFactory leagueFactory;

    public FreeAgentMockForTrade(){
        LeagueModelAbstractFactory.setFactory(new LeagueModelFactory());
        leagueFactory = LeagueModelAbstractFactory.instance();
    }

    public ArrayList<IPlayer> getListOfFreeAgents() {
        List<IPlayer> freeAgents = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 250; i++) {
            if (i % 2 == 0) {
                freeAgents.add(leagueFactory.createFreeAgent("Henry" + i, PlayerPosition.GOALIE.toString(),
                        new PlayerStatistics( r.nextInt(10), r.nextInt(10), r.nextInt(10), r.nextInt(10))));
            }
            else if(i % 3 == 0){
                freeAgents.add(leagueFactory.createFreeAgent("Henry" + i, PlayerPosition.FORWARD.toString(),
                        new PlayerStatistics( r.nextInt(10), r.nextInt(10), r.nextInt(10), 0)));
            }
            else {
                freeAgents.add(leagueFactory.createFreeAgent("Henry" + i, PlayerPosition.DEFENSE.toString(),
                        new PlayerStatistics( r.nextInt(10), r.nextInt(10), r.nextInt(10), 0)));
            }
        }
        return (ArrayList<IPlayer>) freeAgents;
    }
}
