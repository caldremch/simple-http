package com.caldremch.observer

import android.content.Context
import com.caldremch.callback.AbsCallback
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.lang.NullPointerException

/**
 *
 * @author Caldremch
 *
 * @date 2020-01-09 15:38
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class AbsObserver<T>(
    var callback: AbsCallback<T>?,
    var context: Context?,
    var option: Option,
    var handler: IObserverHandler?
) : Observer<T> {

    init {

        handler?.onInit()

        if (canShowDialog()) {
            handler?.showDialog()
        }
    }

    private fun canShowDialog(): Boolean = option.isShowDialog && context != null

    override fun onComplete() {
        handler?.onComplete()
        if (canShowDialog()) {
            handler?.closeDialog()
        }
    }

    override fun onSubscribe(d: Disposable) {
        handler?.onSubscribe(d)
        callback?.onPreRequest(d)
    }

    override fun onNext(t: T) {
        handler?.onNext(t)
        callback?.onSuccess(t)
    }

    override fun onError(e: Throwable) {

        if (canShowDialog()) {
            handler?.closeDialog()
        }

        if (e is NullPointerException) {
            callback?.onSuccess(null)
            return
        }

        handler?.onError(e)

        callback?.onError(e)
    }

}