package com.mars.user.ui.login.login.loginmvp

import cn.nekocode.rxlifecycle.LifecycleEvent
import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.retrofit.RetrofitAPIManager
import com.mars.user.ui.login.login.bean.UserLoginBean
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by gu on 2018/07/18
 * desc: ${desc}
 */
object LoginModel : LoginContract.Model {

    override fun getUserLoginInfo(phone: String, pwd: String, rxLifecycle: RxLifecycle): Observable<UserLoginBean> {
        return RetrofitAPIManager.getProvideClientApi().getLogin(phone, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }
}