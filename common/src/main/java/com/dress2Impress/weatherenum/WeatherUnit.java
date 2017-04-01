package com.dress2Impress.weatherenum;

public enum WeatherUnit {

    METRIC("metric", "˚C");

    private final String name ;
    private final String symbol;

    private WeatherUnit(final String name, final String symbol){
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }
}
