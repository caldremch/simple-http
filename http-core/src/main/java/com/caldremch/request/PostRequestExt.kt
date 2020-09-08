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
class PostRequestExt(url: String) : BaseRequestExt<PostRequestExt>(url, Method.POST),
    IPostRequest<PostRequestExt> {

    /**
     * FormUrlEncoded 形式
     */
    @PublishedApi
    internal var formUrlEncoded = false

    /**
     * 容我孤陋寡闻 post链接 拼接参数
     */
    @PublishedApi
    internal var postQuery = false

    @PublishedApi
    internal var requestBody: RequestBody? = null


    override fun put(body: RequestBody): PostRequestExt {
        requestBody = body
        return this
    }

    override fun put(body: Any): PostRequestExt {
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

    override fun formUrlEncoded(): PostRequestExt {
        this.formUrlEncoded = true
        return this
    }

    override fun postQuery(): PostRequestExt {
        this.postQuery = true
        return this
    }

    inline fun <reified T> execute(callback: AbsCallback<T>) {

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


    @PublishedApi
    internal fun getHttpParamsBody(): RequestBody {
        if (httpParams.isEmpty) {
            return "{}".toRequestBody(HttpParams.MEDIA_TYPE_JSON)
        }
        return httpParams.toJsonString().toRequestBody(HttpParams.MEDIA_TYPE_JSON)
    }


}