package dhl.tradeTest;

import dhl.Mocks.GameConfigMock;
import dhl.Mocks.LeagueObjectModelMocks;
import dhl.InputOutput.importJson.Interface.IGameConfig;
import dhl.leagueModel.Team;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.trade.TradeController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TradeControllerTest {

    TradeController testClassObject;
    TradeMock tradeMock;
    LeagueObjectModelMocks leagueObjectModelMocks;
    ILeagueObjectModel ourLeague;
    GameConfigMock gameConfigMock;
    IGameConfig ourGameConfig;
    ITeam goodTeamMock,badTeamMock;

    @BeforeEach
    public void initObject(){
        leagueObjectModelMocks = new LeagueObjectModelMocks();
        tradeMock = new TradeMock();

        gameConfigMock = new GameConfigMock();
        ourGameConfig = gameConfigMock.getGameConfigMock();

        goodTeamMock = tradeMock.getTeamWithGoodPlayer();
        badTeamMock = tradeMock.getTeamWithBadPlayer();
        Team userTeam = (Team) tradeMock.getTeamWithBadPlayer();
        userTeam.setTeamName("UserTeam");
        ourLeague = leagueObjectModelMocks.getLeagueObjectMock();
        ourLeague.getConferences().get(0).getDivisions().get(0).getTeams().add(goodTeamMock);
        ourLeague.getConferences().get(0).getDivisions().get(0).getTeams().add(badTeamMock);
        ourLeague.getConferences().get(0).getDivisions().get(0).getTeams().add(userTeam);

        testClassObject = new TradeController(userTeam , ourLeague , ourGameConfig);

    }

    @Test
    public void startTradingTest(){
        testClassObject.startTrading();
        Assertions.assertTrue(badTeamMock.getLossPoint() == 0);
    }

    @Test
    public void findLossPointOfTheTeamTest(){
        int losspoint = testClassObject.findLossPointOfTheTeam(tradeMock.getTeamWithBadPlayer());
        Assertions.assertTrue(losspoint == 10);
    }

    @Test
    public void getUserTeamTest(){
        Assertions.assertTrue(testClassObject.getUserTeam().getTeamName().equals("UserTeam"));

    }
}
