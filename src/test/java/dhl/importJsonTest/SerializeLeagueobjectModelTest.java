package dhl.importJsonTest;

import dhl.database.interfaceDB.ILeagueObjectModelData;
import dhl.leagueModel.Coach;
import dhl.importJson.SerializeLeagueobjectModel;
import dhl.leagueModel.Team;
import dhl.leagueModel.interfaceModel.ICoach;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;
import dhl.leagueModelTests.MockDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class SerializeLeagueobjectModelTest {
    SerializeLeagueobjectModel objSerialize = new SerializeLeagueobjectModel();

    @Test
    void serializeLeagueObjectModelTest() throws Exception{
        ILeagueObjectModelData mockDb=new MockDatabase();
        ArrayList<IPlayer> players= new ArrayList<>();
        ICoach headCoach = new Coach("Todd McLellan",0.1,0.5,1.0,0.2);
        ITeam newlyCreatedTeam=new Team("Nova Scotia","Mathew",headCoach,players);
        String str = objSerialize.serializeLeagueObjectModel
                (mockDb.loadLeagueModel("Dhl",""));
        Assertions.assertNotNull(str);
    }
}
