package com.mars.user.ui.login.login.loginmvp

/**
 * Created by gu on 2018/07/18
 * desc: ${desc}
 */
class LoginPresenter(private val mView:LoginContract.View) : LoginContract.Presenter {

    private var model = LoginModel

    override fun login(phone: String, pwd: String) {
        mView.onLoaddingShow()
        model.getUserLoginInfo(phone, pwd, mView.getRxLifecycle())
                .subscribe({
                    mView.onLoaddingDismiss()
                    if (it.success) {
                        if (it.data[0].usertype == 1) {
                            mView.saveToSp(it)
                            mView.onLoginSuccess(it)
                        } else {
                            mView.onLoginIdentityError(it)
                        }
                    } else {
                        mView.onLoginFail(it.msg)
                    }
                }, {
                    mView.onLoaddingDismiss()
                    mView.onServerError(it)
                })
    }
}