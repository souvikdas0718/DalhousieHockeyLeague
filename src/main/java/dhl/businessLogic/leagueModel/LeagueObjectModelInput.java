package dhl.businessLogic.leagueModel;

import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModelInput;
import dhl.businessLogic.leagueModel.interfaceModel.ILeagueObjectModelValidation;
import dhl.businessLogic.leagueModel.interfaceModel.ITeam;
import dhl.inputOutput.importJson.serializeDeserialize.interfaces.ISerializeLeagueObjectModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LeagueObjectModelInput implements ILeagueObjectModelInput {
    private static final Logger logger = LogManager.getLogger(LeagueObjectModelInput.class);
    private String leagueName;
    private String conferenceName;
    String divisionName;
    ITeam newlyCreatedTeam;
    ILeagueObjectModelValidation leagueObjectModelValidation;
    ISerializeLeagueObjectModel serializeLeagueObjectModel;

    public LeagueObjectModelInput(String leagueName, String conferenceName, String divisionName, ITeam newlyCreatedTeam, ILeagueObjectModelValidation leagueObjectModelValidation, ISerializeLeagueObjectModel serializeLeagueObjectModel) {
        logger.info("Constructor of Create League Object Model Input");
        this.leagueName = leagueName;
        this.conferenceName = conferenceName;
        this.divisionName = divisionName;
        this.newlyCreatedTeam = newlyCreatedTeam;
        this.leagueObjectModelValidation = leagueObjectModelValidation;
        this.serializeLeagueObjectModel = serializeLeagueObjectModel;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public String getConferenceName() {
        return conferenceName;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public ITeam getNewlyCreatedTeam() {
        return newlyCreatedTeam;
    }

    public ILeagueObjectModelValidation getLeagueObjectModelValidation() {
        return leagueObjectModelValidation;
    }

    public ISerializeLeagueObjectModel getSerializeLeagueObjectModel() {
        return serializeLeagueObjectModel;
    }
}
