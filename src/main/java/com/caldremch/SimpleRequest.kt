package com.caldremch

import com.caldremch.convert.IConvert
import com.caldremch.observer.IObserverHandler
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

    fun post(url: String): PostRequest {
        return PostRequest(url)
    }

    fun get(url: String): GetRequest {
        return GetRequest(url)
    }
}