package com.caldremch.function

import com.caldremch.custom.IConvert
import io.reactivex.rxjava3.functions.Function
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