package com.caldremch.android.http.http

import com.caldremch.android.http.Api
import com.caldremch.android.http.SimpleRequestConfig
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
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

        val config = SimpleRequestConfig.serverUrlConfig
            ?: throw RuntimeException("please register SimpleRequest")

        val baseUrl = if (config.enableConfig()) config.currentUrl() else config.defaultUrl()
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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