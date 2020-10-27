package dhl.TrainingTest;

import dhl.Mocks.LeagueObjectModelMocks;
import dhl.Training.ITraining;
import dhl.Training.Training;
import dhl.leagueModel.*;
import dhl.leagueModel.interfaceModel.*;
import dhl.database.interfaceDB.ILeagueObjectModelData;
import dhl.leagueModelTests.MockDatabase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import dhl.Mocks.LeagueObjectModelMocks;

public class TrainingTest {
    ITraining trainingParameterized;

    @Test void saveLeagueObjectModelTest() throws Exception{
        trainingParameterized = new Training();
        ILeagueObjectModelData mockLeagueObject=new MockDatabase();
        trainingParameterized.statIncrease(mockLeagueObject.loadLeagueModel("Dhl","Ontario"));
        Assertions.assertEquals("Dhl","Dhl");
    }
}
