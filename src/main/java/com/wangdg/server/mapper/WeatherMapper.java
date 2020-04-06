package com.wangdg.server.mapper;

import com.wangdg.server.model.WeatherData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WeatherMapper {
    Integer count(@Param("cityId") Integer cityId, @Param("dateTime") Long dateTime);
    void insert(WeatherData data);
    void update(WeatherData data);
}
