package com.cft.cft_test.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DailyData {
    @SerializedName("Valute")
    private Map<String, Currency> currencies;

    public List<Currency> getCurrencies() {
        return new ArrayList<>(currencies.values());
    }
}


