package dhl.tradeTest;

import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.trade.AiUserTrade;
import dhl.trade.ExchangingPlayerTradeOffer;
import dhl.trade.Interface.ITradeOffer;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

public class AiUserTradeTest {

    AiUserTrade testClassObject;
    TradeMock tradeMock;

    @BeforeEach
    public void initObject(){
        ITeam offeringTeam = tradeMock.getTeamWithBadPlayer();
        ITeam recevingTeam = tradeMock.getTeamWithGoodPlayer();

        ArrayList<IPlayer> offeringPlayers = new ArrayList<>();
        offeringPlayers.add(offeringTeam.getPlayers().get(0));

        ArrayList<IPlayer> playersWanted = new ArrayList<>();
        playersWanted.add(recevingTeam.getPlayers().get(0));

        ITradeOffer tradeOffer = new ExchangingPlayerTradeOffer(offeringTeam,recevingTeam,offeringPlayers,playersWanted);
        testClassObject = new AiUserTrade(tradeOffer);
    }


}
