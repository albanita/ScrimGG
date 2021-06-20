package com.igalda.scrimgg.dom;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TournamentProfesional implements Serializable {
    private String id;
    private Date begin;
    private Date end;
    private LeagueProfesional league;
    private List<TeamProfesional> teams;
    private String winnerID;
    private WinnerTypeProfesional winnerType;

    public TournamentProfesional(String id, Date begin, Date end, LeagueProfesional league, List<TeamProfesional> teams, String winnerID, WinnerTypeProfesional winnerType) {
        this.id = id;
        this.begin = begin;
        this.end = end;
        this.league = league;
        this.teams = teams;
        this.winnerID = winnerID;
        this.winnerType = winnerType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public LeagueProfesional getLeague() {
        return league;
    }

    public void setLeague(LeagueProfesional league) {
        this.league = league;
    }

    public List<TeamProfesional> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamProfesional> teams) {
        this.teams = teams;
    }

    public String getWinnerID() {
        return winnerID;
    }

    public void setWinnerID(String winnerID) {
        this.winnerID = winnerID;
    }

    public WinnerTypeProfesional getWinnerType() {
        return winnerType;
    }

    public void setWinnerType(WinnerTypeProfesional winnerType) {
        this.winnerType = winnerType;
    }

    @Override
    public String toString() {
        return "TournamentProfesional{" +
                "id='" + id + '\'' +
                ", begin=" + begin +
                ", end=" + end +
                ", league=" + league +
                ", teams=" + teams +
                ", winnerID='" + winnerID + '\'' +
                ", winnerType=" + winnerType +
                '}';
    }
}
