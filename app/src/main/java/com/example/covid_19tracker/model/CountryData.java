package com.example.covid_19tracker.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CountryData implements Serializable {
    @SerializedName("country")
    private String countryName;
    @SerializedName("countryInfo")
    private CountryLocation countryInfo;
    private String cases;
    private String todayCases;
    private String deaths;
    private String todayDeaths;
    private String recovered;
    private String active;
    private String critical;
    @SerializedName("casesPerOneMillion")
    private String casesPerMillion;
    @SerializedName("deathsPerOneMillion")
    private String deathsPerMillion;
    private String tests;
    @SerializedName("testsPerOneMillion")
    private String testPerMillion;

    public CountryData() {
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public CountryLocation getCountryInfo() {
        return countryInfo;
    }

    public void setCountryInfo(CountryLocation countryInfo) {
        this.countryInfo = countryInfo;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(String todayCases) {
        this.todayCases = todayCases;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(String todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCritical() {
        return critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }

    public String getCasesPerMillion() {
        return casesPerMillion;
    }

    public void setCasesPerMillion(String casesPerMillion) {
        this.casesPerMillion = casesPerMillion;
    }

    public String getDeathsPerMillion() {
        return deathsPerMillion;
    }

    public void setDeathsPerMillion(String deathsPerMillion) {
        this.deathsPerMillion = deathsPerMillion;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public String getTestPerMillion() {
        return testPerMillion;
    }

    public void setTestPerMillion(String testPerMillion) {
        this.testPerMillion = testPerMillion;
    }

    @Override
    public String toString() {
        return "CountryData{" +
                "countryName='" + countryName + '\'' +
                ", countryInfo=" + countryInfo +
                ", cases='" + cases + '\'' +
                ", todayCases='" + todayCases + '\'' +
                ", deaths='" + deaths + '\'' +
                ", todayDeaths='" + todayDeaths + '\'' +
                ", recovered='" + recovered + '\'' +
                ", active='" + active + '\'' +
                ", critical='" + critical + '\'' +
                ", casesPerMillion='" + casesPerMillion + '\'' +
                ", deathsPerMillion='" + deathsPerMillion + '\'' +
                ", tests='" + tests + '\'' +
                ", testPerMillion='" + testPerMillion + '\'' +
                '}';
    }
}
