package com.cft.cft_test.domain;

import com.cft.cft_test.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrencyService {
    private static CurrencyService instance;
    private CurrencyApi currencyApi;

    private CurrencyService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.TARGET_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        currencyApi = retrofit.create(CurrencyApi.class);
    }

    public static CurrencyService getInstance() {
        if (instance == null) {
            instance = new CurrencyService();
        }
        return instance;
    }

    public CurrencyApi getCurrenciesApi() {
        return currencyApi;
    }
}
