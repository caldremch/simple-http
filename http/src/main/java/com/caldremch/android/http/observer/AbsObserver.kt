package com.caldremch.android.http.observer

import androidx.annotation.RestrictTo
import com.caldremch.android.http.callback.AbsCallback
import com.caldremch.android.http.callback.DialogCallback
import com.caldremch.android.http.custom.IObserverHandler
import com.caldremch.android.http.exception.NullDataSuccessException
import io.reactivex.rxjava3.disposables.Disposable
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
@RestrictTo(RestrictTo.Scope.LIBRARY)
class AbsObserver<T>(
    var callback: AbsCallback<T>?,
    var handler: IObserverHandler?
) : Observer<T> {

    private var d: Disposable? = null

    init {
        handler?.onInit()
        if (isDialogCallback()) {
            val dialogCallback = callback as DialogCallback<T>
            handler?.showDialog(dialogCallback.context)
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

    /**
     * 是否是 [DialogCallback]
     */
    private fun isDialogCallback(): Boolean{
        if (callback is DialogCallback<T>){
            return true
        }
        return false
    }


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

        //close dialog
        if (isDialogCallback()) {
            handler?.closeDialog()
        }

        /**
         * code is 200, but null T ,[AbsCallback.onSuccess] callback
         */
        if (e is NullDataSuccessException) {
            callback?.onSuccess(null)
            return
        }

        /**
         * handle error callback
         */
        handler?.onError(e)
        callback?.onError(e)
    }


    override fun onComplete() {

        handler?.onComplete()
        if (isDialogCallback()) {
            handler?.closeDialog()
        }
    }




}