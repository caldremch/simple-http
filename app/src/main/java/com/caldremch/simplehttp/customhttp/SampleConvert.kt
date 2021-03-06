package com.caldremch.simplehttp.customhttp

import com.caldremch.android.http.custom.IConvert
import com.caldremch.android.http.exception.ApiCode
import com.caldremch.android.http.exception.ApiHttpException
import com.caldremch.android.http.exception.NullDataSuccessException
import com.google.gson.GsonBuilder
import com.google.gson.JsonNull
import com.google.gson.JsonParser
import okhttp3.ResponseBody

/**
 *
 * @author Caldremch
 *
 * @date 2020-01-10 10:32
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class SampleConvert : IConvert {

    private val mGson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create()
    private val mParser = JsonParser()

    override fun <T> convert(responseBody: ResponseBody, clz: Class<T>): T {
        responseBody.use {
            val jsonRespStr: String = responseBody.string()
            val jsonObject = mParser.parse(jsonRespStr).asJsonObject
            val dataJson = jsonObject["data"]
            val javaStatus = jsonObject["status"]

            if (javaStatus != null) {
                val status = javaStatus.asString
                //抛出后, 在 Observer 中处理
                var msg: String? = ""
                if (jsonObject["msg"] != null) {
                    msg = jsonObject["msg"].asString
                }
                if ("notlogin" == status) {
                    throw ApiHttpException(ApiCode.NOT_LOGIN, msg)
                } else if ("error" == status) {
                    throw ApiHttpException(ApiCode.ERROR, msg)
                }
                if ((dataJson == null || dataJson is JsonNull) && "success" == status) {
                    throw NullDataSuccessException()
                }
            }
            if (dataJson == null || dataJson is JsonNull) throw NullDataSuccessException()
            val dataStr = dataJson.toString()
            return  mGson.fromJson(dataStr, clz)
        }
    }

}