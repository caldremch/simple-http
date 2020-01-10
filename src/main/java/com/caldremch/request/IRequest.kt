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
interface IRequest {
    fun <T> execute(callback: AbsCallback<T>)
}