package com.caldremch.request

import android.content.Context
import androidx.lifecycle.Lifecycle
import autodispose2.AutoDispose
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import com.caldremch.Api
import com.caldremch.Method
import com.caldremch.SimpleRequest
import com.caldremch.callback.AbsCallback
import com.caldremch.callback.DialogCallback
import com.caldremch.custom.IConvert
import com.caldremch.http.RequestHelper
import com.caldremch.observer.AbsObserver
import com.caldremch.observer.Option
import com.caldremch.parse.HttpParams
import com.caldremch.parse.HttpPath
import com.caldremch.parse.HttpUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody
import java.lang.reflect.Type

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
    private var lifeCycle: Lifecycle? = null

    //是否显示弹窗
    private var isShowDialog = false

    private var dialogTips: String? = ""

    //是否显示toast
    private var isShowToast = true

    protected var api: Api

    init {

        //需要报错
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

        val observable = obs

        if (context == null && isShowDialog) {
            isShowToast = false
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


        val callbackType = HttpUtils.getType(callback)

        if (lifeCycle != null) {
            observable
                .compose(transform<T>(convert, callbackType))
                .to(AutoDispose.autoDisposable<T>(AndroidLifecycleScopeProvider.from(lifeCycle)))
                .subscribe(observer)
        } else {
            observable
                .compose(transform<T>(convert, callbackType))
                .subscribe(observer)
        }

    }

    fun build():GoRequest{
       return GoRequest()
    }

    private fun <R> transform(
        convert: IConvert,
        callbackType: Type
    ): ObservableTransformer<ResponseBody, R> {
        return object : ObservableTransformer<ResponseBody, R> {
            override fun apply(upstream: Observable<ResponseBody>): ObservableSource<R> {
                return upstream.flatMap(object : Function<ResponseBody, ObservableSource<R>> {
                    override fun apply(responseBody: ResponseBody): ObservableSource<R> {
                        return Observable.create(object : ObservableOnSubscribe<R> {
                            override fun subscribe(emitter: ObservableEmitter<R>) {
                                emitter.onNext(convert.convert(responseBody, callbackType))
                            }
                        })
                    }

                }).subscribeOn(Schedulers.io())
                    .unsubscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread());
            }
        }

    }


}



