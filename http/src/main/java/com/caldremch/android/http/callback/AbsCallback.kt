package com.caldremch.android.http.callback

import androidx.annotation.RestrictTo
import io.reactivex.rxjava3.disposables.Disposable

/**
 *
 * @author Caldremch
 *
 * @date 2020-01-09 15:14
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
@RestrictTo(RestrictTo.Scope.LIBRARY)
interface AbsCallback<T> {

    /**
     *  onSubscribe(Disposable d) 调用时, 用于取消订阅等操作
     * @param disposable
     */
    fun onPreRequest(disposable: Disposable?) {}

    /**
     * 成功回调返回
     * @param data 具体解析类型
     */
    fun onSuccess(data: T?)

    fun onError(e: Throwable?) {}
}