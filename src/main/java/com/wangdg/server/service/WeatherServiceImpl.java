package com.wangdg.server.service;

import com.wangdg.server.mapper.WeatherMapper;
import com.wangdg.server.model.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private WeatherMapper weatherMapper;

    @Override
    @Transactional
    public void saveWeatherDataList(List<WeatherData> list) {

        if (list == null || list.isEmpty()) {
            return;
        }

        for (WeatherData data : list) {
            Integer count = weatherMapper.count(data.getCityCode(), data.getDateTime());
            if (count <= 0) {
                weatherMapper.insert(data);
            } else {
                weatherMapper.update(data);
            }
        }
    }
}
