package com.mars.user.ui.main.mvp

import cn.nekocode.rxlifecycle.LifecycleEvent
import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.retrofit.RetrofitAPIManager
import com.mars.user.ui.main.bean.GetUpdateAppResBean
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by gu on 2018/07/18
 * desc: ${desc}
 */
object MainModel : MainContract.Model {
    override fun getUpdateApp(type: Int, rxLifecycle: RxLifecycle): Observable<GetUpdateAppResBean> {
        return RetrofitAPIManager.getProvideClientApi().getUpdateApp(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }

}