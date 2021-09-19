package com.cft.cft_test.domain;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.cft.cft_test.data.Currency;
import com.cft.cft_test.data.DailyData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CurrenciesModel {
    private List<Currency> currencies = new ArrayList<>();
    private Currency currentCurrency;
    private final Context context;
    private final Gson serializer;
    private final String CURRENCIES = "Currencies";
    private final String CURRENT_CURRENCY = "CurrentCurrency";

    public CurrenciesModel(Context context) {
        this.context = context;
        serializer = new GsonBuilder().create();
    }

    public void setCurrentCurrency(Currency currentCurrency) {
        this.currentCurrency = currentCurrency;
    }

    public Single<List<Currency>> fetchCurrencies() {
        return CurrencyService.getInstance()
                .getCurrenciesApi()
                .getDailyData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(DailyData::getCurrencies);
    }

    public void saveCurrencies() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("currencies", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String serializedData = serializer.toJson(currencies);
        editor.putString(CURRENCIES, serializedData);
        editor.apply();
    }

    public void saveCurrentCurrency() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("currencies", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String serializedData = serializer.toJson(currentCurrency);
        editor.putString(CURRENT_CURRENCY, serializedData);
        editor.apply();
    }

    public List<Currency> restoreCurrencies() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("currencies", MODE_PRIVATE);
        String serializedData = sharedPreferences.getString(CURRENCIES, null);
        if (serializedData != null) {
            Type typeToken = new TypeToken<List<Currency>>() {
            }.getType();
            currencies = serializer.fromJson(serializedData, typeToken);
            return currencies;
        }
        return Collections.emptyList();
    }

    public Currency restoreCurrentCurrency() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("currencies", MODE_PRIVATE);
        String serializedData = sharedPreferences.getString(CURRENT_CURRENCY, null);
        currentCurrency = serializer.fromJson(serializedData, Currency.class);
        return currentCurrency;

    }

    public double convert(double value) {
        return value * currentCurrency.getCurrentValue();
    }
}
