package com.wangdg.server.weather;

import com.wangdg.server.model.WeatherData;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface WeatherClient {

    /**
     * 根据城市ID取得天气预报信息
     * @param cityId City ID
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    List<WeatherData> getForecast(int cityId) throws IOException, URISyntaxException;

    /**
     * 根据城市ID取得当前天气
     * @param cityId CityID
     * @return
     */
    WeatherData getCurrent(int cityId) throws URISyntaxException, IOException;
}
