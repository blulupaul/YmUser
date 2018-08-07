package com.mars.user.ui.login.login.loginmvp

/**
 * Created by gu on 2018/07/18
 * desc: ${desc}
 */
class LoginPresenter(var view: LoginContract.View) : LoginContract.Presenter {

    private var model = LoginModel

    override fun login(phone: String, pwd: String) {
        view.onLoaddingShow()
        model.getUserLoginInfo(phone, pwd, view.getRxLiftCycle())
                .subscribe({
                    view.onLoaddingDismiss()
                    if (it.success) {
                        if (it.data[0].usertype == 1) {
                            view.saveToSp(it)
                            view.onLoginSuccess(it)
                        } else {
                            view.onLoginIdentityError(it)
                        }
                    } else {
                        view.onLoginFail(it.msg)
                    }
                }, {
                    view.onLoaddingDismiss()
                    view.onServerResError(it)
                })
    }
}