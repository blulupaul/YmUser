package com.mars.user.ui.store.prodetail.mvp

import cn.nekocode.rxlifecycle.LifecycleEvent
import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.bean.BaseNomalResBean
import com.mars.user.retrofit.RetrofitAPIManager
import com.mars.user.ui.store.prodetail.bean.GetProDetailInfoResBean
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by gu on 2018/07/31
 * desc: ${desc}
 */
object ProDetailModel : ProDetailContract.Model {
    override fun getIsGoumai(userid: Int, pid: Int, _jinbi: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean> {
        return RetrofitAPIManager.getProvideClientApi().getIsGouMai(userid, pid, _jinbi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }

    override fun getProDetailInfo(pid: Int, userid: Int, rxLifecycle: RxLifecycle): Observable<GetProDetailInfoResBean> {
        return RetrofitAPIManager.getProvideClientApi().getProDetailInfo(pid, userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }

    override fun addCollect(userid: Int, mid: Int, type: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean> {
        return RetrofitAPIManager.getProvideClientApi().addCollect(userid, mid, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }

    override fun cancelCollect(userid: Int, spid: Int, type: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean> {
        return RetrofitAPIManager.getProvideClientApi().cancelCollect(userid, spid, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }

    override fun addGwc(spid: Int, counts: Int, userid: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean> {
        return RetrofitAPIManager.getProvideClientApi().addgwc(spid, counts, userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }
}