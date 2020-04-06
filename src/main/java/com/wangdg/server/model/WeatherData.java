package com.wangdg.server.model;

import lombok.Data;

@Data
public class WeatherData {
    private Integer cityCode;
    private Long dateTime;
    private Double mainTemp;
    private Double mainTempMin;
    private Double mainTempMax;
    private Double mainPressure;
    private Integer humidity;
    private String description;
    private String icon;
    private String dateTimeText;
}
