package com.caldremch.simplehttp

import android.app.Application
import com.caldremch.SimpleRequestUtils
import com.caldremch.simplehttp.example.SampleConvert
import com.caldremch.simplehttp.example.SampleObsHandler

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
        SimpleRequestUtils.register(SampleConvert(), SampleObsHandler())
    }

}