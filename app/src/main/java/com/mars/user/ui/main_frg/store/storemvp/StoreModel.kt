package com.mars.user.ui.main_frg.store.storemvp

import cn.nekocode.rxlifecycle.LifecycleEvent
import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.retrofit.RetrofitAPIManager
import com.mars.user.ui.main_frg.store.bean.GetProductListByPTypeNewResBean
import com.mars.user.ui.main_frg.store.bean.GetPtypeListResBean
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by gu on 2018/07/26
 * desc: ${desc}
 */
object StoreModel :StoreContract.Model{
    override fun getPTypeList(rxLifecycle: RxLifecycle): Observable<GetPtypeListResBean> {
        return RetrofitAPIManager.getProvideClientApi().getptyptList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }

    override fun getProListByPTypeNew(page: Int, limit: Int, ptype: Int, mdid: Int, userid: Int, rxLifecycle: RxLifecycle):Observable<GetProductListByPTypeNewResBean> {
        return RetrofitAPIManager.getProvideClientApi().getProductListByPTypeNew(page, limit, ptype, mdid, userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))

    }

}