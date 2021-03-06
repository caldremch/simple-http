package com.caldremch.simplehttp.customhttp

import com.caldremch.android.http.custom.IConvert
import com.caldremch.android.http.exception.ApiHttpException
import com.caldremch.android.http.exception.NullDataSuccessException
import com.google.gson.*
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
class SampleConvert2 : IConvert {

    private val mGson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create()
    private val mParser = JsonParser()


    override fun <T> convert(responseBody: ResponseBody, clz: Class<T>): T {

        responseBody.use {
            val jsonRespStr: String = responseBody.string()
            val jsonObject = mParser.parse(jsonRespStr).asJsonObject
            val dataJson = jsonObject["message"]
            val respType = jsonObject["type"]

            if (respType != null) {

                val status = respType.asString

                if ("ok" != status) {

                    //错误处理
                    //抛出后, 在 Observer 中处理
                    var errorMsg: String? = ""
                    var errorCode: Int = -1
                    if (jsonObject["message"] != null) {
                        val errorMessage = jsonObject["message"] as JsonObject
                        errorCode = errorMessage["code"].asInt
                        errorMsg = errorMessage["errmsg"].asString
                    }
                    throw ApiHttpException(errorCode, errorMsg)
                }

                if ((dataJson == null || dataJson is JsonNull) && "ok" == status) {
                    throw NullDataSuccessException()
                }
            }

            if (dataJson == null || dataJson is JsonNull) throw NullDataSuccessException()

            val dataStr = dataJson.toString()

            return Any() as T

//            return if (type == String::class.java) {
//                dataStr as T
//            } else {
//                mGson.fromJson(dataStr, type)
//            }
        }
    }
}

class Resp<T>{
    var message:String? = null
    var code:Int? = null
    var errmsg:String? = null
}
