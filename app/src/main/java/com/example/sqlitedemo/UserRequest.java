package com.example.sqlitedemo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRequest {

    private String NiveauDeBatterie;
    private String AdrMac;
    private String Memoire;
    private String Lattitude;
    private String Longitude;
    private String Android_id;
    private String Modele;
    private String Fabriquant;
    private String VersionSE;

    public UserRequest() {
    }



    public String getNivBatterie() {
        return NiveauDeBatterie;
    }

    public void setNivBatterie(String niveauDeBatterie) {
        NiveauDeBatterie = niveauDeBatterie;
    }
    public String getFabriquant() {
        return Fabriquant;
    }

    public void setFabriquant(String fabriquant) {
        Fabriquant = fabriquant;
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
        return Android_id;
    }

    public void setTerminalId(String terminalId) {
        Android_id = terminalId;
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
                "Android_id='" + Android_id + '\'' +
                ", NiveauDeBatterie='" + NiveauDeBatterie + '\'' +
                ", Memoire='" + Memoire + '\'' +
                ", Lattitude='" + Lattitude + '\'' +
                ", Longitude='" + Longitude + '\'' +
                ", Fabriquant='" + Fabriquant + '\'' +
                ", Modele='" + Modele + '\'' +
                ", VersionSE='" + VersionSE + '\'' +
                '}';
    }
}
