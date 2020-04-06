package com.wangdg.server.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangdg.server.model.WeatherData;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Component
public class WeatherClientImpl implements WeatherClient {

    private static final String WEATHER_MAP_FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast";
    private static final String WEATHER_MAP_CURRENT_URL = "http://api.openweathermap.org/data/2.5/weather";

    /**
     * Logger
     */
    private static Logger logger = LoggerFactory.getLogger(WeatherClientImpl.class);

    /**
     * Http Client
     */
    CloseableHttpClient httpClient = HttpClients.createDefault();

    /**
     * Weather App Key
     */
    @Value("${weather.app.key}")
    private String weatherAppKey;

    /**
     * Json Parser
     */
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<WeatherData> getForecast(int cityId) throws IOException, URISyntaxException {

        URIBuilder builder = new URIBuilder(WEATHER_MAP_FORECAST_URL);
        builder.setParameter("id", String.valueOf(cityId)).setParameter("appid", weatherAppKey);
        URI uri = builder.build();

        HttpEntity httpEntity = null;
        try {
            HttpGet get = new HttpGet(uri);
            HttpResponse response = httpClient.execute(get);
            httpEntity = response.getEntity();

            String json = EntityUtils.toString(httpEntity);
            logger.debug(json);

            JsonNode rootNode = objectMapper.readTree(json);

            Integer code = rootNode.get("cod").asInt(0);
            if (rootNode.get("cod").asInt() != 200) {
                logger.error("Weather map api return error code: {}", code);
                return null;
            }

            List<WeatherData> resultList = new ArrayList<>();

            JsonNode list = rootNode.get("list");
            int size = list.size();
            for (int i = 0; i < size; i++) {
                WeatherData weatherData = createWeatherData(cityId, list.get(i));
                if (weatherData != null) {
                    resultList.add(weatherData);
                }
            }

            return resultList;
        } finally {
            EntityUtils.consumeQuietly(httpEntity);
        }
    }

    @Override
    public WeatherData getCurrent(int cityId) throws URISyntaxException, IOException {

        URIBuilder builder = new URIBuilder(WEATHER_MAP_CURRENT_URL);
        builder.setParameter("id", String.valueOf(cityId)).setParameter("appid", weatherAppKey);
        URI uri = builder.build();

        HttpEntity httpEntity = null;
        try {
            HttpGet get = new HttpGet(uri);
            HttpResponse response = httpClient.execute(get);
            httpEntity = response.getEntity();

            String json = EntityUtils.toString(httpEntity);
            logger.debug(json);

            JsonNode rootNode = objectMapper.readTree(json);

            Integer code = rootNode.get("cod").asInt(0);
            if (rootNode.get("cod").asInt() != 200) {
                logger.error("Weather map api return error code: {}", code);
                return null;
            }
            WeatherData weatherData = createWeatherData(cityId, rootNode);
            return weatherData;

        } finally {
            EntityUtils.consumeQuietly(httpEntity);
        }
    }

    protected WeatherData createWeatherData(int cityId, JsonNode node) {

        if (node == null) {
            return null;
        }

        JsonNode mainNode = node.get("main");
        JsonNode weatherNode = node.get("weather");
        if (weatherNode.isArray()) {
            weatherNode = weatherNode.get(0);
        }

        WeatherData weatherData = new WeatherData();
        weatherData.setCityCode(cityId);
        weatherData.setDateTime(node.get("dt").asLong());
        weatherData.setMainTemp(mainNode.get("temp").asDouble());
        weatherData.setMainTempMin(mainNode.get("temp_min").asDouble());
        weatherData.setMainTempMax(mainNode.get("temp_max").asDouble());
        weatherData.setMainPressure(mainNode.get("pressure").asDouble());
        weatherData.setHumidity(mainNode.get("humidity").asInt());
        weatherData.setDescription(weatherNode.get("description").asText());

        JsonNode dtText = node.get("dt_txt");
        if (dtText != null) {
            weatherData.setDateTimeText(node.get("dt_txt").asText());
        }

        return weatherData;
    }
}
