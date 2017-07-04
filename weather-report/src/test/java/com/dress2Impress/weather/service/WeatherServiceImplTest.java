package com.dress2Impress.weather.service;

import com.dress2Impress.common.InjectUtils;
import com.dress2Impress.common.exception.WeatherServiceException;
import com.dress2Impress.common.localisation.Impl.MessageLocaliserImpl;
import com.dress2Impress.weather.WeatherRequestConstants;
import com.dress2Impress.weather.service.impl.WeatherQueryServiceImpl;
import com.dress2Impress.weather.service.impl.WeatherServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static com.dress2impress.test.util.MockitoUtils.setStatic;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceImplTest {

    private WeatherServiceImpl weatherService;

    @Mock
    private WeatherQueryServiceImpl weatherQueryService;
    @Mock
    private MessageLocaliserImpl messageLocaliser;
    @Mock
    private static Logger LOG;
    @Autowired
    InjectUtils injectUtils;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void createDefaultMocks() throws Exception {
        weatherService = new WeatherServiceImpl(weatherQueryService, messageLocaliser);

        when(messageLocaliser.localiseMessage(any())).thenReturn("Temperature currently unavailable");
        setStatic(weatherService.getClass().getDeclaredField("LOG"), LOG);
    }

    @Test
    public void getCurrentLondonTemperature_valid() {
        when(weatherQueryService.getCurrentTemperature(any(), any())).
                thenReturn(this.getTempValueResponse(10));

        assertThat(weatherService.getCurrentCelciusTemperatureInLondon(), is(10));
    }

    @Test
    public void getCurrentLondonTemperature_emptyMap() {
        when(weatherQueryService.getCurrentTemperature(any(), any())).thenReturn(new LinkedHashMap<>());

        exception.expect(WeatherServiceException.class);
        exception.expectMessage("Temperature currently unavailable");
        weatherService.getCurrentCelciusTemperatureInLondon();
    }

    @Test
    public void getCurrentLondonTemperature_null() {
        when(weatherQueryService.getCurrentTemperature(any(), any())).thenReturn(null);

        exception.expect(WeatherServiceException.class);
        exception.expectMessage("Temperature currently unavailable");
        weatherService.getCurrentCelciusTemperatureInLondon();
    }

    @Test
    public void getCurrentLondonTemperature_minusValues() {
        when(weatherQueryService.getCurrentTemperature(any(), any())).
                thenReturn(this.getTempValueResponse(-30));

        assertThat(weatherService.getCurrentCelciusTemperatureInLondon(), is(-30));
    }


    private LinkedHashMap getTempValueResponse(final int tempValue) {
        final LinkedHashMap<String, ArrayList> responseMap = new LinkedHashMap<>();
        final LinkedHashMap<String, Integer> listMap = new LinkedHashMap<>();
        final LinkedHashMap<String, HashMap> mainMap = new LinkedHashMap<>();
        final ArrayList<HashMap> arrayList = new ArrayList<>();

        listMap.put(WeatherRequestConstants.ResponseValues.TEMP, tempValue);
        mainMap.put(WeatherRequestConstants.ResponseValues.MAIN, listMap);
        arrayList.add(mainMap);
        responseMap.put(WeatherRequestConstants.ResponseValues.LIST, arrayList);

        return responseMap;
    }
}
