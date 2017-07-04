package com.dress2Impress.dress.service.impl;


import com.dress2Impress.common.localisation.MessageLocaliser;
import com.dress2Impress.dress.config.JacketTempProperties;
import com.dress2Impress.weather.service.WeatherService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DressServiceImplTests {

	private DressServiceImpl dressService;

	@Mock
	private WeatherService weatherService;
	@Mock
	private MessageLocaliser messageLocaliser;
	private JacketTempProperties jacketTempProperties;

	@Before
	public void setUpTestDressService(){
		jacketTempProperties = new JacketTempProperties();
		jacketTempProperties.setMaxTempLimit(15);
		dressService = new DressServiceImpl(jacketTempProperties, weatherService, messageLocaliser);
	}

	@Test
	public void testGetJacket_needed(){
		when(weatherService.getCurrentCelciusTemperatureInLondon()).thenReturn(-10);

		assertTrue("A jacket should be needed", dressService.checkLondonJacketWeather().isJacketNeeded());
	}

	@Test
	public void testGetJacket_notNeeded(){
		when(weatherService.getCurrentCelciusTemperatureInLondon()).thenReturn(100);

		assertFalse("A jacket should not be needed", dressService.checkLondonJacketWeather().isJacketNeeded());
	}
}
