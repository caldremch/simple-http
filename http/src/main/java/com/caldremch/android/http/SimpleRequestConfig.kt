package com.caldremch.android.http

import androidx.annotation.RestrictTo
import com.caldremch.android.http.custom.IConvert
import com.caldremch.android.http.custom.IObserverHandler
import com.caldremch.android.http.custom.IServerUrlConfig

/**
 *
 * @author Caldremch
 *
 * @date 2020-09-07 13:22
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
@RestrictTo(RestrictTo.Scope.LIBRARY)
object SimpleRequestConfig {
    var sConvert: IConvert? = null
    var sObserverHandler: IObserverHandler? = null
    var serverUrlConfig: IServerUrlConfig? = null
}