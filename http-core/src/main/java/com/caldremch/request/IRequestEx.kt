package com.caldremch.request

import com.caldremch.callback.AbsCallback

/**
 *
 * @author Caldremch
 *
 * @date 2020-01-10 09:41
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
interface IRequestEx<R> {
    fun <T> execute(callback: AbsCallback<T>)
    fun put(key: String, value: Any?): R
    fun path(pathName: String, value: String):R
    fun disableToast(): R
}