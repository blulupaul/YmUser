package com.mars.user.ui.home.article.articlelistmvp

import cn.nekocode.rxlifecycle.LifecycleEvent
import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.retrofit.RetrofitAPIManager
import com.mars.user.ui.main_frg.home.bean.GetYmmxListResBean
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by gu on 2018/08/29
 * desc: ${desc}
 */
object ArticleListModel :ArticleListContract.Model{
    override fun sendGetYmmxList(typeId: Int, page: Int, limit: Int, rxLifecycle: RxLifecycle): Observable<GetYmmxListResBean> {
        return RetrofitAPIManager.getProvideClientApi().getYmmxList(typeId, page, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }
}