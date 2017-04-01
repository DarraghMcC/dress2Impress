package com.dress2Impress.service.impl;

import com.dress2Impress.WeatherRequestConstants;
import com.dress2Impress.service.WeatherQueryService;
import com.dress2Impress.weatherenum.WeatherUnit;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedHashMap;

@Service
public class WeatherQueryServiceImpl implements WeatherQueryService{

    @Override
    public LinkedHashMap getCurrentTemperature(String location, WeatherUnit unit) {
        final UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(WeatherRequestConstants.URL.CURRENT_WEATHER)
                .queryParam(WeatherRequestConstants.Parameter.LOCATION, location)
                .queryParam(WeatherRequestConstants.Parameter.UNITS, unit.getName())
                .queryParam(WeatherRequestConstants.Parameter.APP_ID, WeatherRequestConstants.WeatherApp.USER_ID);

        return (LinkedHashMap) getRestTemplate().getForEntity(builder.toUriString(), Object.class).getBody();
    }

    private RestTemplate getRestTemplate() {
        return new RestTemplate(new SimpleClientHttpRequestFactory());
    }

}
