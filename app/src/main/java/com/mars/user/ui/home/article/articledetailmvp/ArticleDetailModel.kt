package com.mars.user.ui.home.article.articledetailmvp

import cn.nekocode.rxlifecycle.LifecycleEvent
import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.bean.BaseNomalResBean
import com.mars.user.retrofit.RetrofitAPIManager
import com.mars.user.ui.home.article.bean.GetYmmxInfoResBean
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by gu on 2018/08/31
 * desc: ${desc}
 */
object ArticleDetailModel : ArticleDetailContract.model {
    override fun addScMyMw(userid: Int, mid: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean> {
        return RetrofitAPIManager.getProvideClientApi().addScMyMw(userid, mid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }

    override fun cancelScMyMw(userid: Int, mid: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean> {
        return RetrofitAPIManager.getProvideClientApi().cancelSCMyMw(userid, mid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }

    override fun getYmmxInfo(mxid: Int, userid: Int, rxLifecycle: RxLifecycle): Observable<GetYmmxInfoResBean> {
        return RetrofitAPIManager.getProvideClientApi().getYmmxListInfo(mxid, userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }
}