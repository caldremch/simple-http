package com.caldremch

import com.caldremch.custom.IConvert
import com.caldremch.custom.IServerUrlConfig
import com.caldremch.custom.IObserverHandler
import com.caldremch.request.GetRequest
import com.caldremch.request.PostRequest

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

    private var sConvert: IConvert? = null

    private var sObserverHandler: IObserverHandler? = null

    private var serverUrlConfig: IServerUrlConfig? = null

    fun register(convert: IConvert, handler: IObserverHandler, urlConfig: IServerUrlConfig) {
        sConvert = convert
        sObserverHandler = handler
        serverUrlConfig = urlConfig
    }

    fun getConvert(): IConvert? {
        return sConvert
    }

    fun getServerUrlConfig(): IServerUrlConfig? {
        return serverUrlConfig
    }

    fun getObserverHandler(): IObserverHandler? {
        return sObserverHandler
    }

    /**
     * post 请求
     */
    fun post(url: String): PostRequest {
        return PostRequest(url)
    }

    /**
     * get 请求
     */
    fun get(url: String): GetRequest {
        return GetRequest(url)
    }
}