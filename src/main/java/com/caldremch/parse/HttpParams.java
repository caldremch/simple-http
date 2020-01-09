package com.caldremch.parse;

import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;

/**
 * @author Caldremch
 * @date 2019-08-20 15:54
 * @email caldremch@163.com
 * @describe
 **/
public class HttpParams {

    private static Gson gson = new Gson();

    public static final MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");
    public static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

    private Map<String, Object> urlParamsMap;

    public Map<String, Object> getUrlParams() {
        return urlParamsMap;
    }

    public void setUrlParamsMap(Map<String, Object> urlParamsMap) {
        this.urlParamsMap = urlParamsMap;
    }

    public HttpParams() {
        init();
    }

    public HttpParams(String key, String value) {
        init();
        put(key, value);
    }

    public HttpParams(String key, File file) {
        init();
        put(key, file);
    }

    private void init() {
        urlParamsMap = new HashMap<>();
    }

    public void put(String key, Object value) {
        urlParamsMap.put(key, value);
    }


    public boolean isEmpty() {
        return urlParamsMap.isEmpty();
    }

    public String toJsonString(){
        if (!isEmpty()){
            return gson.toJson(urlParamsMap);
        }
        return "{}";
    }

}
