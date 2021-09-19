package com.cft.cft_test.presentation;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cft.cft_test.R;
import com.cft.cft_test.data.Currency;

import java.util.List;

public class CurrenciesActivity extends AppCompatActivity implements ICurrencyView {
    CurrenciesPresenter presenter;
    private CurrenciesListAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText rublesEditText;
    private EditText convertedEditText;
    private TextView targetCurrencyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new CurrenciesListAdapter(currency -> presenter.onItemListClicked(currency));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView rvCurrenciesList = findViewById(R.id.rvCurrencyList);
        rvCurrenciesList.setLayoutManager(layoutManager);
        rvCurrenciesList.setAdapter(adapter);
        rvCurrenciesList.addItemDecoration(new ItemDecoration(this, R.drawable.divider));

        swipeRefreshLayout = findViewById(R.id.sw);
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.onRefresh());
        swipeRefreshLayout.setRefreshing(true);

        convertedEditText = findViewById(R.id.etTargetCurrency);
        rublesEditText = findViewById(R.id.etRubles);
        rublesEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onValueChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        targetCurrencyTextView = findViewById(R.id.tvTargetCurrency);
        presenter = new CurrenciesPresenter(getBaseContext());
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void showCurrencies(List<Currency> currencies) {
        adapter.setCurrencies(currencies);
    }

    @Override
    public void updateCurrentCurrency(Currency currency) {
        targetCurrencyTextView.setText(currency.getName());
    }

    @Override
    public void updateConvertedValue(String newValue) {
        convertedEditText.setText(newValue);
    }

    @Override
    public double getCurrentValue() {
        return Double.parseDouble(rublesEditText.getText().toString());
    }

    @Override
    public void setProgressVisibility(boolean isVisible) {
        swipeRefreshLayout.setRefreshing(isVisible);
    }

    @Override
    public void setRublesValue(String value) {
        rublesEditText.setText(value);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }
}