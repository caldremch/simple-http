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
        return true
    }

    override fun currentUrl(): String {
        return ""
    }

    override fun defaultUrl(): String {
        return ""
    }
}