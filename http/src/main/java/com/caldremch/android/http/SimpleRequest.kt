package com.caldremch.android.http

import com.caldremch.android.http.custom.IConvert
import com.caldremch.android.http.custom.IObserverHandler
import com.caldremch.android.http.custom.IServerUrlConfig
import com.caldremch.android.http.http.ConfigOption
import com.caldremch.android.http.request.GetRequestExt
import com.caldremch.android.http.request.PostRequestExt
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
    fun  post(url: String): PostRequestExt {
        return PostRequestExt(url)
    }

    /**
     * get 请求
     */
    fun get(url: String): GetRequestExt {
        return GetRequestExt(url)
    }
}