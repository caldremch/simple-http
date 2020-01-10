package com.caldremch.simplehttp

import android.app.Application
import com.caldremch.SimpleRequest
import com.caldremch.simplehttp.customhttp.SampleConvert
import com.caldremch.simplehttp.customhttp.SampleObsHandler
import com.caldremch.simplehttp.customhttp.SampleServerUrlConfig

/**
 *
 * @author Caldremch
 *
 * @date 2020-01-10 11:17
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ToastUtils.init(this)
        SimpleRequest.register(SampleConvert(), SampleObsHandler(), SampleServerUrlConfig())
    }

}