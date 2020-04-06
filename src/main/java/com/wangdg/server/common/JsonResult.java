package com.wangdg.server.common;

public class JsonResult {

    private Integer code;

    private String message;

    private Object data;

    private static JsonResult EMPTY_SUCCESS_RESULT = new JsonResult(ResultCodes.SUCCESS, "success", null);

    public static JsonResult success() {
        return EMPTY_SUCCESS_RESULT;
    }

    public static JsonResult success(Object data) {
        return data != null ? new JsonResult(ResultCodes.SUCCESS, "success", data) : EMPTY_SUCCESS_RESULT;
    }

    public static JsonResult fail(Integer code, String message) {
        return new JsonResult(code, message, null);
    }

    public static JsonResult fail(Integer code, String message, Object data) {
        return new JsonResult(code, message, data);
    }

    public static JsonResult reject(String message) {
        return new JsonResult(ResultCodes.VALID_ERROR, message, null);
    }

    public static JsonResult reject(String message, String data) {
        return new JsonResult(ResultCodes.VALID_ERROR, message, data);
    }

    private JsonResult(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
