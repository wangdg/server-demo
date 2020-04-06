package com.wangdg.server.task;

import com.wangdg.server.common.Constants;
import com.wangdg.server.model.WeatherData;
import com.wangdg.server.service.WeatherService;
import com.wangdg.server.weather.WeatherClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class WeatherDataUpdateTask {

    @Autowired
    private WeatherClient weatherClient;

    @Autowired
    private WeatherService weatherService;

    /**
     * Logger
     */
    private static Logger logger = LoggerFactory.getLogger(WeatherDataUpdateTask.class);

    @Scheduled(cron = "0 1 0,3,6,9,12,15,18,21 * * *")
    public void update() throws IOException, URISyntaxException {
        logger.debug("Start the task of updating weather data.");

        List<WeatherData> dalianWeatherList = weatherClient.getForecast(Constants.CITY_ID_DALIAN);
        logger.debug("Query weather data of Dalian successfully.");
        List<WeatherData> beijingWeatherList = weatherClient.getForecast(Constants.CITY_ID_BEIJING);
        logger.debug("Query weather data of Beijing successfully.");

        weatherService.saveWeatherDataList(dalianWeatherList);
        logger.debug("Save weather data of Dalian to DB");
        weatherService.saveWeatherDataList(beijingWeatherList);
        logger.debug("Save weather data of Beijing to DB");
    }
}
