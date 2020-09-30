package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.ILeagueObjectModel;
import dhl.leagueModel.interfaceModel.IParserOutput;
import dhl.leagueModel.interfaceModel.IPlayer;
import dhl.leagueModel.interfaceModel.ITeam;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Team implements ITeam {
    private int teamId;
    private String teamName;
    private String generalManager;
    private String headCoach;
    private String divisionName;
    private String conferenceName;

    public Team(){
        setDefault();
    }
    public void setDefault(){
        teamId=-1;
        teamName="";
        generalManager="";
        headCoach="";
        divisionName="";
        conferenceName="";
    }


    public String getTeamName() {
        return teamName;
    }
    public void setTeamName(String teamName) {
        this.teamName=teamName;
    }

    public String getGeneralManager() {
        return generalManager;
    }

    public void setGeneralManager(String generalManager) {
        this.generalManager=generalManager;
    }

    public String getHeadCoach() {
        return headCoach;
    }
    public void setHeadCoach(String headCoach) {
        this.headCoach=headCoach;
    }


    public String getDivisionName() {
        return divisionName;
    }
    public void setDivisionName(String divisionName) {
        this.divisionName=divisionName;
    }

    public String getConferenceName() {
        return conferenceName;
    }
    public void setConferenceName(String conferenceName) {
        this.conferenceName=conferenceName;
    }

    public boolean checkIfOneCaptainPerTeam(IParserOutput parserOutput) {
        List<IPlayer> playerList=parserOutput.getTeamPlayers().get(teamName);
        Predicate<IPlayer> playerPredicate = player -> player.getCaptain() == true;
        List<IPlayer> captainList=playerList.stream().filter(playerPredicate).collect(Collectors.toList());
        return captainList.size()==1;
    }


}
