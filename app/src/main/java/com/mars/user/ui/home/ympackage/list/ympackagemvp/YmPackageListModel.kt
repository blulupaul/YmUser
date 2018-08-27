package com.mars.user.ui.home.ympackage.list.ympackagemvp

import cn.nekocode.rxlifecycle.LifecycleEvent
import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.retrofit.RetrofitAPIManager
import com.mars.user.ui.home.ympackage.list.bean.GetPackageListNewGroupResBean
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by gu on 2018/08/07
 * desc: ${desc}
 */
object YmPackageListModel : YmPackageListContract.Model {
    override fun getYmPackageList(mdid: Int, page: Int, limit: Int, rxLifecycle: RxLifecycle): Observable<GetPackageListNewGroupResBean> {
        return RetrofitAPIManager.getProvideClientApi().getPackageList_new_group(mdid, page, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }
}