package dhl.tradingTest;

import dhl.leagueModel.Player;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.trading.Interface.ITradeAcceptReject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TradeAcceptRejectTest {

    ITradeAcceptReject testClassObject;
    LeagueObjectModelMocks leagueMockObject;

    @BeforeEach
    public void initObject(){
        leagueMockObject = new LeagueObjectModelMocks();
        ITeam offeringTeam = leagueMockObject.getTeamObjectMock();
        ITeam receivingTeam = leagueMockObject.getTeamObjectMock();
        //ITradeOffer tadeOffer = new SwapingTradeOffer(offeringTeam,receivingTeam,);
        //testClassObject = new TradeAcceptReject();
    }

    @Test
    public void tryDD(){
        ArrayList<IPlayer> a = new ArrayList<>();
        IPlayer player1 = new Player();player1.setPlayerName("player1");
        IPlayer player2 = new Player();player2.setPlayerName("player2   ");
        IPlayer player3 = new Player();player3.setPlayerName("player3");
        IPlayer player4 = new Player();player4.setPlayerName("player4");
        a.add(player1);a.add(player2);a.add(player3);a.add(player4);
        a.remove(player3);
        for(IPlayer p : a){
            System.out.println(p.getPlayerName());
        }
    }
}
