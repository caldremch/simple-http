package com.caldremch.request

import android.content.Context
import com.caldremch.*
import com.caldremch.callback.AbsCallback
import com.caldremch.callback.DialogCallback
import com.caldremch.http.RetrofitHelper
import com.caldremch.observer.AbsObserver
import com.caldremch.observer.IObserverHandler
import com.caldremch.observer.Option
import com.caldremch.parse.HttpParams
import com.caldremch.parse.HttpPath
import com.caldremch.parse.HttpUtils
import com.caldremch.utils.RxUtils
import io.reactivex.Observable
import okhttp3.ResponseBody
import java.lang.RuntimeException

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
abstract class BaseRequest(var url: String, @Method var type:Int) : IRequest {

    protected var httpParams: HttpParams = HttpParams()

    protected var httpPath: HttpPath = HttpPath()

    protected var context: Context? = null

    //是否显示弹窗
    protected var isShowDialog = false

    protected var dialogTips: String? = ""

    //是否显示toast
    protected var isShowToast = true

    protected lateinit var api: Api

    init {
        api = RetrofitHelper.instance.getApi()
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
//            observable = observable.compose(RxUtils.bindToLifecycle(context))
        }

        if (callback is DialogCallback) {
            context = callback.context
            if (context != null) {
                dialogTips = callback.tips
                isShowDialog = true
            }
        }


        val convert = SimpleRequestUtils.getConvert()
        val obsHandler = SimpleRequestUtils.getObserverHandler()

        if (convert == null || obsHandler == null){
            throw RuntimeException("please register convert and observer handler")
        }

        val observer =
            AbsObserver(
                callback,
                context,
                Option(isShowDialog, isShowToast, dialogTips),
                obsHandler
            )

        observable.compose(RxUtils.applySchedulers())
            .map(TransFunction<T>(HttpUtils.getType(callback), convert))
            .subscribe(observer)

    }

}