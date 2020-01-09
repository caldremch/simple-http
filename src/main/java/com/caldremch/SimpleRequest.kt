package com.caldremch

import android.content.Context
import com.caldremch.callback.AbsCallback
import com.caldremch.callback.DialogCallback
import com.caldremch.convert.CommonConvert
import com.caldremch.http.RetrofitHelper
import com.caldremch.observer.AbsObserver
import com.caldremch.observer.IObserver
import com.caldremch.observer.Option
import com.caldremch.parse.HttpParams
import com.caldremch.parse.HttpPath
import com.caldremch.parse.HttpUtils
import com.caldremch.utils.RxUtils
import com.google.gson.Gson
import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody

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
class SimpleRequest : RequestBuilderOption() {

    companion object {

        var realConvert: Class<in CommonConvert>? = null
        var realObserver: Class<in IObserver>? = null

        fun register(clz: Class<in CommonConvert>, obs: Class<in IObserver>) {
            realConvert = clz
            realObserver = obs
        }


        fun post(url: String): RequestBuilder {
            return RequestBuilder(url, Method.POST)
        }

        fun get(url: String): RequestBuilder {
            return RequestBuilder(url, Method.GET)
        }

        fun file(url: String): RequestBuilder {
            return RequestBuilder(url, Method.FILE)
        }

    }


    fun execute() {


    }

    private fun <T> go(obs: Observable<ResponseBody>, callback: AbsCallback<T>) {
        var observable = obs
        if (context == null && isShowDialog) {
            isShowToast = false
        }
        if (context != null) {
            observable = observable.compose(RxUtils.bindToLifecycle(context))
        }

        if (callback is DialogCallback) {
            context = callback.context
            if (context != null) {
                dialogTips = callback.tips
                isShowDialog = true
            }
        }


        val convert = realConvert?.newInstance() as CommonConvert
        val obsHandler = realObserver?.newInstance() as IObserver

        val observer =
            AbsObserver(
                callback,
                context,
                Option(isShowDialog, isShowToast, dialogTips),
                obsHandler
            )

        observable.compose(RxUtils.applySchedulers())
            .map(TransFunction<T>(HttpUtils.getType(callback), convert))
            .subscribe(observer)

    }

    class RequestBuilder(url: String, type: Int) : RequestBuilderOption() {

        init {
            this.url = url
            this.type = type
        }

        fun with(context: Context): RequestBuilder {
            this.context = context
            return this
        }

        fun put(key: String, value: Any): RequestBuilder {
            if (value != null) {
                httpParams!!.put(key, value)
            }
            return this
        }

        fun put(body: RequestBody): RequestBuilder {
            requestBody = body
            return this
        }

        fun path(pathName: String, value: String): RequestBuilder {
            if (httpPath == null) {
                httpPath = HttpPath()
            }
            httpPath!!.put(pathName, value)
            return this
        }

        fun put(body: Any): RequestBuilder {
            if (type == Method.GET && body is Map<*, *>) {
                val d =
                    body as Map<String, Any>
                httpParams!!.setUrlParamsMap(d)
                return this
            }
            if (body is RequestBody) {
                requestBody = body
            } else {
                requestBody = Gson().toJson(body).toRequestBody(HttpParams.MEDIA_TYPE_JSON)
            }
            return this
        }

        fun formUrlEncoded(): RequestBuilder {
            this.formUrlEncoded = true
            return this
        }

        fun postQuery(): RequestBuilder {
            this.postQuery = true
            return this
        }

        fun disableToast(): RequestBuilder {
            this.isShowToast = false
            return this
        }


        fun <T> execute(callback: AbsCallback<T>) {

            val request = SimpleRequest()

            //todo build 与 simpleRequest 赋, 这样的 builder 是否合理

            var api: Api = RetrofitHelper.instance.getApi()
            if (httpPath != null && !httpPath!!.isEmpty) {
                url = httpPath!!.getPathUrl(url)

            }

            when (type) {
                Method.GET -> if (httpParams!!.isEmpty) {
                    request.go<T>(api[url!!], callback)
                } else {
                    request.go<T>(api[url!!, httpParams!!.urlParams], callback)
                }
                Method.POST -> {
                    //body优先
                    if (requestBody != null) {
                        request.go<T>(api.post(url!!, requestBody!!), callback)
                        return
                    }
                    if (httpParams!!.isEmpty) { //jpark服务器接口需要传一个空的body, 不能不传
                        val body: RequestBody = "{}".toRequestBody(HttpParams.MEDIA_TYPE_JSON)
                        request.go<T>(api.post(url!!, body), callback)
                    } else {
                        if (formUrlEncoded) {
                            request.go<T>(api.post(url!!, httpParams!!.urlParams), callback)
                        } else if (postQuery) {
                            request.go<T>(api.postQuery(url!!, httpParams!!.urlParams), callback)
                        } else {
                            val body: RequestBody =
                                httpParams!!.toJsonString().toRequestBody(HttpParams.MEDIA_TYPE_JSON)
                            request.go<T>(api.post(url!!, body), callback)
                        }
                    }
                }
//                Method.FILE -> if (parts != null) {
//                    go<T>(api.upload(parts), callback)
//                }
            }

        }


    }
}