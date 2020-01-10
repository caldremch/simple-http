package com.caldremch.simplehttp.customhttp

import com.caldremch.exception.ApiHttpException
import com.caldremch.custom.IObserverHandler
import com.caldremch.simplehttp.ToastUtils

/**
 *
 * @author Caldremch
 *
 * @date 2020-01-10 10:38
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class SampleObsHandler : IObserverHandler {

    override fun showDialog() {
    }

    override fun closeDialog() {

    }

    override fun onError(e: Throwable) {
        if (e is ApiHttpException){
            ToastUtils.show(e.message)
        }
    }
}