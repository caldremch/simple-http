package com.caldremch.observer

import androidx.annotation.RestrictTo

/**
 *
 * @author Caldremch
 *
 * @date 2020-01-09 15:51
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
@RestrictTo(RestrictTo.Scope.LIBRARY)
data class Option(
    var isShowDialog: Boolean,
    var isShowToast: Boolean,
    var dialogTips: String?
)