package com.caldremch

import com.caldremch.convert.IConvert
import io.reactivex.functions.Function
import okhttp3.ResponseBody
import java.lang.reflect.Type

/**
 *
 * @author Caldremch
 *
 * @date 2020-01-09 15:27
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class TransFunction<T>(var type: Type, var convert: IConvert) : Function<ResponseBody, T> {

    override fun apply(responseBody: ResponseBody): T {
        return convert.convert(responseBody, type)
    }


}