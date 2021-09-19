package com.cft.cft_test.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cft.cft_test.R;
import com.cft.cft_test.data.Currency;

import java.util.ArrayList;
import java.util.List;

class CurrenciesListAdapter extends RecyclerView.Adapter<CurrenciesListAdapter.ViewHolder> {
    List<Currency> currencies = new ArrayList<>();
    private static CurrencyClickListener currencyClickListener;
    private static int selectedPosition = 0;
    private static int lastSelectedPosition = -1;

    public CurrenciesListAdapter(CurrencyClickListener currencyClickListener) {
        this.currencyClickListener = currencyClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_list_currency, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Currency currency = currencies.get(position);
        holder.itemView.setOnClickListener(v -> {
            selectedPosition = holder.getAbsoluteAdapterPosition();
            notifyItemChanged(lastSelectedPosition);
            lastSelectedPosition = selectedPosition;
            currencyClickListener.onClick(currency);
            notifyItemChanged(selectedPosition);
        });
        if (position == selectedPosition) {
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.colorSelected));
        } else {
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.colorBackground));
        }
        holder.bind(currency);
    }

    @Override
    public int getItemCount() {
        return currencies.size();
    }

    public void setCurrencies(List<Currency> currenciesUpdate) {
        currencies.clear();
        currencies.addAll(currenciesUpdate);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNominal, tvName, tvCode, tvRate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNominal = itemView.findViewById(R.id.tvNominal);
            tvName = itemView.findViewById(R.id.tvName);
            tvCode = itemView.findViewById(R.id.tvCode);
            tvRate = itemView.findViewById(R.id.tvRate);
        }

        void bind(Currency currency) {
            if (getAbsoluteAdapterPosition() == 0 && lastSelectedPosition == -1) {
                itemView.setBackgroundColor(itemView.getResources().getColor(R.color.colorSelected));
                lastSelectedPosition = 0;
                currencyClickListener.onClick(currency);
            }
            tvNominal.setText(String.valueOf(currency.getNominal()));
            tvName.setText(currency.getName());
            tvCode.setText(currency.getCharCode());
            tvRate.setText(String.valueOf(currency.getCurrentValue()));
        }
    }
}
