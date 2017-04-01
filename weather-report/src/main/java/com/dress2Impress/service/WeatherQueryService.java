package com.dress2Impress.service;

import com.dress2Impress.weatherenum.WeatherUnit;

import java.util.LinkedHashMap;

public interface WeatherQueryService {

     LinkedHashMap getCurrentTemperature(String location, WeatherUnit unit);
}
