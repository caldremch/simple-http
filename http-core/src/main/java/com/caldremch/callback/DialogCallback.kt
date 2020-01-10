package com.caldremch.callback

import android.content.Context
import androidx.annotation.Nullable

/**
 *
 * @author Caldremch
 *
 * @date 2020-01-09 15:19
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
abstract class DialogCallback<T>(var context: Context, var tips: String? = "") : HttpCallback<T>() {

}