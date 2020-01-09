package com.caldremch.parse

import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.File
import java.util.*
import okhttp3.MediaType.Companion.toMediaType

/**
 * @author Caldremch
 * @date 2019-08-20 15:54
 * @email caldremch@163.com
 * @describe
 */
class HttpParams {
    private var urlParamsMap: MutableMap<String, Any> = mutableMapOf()

    val urlParams: MutableMap<String, Any>?
        get() = urlParamsMap

    fun setUrlParamsMap(urlParamsMap: MutableMap<String, Any>) {
        this.urlParamsMap = urlParamsMap
    }

    constructor() {
    }

    constructor(key: String, value: String) {
        put(key, value)
    }

    constructor(key: String, file: File) {
        put(key, file)
    }


    fun put(key: String, value: Any) {
        urlParamsMap!![key] = value
    }

    val isEmpty: Boolean
        get() = urlParamsMap!!.isEmpty()

    fun toJsonString(): String {
        return if (!isEmpty) {
            gson.toJson(urlParamsMap)
        } else "{}"
    }

    companion object {
        private val gson = Gson()
        val MEDIA_TYPE_PLAIN: MediaType = "text/plain;charset=utf-8".toMediaType()
        val MEDIA_TYPE_JSON: MediaType = "application/json;charset=utf-8".toMediaType()
        val MEDIA_TYPE_STREAM: MediaType = "application/octet-stream".toMediaType()
    }
}