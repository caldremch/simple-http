package com.caldremch.request

import androidx.annotation.RestrictTo

/**
 * @author Caldremch
 * @date 2020-09-08
 * @email caldremch@163.com
 * @describe
 */
abstract class Abs<out T: Abs<T>>{

    fun a():T{
        return this as T
    }

    fun b():T{
        return this as T
    }
}

