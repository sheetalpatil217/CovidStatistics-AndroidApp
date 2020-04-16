package com.example.covid_19tracker.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit instance;

    public static Retrofit getInstance(){

            instance= new Retrofit.Builder().baseUrl("https://corona.lmao.ninja/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        return instance;
    }
}
