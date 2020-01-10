package com.caldremch.request

import com.caldremch.Method
import com.caldremch.callback.AbsCallback
import com.caldremch.parse.HttpParams
import com.google.gson.Gson
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

/**
 *
 * @author Caldremch
 *
 * @date 2020-01-09 16:43
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class PostRequest(url: String) : BaseRequest(url, Method.POST) {
    /**
     * FormUrlEncoded 形式
     */
    protected var formUrlEncoded = false
    /**
     * 容我孤陋寡闻 post链接 拼接参数
     */
    protected var postQuery = false

    protected var requestBody: RequestBody? = null


    fun put(body: RequestBody): PostRequest {
        requestBody = body
        return this
    }

    fun put(body: Any): BaseRequest {
        if (type == Method.GET && body is Map<*, *>) {
            httpParams.setUrlParamsMap(body as MutableMap<String, Any>)
            return this
        }

        if (body is RequestBody) {
            requestBody = body
        } else {
            requestBody = Gson().toJson(body).toRequestBody(HttpParams.MEDIA_TYPE_JSON)
        }
        return this
    }

    fun formUrlEncoded(): BaseRequest {
        this.formUrlEncoded = true
        return this
    }

    fun postQuery(): BaseRequest {
        this.postQuery = true
        return this
    }

    override fun <T> execute(callback: AbsCallback<T>) {

        //post body
        if (requestBody != null) {
            go<T>(api.post(url, requestBody!!), callback)
            return
        }

        //post 空 body
        if (httpParams.isEmpty) {
            go<T>(api.post(url, getHttpParamsBody()), callback)
            return
        }

        //post formUrlEncoded
        if (formUrlEncoded) {
            go<T>(api.post(url, httpParams.urlParams), callback)
            return
        }

        //post动态链接 url后面拼接 key/value
        if (postQuery) {
            go<T>(api.postQuery(url, httpParams.urlParams), callback)
            return
        }

        //post json body
        go<T>(api.post(url, getHttpParamsBody()), callback)
    }

    private fun getHttpParamsBody(): RequestBody {
        if (httpParams.isEmpty) {
            return "{}".toRequestBody(HttpParams.MEDIA_TYPE_JSON)
        }
        return httpParams.toJsonString().toRequestBody(HttpParams.MEDIA_TYPE_JSON)
    }
}