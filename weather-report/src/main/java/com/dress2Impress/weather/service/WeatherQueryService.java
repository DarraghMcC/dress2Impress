package com.dress2Impress.weather.service;

import com.dress2Impress.common.weatherenum.WeatherUnit;

import java.util.LinkedHashMap;

public interface WeatherQueryService {

     LinkedHashMap getCurrentTemperature(String location, WeatherUnit unit);
}
