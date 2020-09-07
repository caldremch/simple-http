package com.caldremch.request

import androidx.lifecycle.Lifecycle
import autodispose2.AutoDispose
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import com.caldremch.*
import com.caldremch.callback.AbsCallback
import com.caldremch.custom.IConvert
import com.caldremch.http.RequestHelper
import com.caldremch.observer.AbsObserver
import com.caldremch.parse.HttpParams
import com.caldremch.parse.HttpPath
import com.caldremch.parse.HttpUtilsEx
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
    protected var httpPath: HttpPath = HttpPath()
    protected var lifeCycle: Lifecycle? = null
    //是否显示toast
    protected var isShowToast = true
    protected var api: Api

    init {

        //must config serverUrlConfig
        SimpleRequestConfig.serverUrlConfig
            ?: throw RuntimeException("please register SimpleRequest")
        /**
         * [IServerUrlConfig] enableConfig
         */
        api =
            if (SimpleRequestConfig.serverUrlConfig!!.enableConfig()) RequestHelper().getApi() else RequestHelper.INSTANCE.getApi()
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

    protected inline fun <reified T> go(obs: Observable<ResponseBody>, callback: AbsCallback<T>) {

        val convert = SimpleRequestConfig.sConvert
        val obsHandler = SimpleRequestConfig.sObserverHandler

        if (convert == null || obsHandler == null) {
            throw RuntimeException("please register SimpleRequest")
        }

        val observer = AbsObserver(callback, obsHandler)

        val callbackType = HttpUtilsEx.getType<T>(callback)

        if (lifeCycle != null) {
            obs.compose(transform<T>(convert, callbackType))
                .to(AutoDispose.autoDisposable<T>(AndroidLifecycleScopeProvider.from(lifeCycle)))
                .subscribe(observer)
        } else {
            obs.compose(transform<T>(convert, callbackType)).subscribe(observer)
        }

    }


    protected inline fun goByParams(params: TempParams) {
        if (params.requestBody != null) {
            go<T>(api.post(url, requestBody!!), callback)
            return
        }

        //post 空 body
        if (httpParams.isEmpty) {
            go<T>(api.post(url, getHttpParamsBody()), callback)
            return
        }

        //post formUrlEncoded
        if (formUrlEncoded) {
            go<T>(api.post(url, httpParams.urlParams), callback)
            return
        }

        //post动态链接 url后面拼接 key/value
        if (postQuery) {
            go<T>(api.postQuery(url, httpParams.urlParams), callback)
            return
        }

        //post json body
        go<T>(api.post(url, getHttpParamsBody()), callback)
    }

    /**
     * [ResponseBody] -> [ObservableOnSubscribe]
     */
    protected fun <R> transform(
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



