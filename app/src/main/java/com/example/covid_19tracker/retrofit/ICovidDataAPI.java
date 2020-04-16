package com.example.covid_19tracker.retrofit;

import com.example.covid_19tracker.model.CountryData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ICovidDataAPI {
    @GET("countries")
    Call<List<CountryData>> getCovidData();
}
