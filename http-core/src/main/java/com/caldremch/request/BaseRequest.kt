package com.caldremch.request

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.caldremch.Api
import com.caldremch.Method
import com.caldremch.SimpleRequest
import com.caldremch.callback.AbsCallback
import com.caldremch.callback.DialogCallback
import com.caldremch.function.TransFunction
import com.caldremch.http.RequestHelper
import com.caldremch.observer.AbsObserver
import com.caldremch.observer.Option
import com.caldremch.parse.HttpParams
import com.caldremch.parse.HttpPath
import com.caldremch.parse.HttpUtils
import com.caldremch.utils.RxUtils
import com.trello.rxlifecycle3.LifecycleProvider
import io.reactivex.Observable
import okhttp3.ResponseBody

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
abstract class BaseRequest(var url: String, @Method var type: Int) : IRequest {

    protected var httpParams: HttpParams = HttpParams()

    private var httpPath: HttpPath = HttpPath()

    private var context: Context? = null

    //是否显示弹窗
    private var isShowDialog = false

    private var dialogTips: String? = ""

    //是否显示toast
    private var isShowToast = true

    protected var api: Api

    init {
        val config = SimpleRequest.getServerUrlConfig()
            ?: throw RuntimeException("please register SimpleRequest")
        api =
            if (config.enableConfig()) RequestHelper().getApi() else RequestHelper.INSTANCE.getApi()
    }

    fun with(context: Context): BaseRequest {
        this.context = context
        return this
    }

    fun put(key: String, value: Any?): BaseRequest {
        value?.let {
            httpParams.put(key, value)
        }
        return this
    }


    fun path(pathName: String, value: String): BaseRequest {
        httpPath.put(pathName, value)
        return this
    }

    fun disableToast(): BaseRequest {
        this.isShowToast = false
        return this
    }


    protected fun <T> go(obs: Observable<ResponseBody>, callback: AbsCallback<T>) {

        var observable = obs
        if (context == null && isShowDialog) {
            isShowToast = false
        }

        if (context != null) {
            if (context is LifecycleProvider<*>) {
                observable = observable.compose(RxUtils.bindToLifecycle(context))
            }
        }

        if (callback is DialogCallback) {
            context = callback.context
            if (context != null) {
                dialogTips = callback.tips
                isShowDialog = true
            }
        }


        val convert = SimpleRequest.getConvert()
        val obsHandler = SimpleRequest.getObserverHandler()

        if (convert == null || obsHandler == null) {
            throw RuntimeException("please register SimpleRequest")
        }

        val observer =
            AbsObserver(
                callback,
                context,
                Option(isShowDialog, isShowToast, dialogTips),
                obsHandler
            )

        observable.compose(RxUtils.applySchedulers())
            .map(
                TransFunction<T>(
                    HttpUtils.getType(
                        callback
                    ), convert
                )
            )
            .subscribe(observer)

    }

}