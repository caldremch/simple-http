package com.caldremch.custom

import io.reactivex.rxjava3.disposables.Disposable


/**
 *
 * @author Caldremch
 *
 * @date 2020-01-09 17:56
 *
 * @email caldremch@163.com
 *
 * @describe 可以处理Observer所有声明周期方法
 *
 **/
interface IObserverHandler {
    /**
     * 处理弹窗相关逻辑
     */
    fun showDialog()

    fun closeDialog()

    fun onInit() {}
    fun onSubscribe(d: Disposable) {}
    fun onNext(data: Any?) {}
    //用户处理错误
    fun onError(e: Throwable)

    fun onComplete() {}
}