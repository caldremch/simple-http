package com.caldremch

import com.caldremch.custom.IConvert
import com.caldremch.custom.IObserverHandler
import com.caldremch.custom.IServerUrlConfig
import com.caldremch.http.ConfigOption
import com.caldremch.request.GetRequest
import com.caldremch.request.PostRequest
import com.google.gson.Gson

/**
 *
 * @author Caldremch
 *
 * @date 2020-01-09 16:07
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
object SimpleRequest {

    //请求相关配置信息
    private val configOption = ConfigOption()

    /**
     * 设置自定义Gson
     */
    fun setGson(gson: Gson) {
        configOption.gson = gson
    }

    fun register(convert: IConvert, handler: IObserverHandler, urlConfig: IServerUrlConfig) {
        SimpleRequestConfig.sConvert = convert
        SimpleRequestConfig.sObserverHandler = handler
        SimpleRequestConfig.serverUrlConfig = urlConfig
    }

    /**
     * post 请求
     */
    inline fun <reified T> post(url: String): PostRequest {
        return PostRequest<T>(url)
    }

    /**
     * get 请求
     */
    fun get(url: String): GetRequest {
        return GetRequest(url)
    }
}