package com.mars.user.ui.mine.myaccount.accountmvp

import cn.nekocode.rxlifecycle.LifecycleEvent
import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.retrofit.RetrofitAPIManager
import com.mars.user.ui.mine.myaccount.bean.GetMyJinBiResBean
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by gu on 2018/07/27
 * desc: ${desc}
 */
object AccountModel : AccountContract.Model {
    override fun getMyJinbi(userid: Int, type: Int, page: Int, limit: Int, rxLifecycle: RxLifecycle): Observable<GetMyJinBiResBean> {
        return RetrofitAPIManager.getProvideClientApi().getMyjinbi(userid, type, page, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }
}