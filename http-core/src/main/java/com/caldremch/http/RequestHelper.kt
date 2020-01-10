package com.caldremch.http

import com.caldremch.Api
import com.caldremch.SimpleRequest
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit

/**
 *
 * @author Caldremch
 *
 * @date 2020-01-09 14:57
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/

class RequestHelper {

    private val defualt_timeout = 20L

    private val gson: Gson = Gson()

    private var clientBuilder: OkHttpClient.Builder

    private var retrofit: Retrofit

    init {

        clientBuilder = OkHttpClient.Builder()
            .readTimeout(defualt_timeout, TimeUnit.SECONDS)
            .connectTimeout(defualt_timeout, TimeUnit.SECONDS)

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        clientBuilder.addInterceptor(loggingInterceptor)

        val config = SimpleRequest.getServerUrlConfig()
            ?: throw RuntimeException("please register SimpleRequest")

        val baseUrl = if (config.enableConfig())  config.defaultUrl() else config.currentUrl()
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(clientBuilder.build())
            .build()
    }


    private fun <T> create(clz: Class<T>): T {
        return retrofit.create(clz)
    }

    fun getApi(): Api {
        return create(Api::class.java)
    }

    companion object {

        val INSTANCE: RequestHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RequestHelper()
        }
    }
}