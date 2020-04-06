package com.wangdg.server.controller;

import com.wangdg.server.common.Constants;
import com.wangdg.server.common.JsonResult;
import com.wangdg.server.model.WeatherData;
import com.wangdg.server.weather.WeatherClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
public class CurrentController {

    @Autowired
    private WeatherClient weatherClient;

    @RequestMapping(path = "/current", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult getCurrent(@RequestParam("city") String city) throws IOException, URISyntaxException {
        WeatherData weatherData = null;
        if ("dalian".equals(city)) {
            weatherData = weatherClient.getCurrent(Constants.CITY_ID_DALIAN);
        } else if ("bejing".equals(city)) {
            weatherData = weatherClient.getCurrent(Constants.CITY_ID_BEIJING);
        }
        return JsonResult.success(weatherData);
    }
}
