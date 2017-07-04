package com.dress2Impress.dress.service.impl;

import com.dress2Impress.common.dto.dress.JacketCheckDTO;
import com.dress2Impress.common.localisation.MessageLocaliser;
import com.dress2Impress.dress.config.JacketTempProperties;
import com.dress2Impress.dress.service.DressService;
import com.dress2Impress.dress.tempenum.JacketTempDescription;
import com.dress2Impress.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DressServiceImpl implements DressService{

	private final JacketTempProperties jacketTempProperties;
	private final WeatherService weatherService;
	private final MessageLocaliser messageLocaliser;

	@Autowired
	public DressServiceImpl(final JacketTempProperties jacketTempProperties,
							final WeatherService weatherService,
							final MessageLocaliser messageLocaliser) {
		this.jacketTempProperties = jacketTempProperties;
		this.weatherService = weatherService;
		this.messageLocaliser = messageLocaliser;
	}

	public JacketCheckDTO checkLondonJacketWeather(){
 		final JacketCheckDTO jacketCheckDTO = new JacketCheckDTO();
 		final Integer currentTemp = weatherService.getCurrentCelciusTemperatureInLondon();

 		if(currentTemp > jacketTempProperties.getMaxTempLimit()) {
 			return jacketCheckDTO.setJacketNeeded(false);
		}

		return jacketCheckDTO.setJacketNeeded(true)
				.setJacketDetails(messageLocaliser.localiseMessage(JacketTempDescription.getMessageCode(currentTemp)));
	}

}
