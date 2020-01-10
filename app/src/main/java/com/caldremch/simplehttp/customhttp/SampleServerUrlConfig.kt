package com.caldremch.simplehttp.customhttp

import com.caldremch.custom.IServerUrlConfig

/**
 *
 * @author Caldremch
 *
 * @date 2020-01-10 11:54
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class SampleServerUrlConfig : IServerUrlConfig {

    override fun enableConfig(): Boolean {
        return false
    }

    override fun currentUrl(): String {
        return "https://www.jpark.vip/"
    }

    override fun defaultUrl(): String {
        return "https://www.jpark.vip/"
    }
}