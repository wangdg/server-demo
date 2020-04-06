package com.wangdg.server.service;

import com.wangdg.server.model.WeatherData;

import java.util.List;

public interface WeatherService {
    void saveWeatherDataList(List<WeatherData> list);
}
