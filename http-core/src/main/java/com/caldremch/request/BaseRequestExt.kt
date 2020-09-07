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
abstract class BaseRequestExt<R:IRequest>(var url: String, @Method var type: Int) : IRequestEx<R> {

    @PublishedApi
    internal var httpParams: HttpParams = HttpParams()
    protected var httpPath: HttpPath = HttpPath()
    @PublishedApi
    internal var lifeCycle: Lifecycle? = null
    //是否显示toast
    protected var isShowToast = true
    @PublishedApi
    internal var api: Api

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

    override fun put(key: String, value: Any?): R {
                value?.let {
            httpParams.put(key, value)
        }
        return this
    }


    override fun path(pathName: String, value: String): BaseRequestExt<R> {
        httpPath.put(pathName, value)
        return this
    }

    override  fun disableToast(): BaseRequestExt<R> {
        this.isShowToast = false
        return this
    }



    @PublishedApi
    internal  inline fun <reified T> go(obs: Observable<ResponseBody>, callback: AbsCallback<T>) {

        val convert = SimpleRequestConfig.sConvert
        val obsHandler = SimpleRequestConfig.sObserverHandler

        if (convert == null || obsHandler == null) {
            throw RuntimeException("please register SimpleRequest")
        }

        val observer = AbsObserver(callback, obsHandler)


        if (lifeCycle != null) {
            obs.compose(transform<T>())
                .to(AutoDispose.autoDisposable<T>(AndroidLifecycleScopeProvider.from(lifeCycle)))
                .subscribe(observer)
        } else {
            obs.compose(transform<T>()).subscribe(observer)
        }

    }



    /**
     * [ResponseBody] -> [ObservableOnSubscribe]
     */
    @PublishedApi
    internal inline fun <reified R> transform(
    ): ObservableTransformer<ResponseBody, R> {
        return object : ObservableTransformer<ResponseBody, R> {
            override fun apply(upstream: Observable<ResponseBody>): ObservableSource<R> {
                return upstream.flatMap(object : Function<ResponseBody, ObservableSource<R>> {
                    override fun apply(responseBody: ResponseBody): ObservableSource<R> {
                        return Observable.create(object : ObservableOnSubscribe<R> {
                            override fun subscribe(emitter: ObservableEmitter<R>) {
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



