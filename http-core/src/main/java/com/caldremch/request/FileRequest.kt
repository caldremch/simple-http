package com.caldremch.request

import com.caldremch.Method
import com.caldremch.callback.AbsCallback
import okhttp3.MultipartBody

/**
 *
 * @author Caldremch
 *
 * @date 2020-01-09 16:43
 *
 * @email caldremch@163.com
 *
 * @describe todo
 *
 **/
class FileRequest(url: String) : BaseRequest(url,  Method.FILE) {

    protected var parts: List<MultipartBody.Part>? = null

    override fun <T> execute(callback: AbsCallback<T>) {

    }

}