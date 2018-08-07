package com.mars.user.ui.login.login.loginmvp

import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.ui.login.login.bean.UserLoginBean
import io.reactivex.Observable


/**
 * Created by gu on 2018/07/18
 * desc: ${desc}
 */
interface LoginContract {

    interface View {
        fun onLoginSuccess(bean: UserLoginBean)
        fun onLoginIdentityError(bean: UserLoginBean)
        fun onLoginFail(msg:String)
        fun onServerResError(t: Throwable)
        fun onLoaddingShow()
        fun onLoaddingDismiss()
        fun saveToSp(bean: UserLoginBean)
        fun getRxLiftCycle(): RxLifecycle
    }

    interface Model {
        fun getUserLoginInfo(phone: String, pwd: String, rxLifecycle: RxLifecycle): Observable<UserLoginBean>
    }

    interface Presenter {
        fun login(phone: String, pwd: String)
    }

}