package com.caldremch

import android.content.Context
import androidx.lifecycle.Lifecycle
import com.caldremch.parse.HttpParams
import com.caldremch.parse.HttpPath

/**
 *
 * @author Caldremch
 *
 * @date 2020-09-06
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class BaseReqBuilder {
    protected var httpParams: HttpParams = HttpParams()
    private var httpPath: HttpPath = HttpPath()
    private var context: Context? = null
    private var lifeCycle: Lifecycle? = null
    //是否显示弹窗
    private var isShowDialog = false
    private var dialogTips: String? = ""
    //是否显示toast
    private var isShowToast = true

}