package com.caldremch.request

import com.caldremch.Method
import com.caldremch.ParamsBuilder
import com.caldremch.callback.AbsCallback
import com.caldremch.parse.HttpParams

/**
 *
 * @author Caldremch
 *
 * @date 2020-01-09 16:43
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class GetRequestExt(url: String) : BaseRequestExt<GetRequestExt>(url,  Method.GET) {

    override fun <T> execute(callback: AbsCallback<T>) {

    }

    inline fun <reified T> exec(callback: AbsCallback<T>){
        if(httpParams.isEmpty) {
            go<T>(api.get(url), callback)
        } else {
            go<T>(api.get(url, httpParams.urlParams), callback)
        }
    }
}