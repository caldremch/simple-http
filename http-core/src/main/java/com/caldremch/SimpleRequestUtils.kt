package com.caldremch

import com.caldremch.convert.IConvert
import com.caldremch.observer.IObserverHandler

/**
 *
 * @author Caldremch
 *
 * @date 2020-01-09 16:07
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
object SimpleRequestUtils {

    private var convert: IConvert? = null

    private var observerHandler: IObserverHandler? = null


    fun register(convert: IConvert, handler: IObserverHandler) {
        SimpleRequestUtils.convert = convert
        SimpleRequestUtils.observerHandler = handler
    }

    fun getConvert():IConvert?{
        return convert
    }

    fun getObserverHandler():IObserverHandler?{
        return observerHandler
    }
}