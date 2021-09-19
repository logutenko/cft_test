package com.cft.cft_test.presentation;

import android.content.Context;
import android.util.Log;

import com.cft.cft_test.data.Currency;
import com.cft.cft_test.domain.CurrenciesModel;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class CurrenciesPresenter {

    public CurrenciesModel model;
    private ICurrencyView view;
    private final CompositeDisposable composite = new CompositeDisposable();
    private final DecimalFormat format = new DecimalFormat("#,###.####");

    public CurrenciesPresenter(Context context) {
        model = new CurrenciesModel(context);
        composite.add(Observable.interval(1000, 10000,
                TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(qwe -> {
                    fetchCurrencies();
                }, error -> {
                    handleError(error);
                }));

    }

    public void attachView(ICurrencyView view) {
        this.view = view;
        restoreCurrencies();
        restoreCurrentCurrency();
    }

    public void detachView() {
        view = null;
        composite.dispose();
    }

    public void onRefresh() {
        fetchCurrencies();
    }

    public void onPause() {
        model.saveCurrentCurrency();
        model.saveCurrencies();
    }

    public void onItemListClicked(Currency currency) {
        model.setCurrentCurrency(currency);
        view.updateCurrentCurrency(currency);
        convert(view.getCurrentValue());
    }

    public void onValueChanged(String value) {
        if (value.isEmpty()) {
            view.setRublesValue("0");
            convert(0);
        } else {
            convert(Double.parseDouble(value));
        }
    }

    private void restoreCurrencies() {
        List<Currency> savedCurrencies = model.restoreCurrencies();
        if (savedCurrencies.isEmpty()) {
            fetchCurrencies();
        } else {
            view.showCurrencies(savedCurrencies);
        }
    }

    private void fetchCurrencies() {
        composite.add(model.fetchCurrencies()
                .doOnSubscribe(consumer -> {
                    view.setProgressVisibility(true);
                })
                .subscribe(currencies -> {
                    view.showCurrencies(currencies);
                    view.setProgressVisibility(false);
                }, throwable -> {
                    view.setProgressVisibility(false);
                    handleError(throwable);
                }));
    }

    private void restoreCurrentCurrency() {
        Currency currency = model.restoreCurrentCurrency();
        if (currency != null) {
            view.updateCurrentCurrency(currency);
            model.setCurrentCurrency(currency);
        }
    }

    private void handleError(Throwable throwable) {
        Log.e("Test", "handleError: " + throwable.getLocalizedMessage());
    }

    private void convert(double sourceValue) {
        view.updateConvertedValue(format.format(model.convert(sourceValue)));
    }

}
