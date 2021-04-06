package com.example.cashfreepaymentgateway.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ServiceGenerator {
    private static final String TEST_URL="https://test.cashfree.com/api/v2/cftoken/";

    private static final Gson gson = new GsonBuilder().setLenient().create();
    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null, null);
    }

    private static <S> S createService(Class<S> serviceClass, String username, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TEST_URL)
                .client(provideOkHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(serviceClass);
    }

    private static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder().readTimeout(1, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES)
                .build();
    }



}