package com.cft.cft_test.presentation;

import com.cft.cft_test.data.Currency;

import java.util.List;

public interface ICurrencyView {
    void showCurrencies(List<Currency> currencies);

    void updateCurrentCurrency(Currency currency);

    void updateConvertedValue(String convertedValue);

    double getCurrentValue();

    void setProgressVisibility(boolean isVisible);

    void setRublesValue(String value);
}