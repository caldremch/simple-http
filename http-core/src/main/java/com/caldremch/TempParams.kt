package com.caldremch

import okhttp3.RequestBody

/**
 *
 * @author Caldremch
 *
 * @date 2020-09-07 14:01
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class TempParams {
    /**
     * FormUrlEncoded 形式
     */
     var formUrlEncoded = false

    /**
     * 容我孤陋寡闻 post链接 拼接参数
     */
     var postQuery = false

     var requestBody: RequestBody? = null

}