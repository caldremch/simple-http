package com.caldremch.request

import com.caldremch.Api
import com.caldremch.Method
import com.caldremch.TempParams
import com.caldremch.callback.AbsCallback
import com.caldremch.parse.HttpParams
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Observable
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody

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
class PostRequestExt(url: String) : BaseRequestExt<PostRequestExt>(url, Method.POST) {

    /**
     * FormUrlEncoded 形式
     */
    protected var formUrlEncoded = false

    /**
     * 容我孤陋寡闻 post链接 拼接参数
     */
    protected var postQuery = false

    protected var requestBody: RequestBody? = null


    fun put(body: RequestBody): PostRequestExt {
        requestBody = body
        return this
    }

    fun put(body: Any): PostRequestExt {
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

    fun formUrlEncoded(): PostRequestExt {
        this.formUrlEncoded = true
        return this
    }

    fun postQuery(): PostRequestExt {
        this.postQuery = true
        return this
    }


    override fun <T> execute(callback: AbsCallback<T>) {

        val params = TempParams()
        params.formUrlEncoded = formUrlEncoded
        params.requestBody = requestBody
        params.postQuery = postQuery
//        goByParams(params)

        //post body

    }

    inline fun <reified T> exe(callback: AbsCallback<T>) {
        if (`access$requestBody` != null) {
            go<T>(`access$api`.post(url, `access$requestBody`!!), callback)
            return
        }

        //post 空 body
        if (`access$httpParams`.isEmpty) {
            go<T>(`access$api`.post(url, getHttpParamsBody()), callback)
            return
        }

        //post formUrlEncoded
        if (`access$formUrlEncoded`) {
            go<T>(`access$api`.post(url, `access$httpParams`.urlParams), callback)
            return
        }

        //post动态链接 url后面拼接 key/value
        if (`access$postQuery`) {
            go<T>(`access$api`.postQuery(url, `access$httpParams`.urlParams), callback)
            return
        }

        //post json body
        go<T>(`access$api`.post(url, getHttpParamsBody()), callback)
    }


    @PublishedApi
    internal fun getHttpParamsBody(): RequestBody {
        if (httpParams.isEmpty) {
            return "{}".toRequestBody(HttpParams.MEDIA_TYPE_JSON)
        }
        return httpParams.toJsonString().toRequestBody(HttpParams.MEDIA_TYPE_JSON)
    }

    @PublishedApi
    internal var `access$requestBody`: RequestBody?
        get() = requestBody
        set(value) {
            requestBody = value
        }

    @PublishedApi
    internal var `access$api`: Api
        get() = api
        set(value) {
            api = value
        }


    @PublishedApi
    internal var `access$httpParams`: HttpParams
        get() = httpParams
        set(value) {
            httpParams = value
        }

    @PublishedApi
    internal var `access$formUrlEncoded`: Boolean
        get() = formUrlEncoded
        set(value) {
            formUrlEncoded = value
        }

    @PublishedApi
    internal var `access$postQuery`: Boolean
        get() = postQuery
        set(value) {
            postQuery = value
        }
}