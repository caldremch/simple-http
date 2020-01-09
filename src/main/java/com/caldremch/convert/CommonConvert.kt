package com.caldremch.convert

import okhttp3.ResponseBody
import java.lang.reflect.Type

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
interface CommonConvert {
    fun <T> convert(responseBody: ResponseBody, type: Type): T
}