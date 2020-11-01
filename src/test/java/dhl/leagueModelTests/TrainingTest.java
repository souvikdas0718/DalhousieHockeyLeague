package dhl.leagueModelTests;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.leagueModel.interfaceModel.ITraining;
import dhl.leagueModel.Training;
import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;
import dhl.database.interfaceDB.ILeagueObjectModelDB;
import dhl.leagueModelTests.MockDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class TrainingTest {
    ITraining trainingParameterized;
    LeagueObjectModelMocks mocks;

    @BeforeEach
    public void initObject(){
        IInjurySystem injurySystem = new InjurySystem();
        trainingParameterized = new Training(injurySystem);
        mocks = new LeagueObjectModelMocks();
    }

    @Test
    public void statIncreaseTest() throws Exception{

        ILeagueObjectModelDB mockLeagueObject=new MockDatabase();
        ILeagueObjectModel newLeagueObject=new LeagueObjectModel();
        newLeagueObject = trainingParameterized.findPlayersForStatIncrease(mockLeagueObject.loadLeagueModel("Dhl","Ontario"));

        Assertions.assertNotNull("Dhl", newLeagueObject.getLeagueName());
    }
    public void updatePlayerStatsTest() throws Exception{
        List<IPlayer> updatedPlayersList=new ArrayList<>();
        String [] randomValues = {"0.1","0.2","0.3","0.4"};
        updatedPlayersList = trainingParameterized.updatePlayerStats(updatedPlayersList, mocks.getSingleCoach(), randomValues);

        for(int i=0; i<updatedPlayersList.size(); i++){
            int skating = updatedPlayersList.get(i).getPlayerStats().getSkating();
            Assertions.assertEquals(11,updatedPlayersList.get(i).getPlayerStats().getSkating());
            Assertions.assertEquals(11,updatedPlayersList.get(i).getPlayerStats().getShooting());
            Assertions.assertEquals(11,updatedPlayersList.get(i).getPlayerStats().getChecking());
            Assertions.assertEquals(1,updatedPlayersList.get(i).getPlayerStats().getSaving());

        }

    }
}
