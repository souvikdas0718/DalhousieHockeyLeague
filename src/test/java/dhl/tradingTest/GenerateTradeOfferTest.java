package dhl.tradingTest;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.trading.GenerateTradeOffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class GenerateTradeOfferTest {

    GenerateTradeOffer testClassObject;
    LeagueObjectModelMocks mockObject;


    @BeforeEach
    public void initObject(){
        mockObject = new LeagueObjectModelMocks();
        // TODO: 27-10-2020 make mock for trade config 
        //testClassObject = new GenerateTradeOffer();
    }


    @Test
    public void sortPlayerListTest() throws Exception {
        ArrayList<IPlayer> players = testClassObject.sortPlayerList(mockObject.getTeamObjectMock());
        Assertions.assertTrue(players.get(0).getPlayerStrength() <= players.get(1).getPlayerStrength());
    }
}
