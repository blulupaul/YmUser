package com.mars.user.base.mvpview

import cn.nekocode.rxlifecycle.RxLifecycle

/**
 * Created by gu on 2018/07/31
 * desc: ${desc}
 */
interface MvpView {
    fun onServerError(t: Throwable)
    fun getRxLifecycle(): RxLifecycle
}