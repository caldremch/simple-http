package com.caldremch.observer

import android.content.Context
import androidx.lifecycle.*
import com.caldremch.callback.AbsCallback
import com.caldremch.custom.IObserverHandler
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.lang.NullPointerException
import io.reactivex.rxjava3.core.Observer
/**
 *
 * @author Caldremch
 *
 * @date 2020-01-09 15:38
 *
 * @email caldremch@163.com
 *
 * @describe handle data
 *
 **/
class AbsObserver<T>(
    var callback: AbsCallback<T>?,
    var context: Context?,
    var option: Option,
    var handler: IObserverHandler?
) : Observer<T> {

    private var d: Disposable? = null

    init {

        registerRawLifecycle()

        handler?.onInit()

        if (canShowDialog()) {
            handler?.showDialog()
        }
    }

    /**
     * when lifecycle is destroy, check subscribe
     */
   private fun destroy(){
        d?.let {
            if (!it.isDisposed){
                it.dispose()
            }
        }
    }

    private fun canShowDialog(): Boolean = option.isShowDialog && context != null


    override fun onSubscribe(d: Disposable) {
        this.d = d
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

        unRegisterRawLifecycle()
    }


    override fun onComplete() {

        handler?.onComplete()

        if (canShowDialog()) {
            handler?.closeDialog()
        }

        unRegisterRawLifecycle()
    }

    /**
     * register the android lifecycle
     */
    private fun registerRawLifecycle() {
        context?.let {
            if (it is LifecycleOwner) {
                it.lifecycle.addObserver(object : DefaultLifecycleObserver{
                    override fun onDestroy(owner: LifecycleOwner) {
                        destroy()
                    }
                })
            }
        }
    }

    /**
     * remove  android lifecycle observer
     */
    private fun unRegisterRawLifecycle() {
        context?.let {
            if (it is LifecycleOwner) {
//                it.lifecycle.removeObserver(this)
            }
        }
    }



}