package dhl.leagueModel;

import dhl.leagueModel.interfaceModel.*;

import java.util.List;
import java.util.function.Predicate;
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

    public Team(String teamName,String generalManager,String headCoach,String divisionName,String conferenceName){
        setTeamName(teamName);
        setGeneralManager(generalManager);
        setHeadCoach(headCoach);
        setDivisionName(divisionName);
        setConferenceName(conferenceName);
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


    public void checkIfOneCaptainPerTeam(List<IPlayer> playerList) throws Exception {
        Predicate<IPlayer> playerPredicate = player -> player.getCaptain() ;
        List<IPlayer> captainList=playerList.stream().filter(playerPredicate).collect(Collectors.toList());
        if(captainList.size()==0){
            throw new Exception("Please select captain for the team");
        }
        if(captainList.size()>1){
            throw new Exception("There can be only one captain per team");
        }
    }

    public boolean checkIfSizeOfTeamValid(List<IPlayer> playerList) {
        return playerList.size() <20 ;
    }


    public boolean checkIfTeamValid(IParserOutput parserOutput,IValidation validation) throws Exception{
        List<IPlayer> playerList=parserOutput.getPlayers();
        validation.isStringEmpty(teamName,"Team name");
        validation.isStringEmpty(headCoach,"Head Coach name");
        validation.isStringEmpty(generalManager,"General manager name");
        validation.isListEmpty(playerList,"Players");
        checkIfOneCaptainPerTeam(playerList);
        if(this.checkIfSizeOfTeamValid(playerList)==false){
            throw new Exception("Number of players cannot exceed 20 in each team");
        }
        return true;
    }


}
