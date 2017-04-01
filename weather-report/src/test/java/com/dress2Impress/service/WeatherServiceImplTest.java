package com.dress2Impress.service;

import com.dress2Impress.WeatherRequestConstants;
import com.dress2Impress.common.InjectUtils;
import com.dress2Impress.localisation.Impl.MessageLocaliserImpl;
import com.dress2Impress.service.impl.WeatherQueryServiceImpl;
import com.dress2Impress.service.impl.WeatherServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Mock
    private WeatherQueryServiceImpl weatherQueryService;
    @Mock
    private MessageLocaliserImpl messageLocaliser;
    @Mock
    private static Logger LOG;
    @Autowired
    InjectUtils injectUtils;

    @Before
    public void createDefaultMocks() throws Exception {
        when(messageLocaliser.localiseMessage(any())).thenReturn("Temperature currently unavailable");
        setStatic(weatherService.getClass().getDeclaredField("LOG"), LOG);
    }

    @Test
    public void getCurrentLondonTemperature_valid() {
        when(weatherQueryService.getCurrentTemperature(any(), any())).
                thenReturn(this.getTempValueResponse(10));

        assertThat(weatherService.getCurrentLondonMetricTemperature(), is("10˚C"));
    }

    @Test
    public void getCurrentLondonTemperature_emptyMap() {
        when(weatherQueryService.getCurrentTemperature(any(), any())).thenReturn(new LinkedHashMap<>());

        assertThat(weatherService.getCurrentLondonMetricTemperature(), is("Temperature currently unavailable"));
    }

    @Test
    public void getCurrentLondonTemperature_null() {
        when(weatherQueryService.getCurrentTemperature(any(), any())).thenReturn(null);

        assertThat(weatherService.getCurrentLondonMetricTemperature(), is("Temperature currently unavailable"));
    }

    @Test
    public void getCurrentLondonTemperature_minusValues() {
        when(weatherQueryService.getCurrentTemperature(any(), any())).
                thenReturn(this.getTempValueResponse(-30));

        assertThat(weatherService.getCurrentLondonMetricTemperature(), is("-30˚C"));
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
