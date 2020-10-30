package dhl.trade.Interface;

import dhl.importJson.Interface.IGameConfig;
import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.ITeam;

public interface ITradeController {

    public void startTrading();

    public ITeam getUserTeam();

}
