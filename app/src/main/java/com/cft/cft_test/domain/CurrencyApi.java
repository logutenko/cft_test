package com.cft.cft_test.domain;

import com.cft.cft_test.data.DailyData;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface CurrencyApi {

    @GET("daily_json.js")
    Single<DailyData> getDailyData();
}
