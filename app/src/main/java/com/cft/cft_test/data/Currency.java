package com.cft.cft_test.data;

import com.google.gson.annotations.SerializedName;

public class Currency {
    @SerializedName("CharCode")
    private String charCode;
    @SerializedName("Nominal")
    private int nominal;
    @SerializedName("Name")
    private String name;
    @SerializedName("Value")
    private double currentValue;

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "charCode='" + charCode + '\'' +
                ", nominal=" + nominal +
                ", name='" + name + '\'' +
                ", currentValue=" + currentValue +
                '}';
    }
}
