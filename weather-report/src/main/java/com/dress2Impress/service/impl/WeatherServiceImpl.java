package com.dress2Impress.service.impl;

import com.dress2Impress.WeatherRequestConstants;
import com.dress2Impress.localisation.MessageLocaliser;
import com.dress2Impress.logging.annotation.InjectLogger;
import com.dress2Impress.service.WeatherQueryService;
import com.dress2Impress.service.WeatherService;
import com.dress2Impress.weatherenum.WeatherUnit;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;


@Service
public class WeatherServiceImpl implements WeatherService {

    @InjectLogger
    private static Logger LOG;

    private final WeatherQueryService weatherQueryService;
    private final MessageLocaliser messageLocaliser;

    @Autowired
    public WeatherServiceImpl(final WeatherQueryService weatherQueryService,
                              final MessageLocaliser messageLocaliser) {
        this.weatherQueryService = weatherQueryService;
        this.messageLocaliser = messageLocaliser;
    }

    @Override
    public String getCurrentLondonMetricTemperature() {
        final WeatherUnit unit = WeatherUnit.METRIC;

        try {
            return convertTempValue(this.weatherQueryService.getCurrentTemperature(
                    WeatherRequestConstants.Location.LONDON, unit), unit);
        } catch(Exception exception) {
            LOG.error("Error in retrieving logging", exception);
            return messageLocaliser.localiseMessage("temp.service.error");
        }
    }

    private String convertTempValue(final LinkedHashMap responseBody, final WeatherUnit unit) {
        final Long value = ((Integer)((LinkedHashMap)((LinkedHashMap)((ArrayList)(responseBody)
                .get(WeatherRequestConstants.ResponseValues.LIST)).get(0))
                .get(WeatherRequestConstants.ResponseValues.MAIN))
                .get(WeatherRequestConstants.ResponseValues.TEMP)).longValue();

         return new StringBuilder().append(value).append(unit.getSymbol()).toString();
    }
}
