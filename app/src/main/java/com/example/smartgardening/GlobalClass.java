package com.example.smartgardening;

import android.app.Application;

import java.util.ArrayList;

public class GlobalClass extends Application {
    private String User;
    private String Land;
    private String Plant;
    private String ApparentUser;
    private String landspace1Name;
    private String landspace2Name;
    private ArrayList<String> currentSoilMoisture;
    private ArrayList<String> currentLightIntensity;
    private ArrayList<String> optimalSoilMoisture;
    private ArrayList<String> optimalLightIntensity;
    private ArrayList<String> plantName;
    private int Plant1ID;
    private int Plant2ID;

    public void setUser(String User) {
        this.User = User;
    }

    public void setLand(String Land) {
        this.Land = Land;
    }

    public void setPlant(String Plant) {
        this.Plant = Plant;
    }

    public String getUser() {
        return User;
    }

    public String getLand() {
        return Land;
    }

    public String getPlant() {
        return Plant;
    }

    public void setApparentUser(String apparentUser) { ApparentUser = apparentUser;}

    public String getApparentUser(){ return ApparentUser;}

    public void setLandspace1Name (String landspace) { landspace1Name=landspace;}

    public String getLandspace1Name(){ return landspace1Name;}

    public void setLandspace2Name (String landspace) { landspace2Name=landspace;}

    public String getLandspace2Name(){ return landspace2Name;}

    public void setplantname (ArrayList<String> plantname) {
        plantName=plantname;}

    public ArrayList<String> getPlantName(){
        return plantName;}

    public void setCurrentSoilMoisture (ArrayList<String> currentsoilmoisture){
        currentSoilMoisture = currentsoilmoisture;
    }

    public ArrayList<String> getCurrentSoilMoisture() {
        return currentSoilMoisture;
    }

    public void setCurrentLightIntensity (ArrayList<String> currentlightintensity){
        currentLightIntensity = currentlightintensity;
    }

    public ArrayList<String> getCurrentLightIntensity() {
        return currentLightIntensity;
    }

    public void setOptimalSoilMoisture (ArrayList<String> optimalsoilmoisture){
        optimalSoilMoisture = optimalsoilmoisture;
    }

    public ArrayList<String> getOptimalSoilMoisture() {
        return optimalSoilMoisture;
    }

    public void setOptimalLightIntensity (ArrayList<String> optimallightintensity){
        optimalLightIntensity = optimallightintensity;
    }

    public ArrayList<String> getOptimalLightIntensity() {
        return optimalLightIntensity;
    }

    public void setPlant1ID (int plantID) { Plant1ID = plantID;}

    public int getPlant1ID() { return Plant1ID; }

    public void setPlant2ID (int plantID) { Plant2ID = plantID;}

    public int getPlant2ID() { return Plant2ID; }
}