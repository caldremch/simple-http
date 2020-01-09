package com.caldremch

import android.content.Context
import com.caldremch.parse.HttpParams
import com.caldremch.parse.HttpPath
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 *
 * @author Caldremch
 *
 * @date 2020-01-09 16:09
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
open class RequestBuilderOption {

    protected var httpParams: HttpParams? = null
    protected var httpPath: HttpPath? = null
    protected var url: String? = null
    //0:get 1:post 2:file
    protected var type = Method.GET
    protected var context: Context? = null
    //是否显示弹窗
    protected var isShowDialog = false
    protected var dialogTips:String? = ""
    //是否显示toast
    protected var isShowToast = true
    /**
     * FormUrlEncoded 形式
     */
    protected var formUrlEncoded = false
    /**
     * 容我孤陋寡闻 post链接 拼接参数
     */
    protected var postQuery = false

    protected var requestBody: RequestBody? = null

    private val parts: List<MultipartBody.Part>? = null
}