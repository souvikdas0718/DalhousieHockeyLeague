package dhl.tradingTest;

import dhl.leagueModel.interfaceModel.ITeam;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.trading.Interface.ITradeAcceptReject;
import org.junit.jupiter.api.BeforeEach;

public class TradeAcceptRejectTest {

    ITradeAcceptReject testClassObject;
    LeagueObjectModelMocks mocks;
    @BeforeEach
    public void initObject(){
        mocks = new LeagueObjectModelMocks();
        ITeam offeringTeam = mocks.getTeamObjectMock();
        ITeam receivingTeam = mocks.getTeamObjectMock();
        //ITradeOffer tadeOffer = new SwapingTradeOffer(offeringTeam,receivingTeam,);
        //testClassObject = new TradeAcceptReject();
    }
}
