package com.mars.user.base.view

import cn.nekocode.rxlifecycle.RxLifecycle

/**
 * Created by gu on 2018/07/31
 * desc: ${desc}
 */
interface BaseView {
    fun onServerError(t: Throwable)
    fun getRxLifecycle(): RxLifecycle
}