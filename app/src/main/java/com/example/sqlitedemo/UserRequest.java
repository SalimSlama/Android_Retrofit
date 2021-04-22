package com.example.sqlitedemo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRequest {

    private String NivBatterie;
    private String AdrMac;
    private String Memoire;
    private String Lattitude;
    private String Longitude;
    private String TerminalId;
    private String Modele;
    private String VersionSE;


    public String getNivBatterie() {
        return NivBatterie;
    }

    public void setNivBatterie(String nivBatterie) {
        NivBatterie = nivBatterie;
    }

    public String getAdrMac() {
        return AdrMac;
    }

    public void setAdrMac(String adrMac) {
        AdrMac = adrMac;
    }

    public String getMemoire() {
        return Memoire;
    }

    public void setMemoire(String memoire) {
        Memoire = memoire;
    }

    public String getLattitude() {
        return Lattitude;
    }

    public void setLattitude(String lattitude) {
        Lattitude = lattitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getTerminalId() {
        return TerminalId;
    }

    public void setTerminalId(String terminalId) {
        TerminalId = terminalId;
    }

    public String getModele() {
        return Modele;
    }

    public void setModele(String modele) {
        Modele = modele;
    }

    public String getVersionSE() {
        return VersionSE;
    }

    public void setVersionSE(String versionSE) {
        VersionSE = versionSE;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "NivBatterie=" + NivBatterie +
                ", AdrMac='" + AdrMac + '\'' +
                ", Memoire='" + Memoire + '\'' +
                ", Lattitude='" + Lattitude + '\'' +
                ", Longitude='" + Longitude + '\'' +
                ", TerminalId='" + TerminalId + '\'' +
                ", Modele='" + Modele + '\'' +
                ", VersionSE='" + VersionSE + '\'' +
                '}';
    }
}
