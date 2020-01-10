package com.caldremch.observer

/**
 *
 * @author Caldremch
 *
 * @date 2020-01-09 17:56
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
interface IObserverHandler {

    /**
     * 处理弹窗相关逻辑
     */
     fun showDialog()

     fun closeDialog()

    /**
     * 用户自己处理错误
     */
     fun handleError(e: Throwable)
}