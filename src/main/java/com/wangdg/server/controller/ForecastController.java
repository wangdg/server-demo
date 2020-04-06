package com.wangdg.server.controller;

import com.wangdg.server.common.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForecastController {

    @RequestMapping(path = "/forecast", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult getForecast(@RequestParam("city_id") String cityId) {
        return JsonResult.success();
    }
}
