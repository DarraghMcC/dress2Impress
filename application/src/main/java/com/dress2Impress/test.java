package com.dress2Impress;

import com.dress2Impress.logging.annotation.InjectLogger;
import com.dress2Impress.service.WeatherService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class test {

    @InjectLogger
    private static Logger LOG;

    @Autowired
    public test(final WeatherService weatherService){
        System.out.println("Current temp is " + weatherService.getCurrentLondonMetricTemperature());
    }
}
