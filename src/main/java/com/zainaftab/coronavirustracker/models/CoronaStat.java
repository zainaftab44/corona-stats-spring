package com.zainaftab.coronavirustracker.models;

public class CoronaStat {
    private String state;
    private String country;
    private int currentToll;
    private int currentTollDiff;
    private int recoveredToll;
    private int recoveredTollDiff;
    private int deathToll;
    private int deathTollDiff;

    public int getCurrentTollDiff() {
        return currentTollDiff;
    }

    public void setCurrentTollDiff(int currentTollDiff) {
        this.currentTollDiff = currentTollDiff;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCurrentToll() {
        return currentToll;
    }

    public void setCurrentToll(int currentToll) {
        this.currentToll = currentToll;
    }

    public int getRecoveredToll() {
        return recoveredToll;
    }

    public void setRecoveredToll(int recoveredToll) {
        this.recoveredToll = recoveredToll;
    }

    public int getRecoveredTollDiff() {
        return recoveredTollDiff;
    }

    public void setRecoveredTollDiff(int recoveredTollDiff) {
        this.recoveredTollDiff = recoveredTollDiff;
    }

    public int getDeathToll() {
        return deathToll;
    }

    public void setDeathToll(int deathToll) {
        this.deathToll = deathToll;
    }

    public int getDeathTollDiff() {
        return deathTollDiff;
    }

    public void setDeathTollDiff(int deathTollDiff) {
        this.deathTollDiff = deathTollDiff;
    }

    @Override
    public String toString() {
        return "CoronaStat{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", currentToll=" + currentToll +
                ", diffFromPreviousToll=" + currentTollDiff +
                '}';
    }
}
