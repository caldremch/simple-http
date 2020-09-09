package com.caldremch.android.http.request

import okhttp3.RequestBody

/**
 *
 * @author Caldremch
 *
 * @date 2020-09-08
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
interface IPostRequest<out R : IRequestEx<R>> : IRequestEx<R> {
    fun put(body: RequestBody): R
    fun put(body: Any): R
    fun formUrlEncoded(): R
    fun postQuery(): R
}