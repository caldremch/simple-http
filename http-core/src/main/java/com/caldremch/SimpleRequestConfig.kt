package com.caldremch

import androidx.annotation.RestrictTo
import com.caldremch.custom.IConvert
import com.caldremch.custom.IObserverHandler
import com.caldremch.custom.IServerUrlConfig

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