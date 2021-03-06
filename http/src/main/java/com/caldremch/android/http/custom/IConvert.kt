package com.caldremch.android.http.custom

import okhttp3.ResponseBody

/**
 *
 * @author Caldremch
 *
 * @date 2020-01-09 15:33
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
interface IConvert {
    fun <T> convert(responseBody: ResponseBody, clz:Class<T>): T
}