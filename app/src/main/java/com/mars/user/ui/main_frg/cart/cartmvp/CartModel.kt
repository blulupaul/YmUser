package com.mars.user.ui.main_frg.cart.cartmvp

import cn.nekocode.rxlifecycle.LifecycleEvent
import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.bean.BaseNomalResBean
import com.mars.user.retrofit.RetrofitAPIManager
import com.mars.user.ui.main_frg.cart.bean.GetMyGwcListResBean
import com.mars.user.ui.main_frg.cart.bean.GwcJiaJianReqBean
import com.mars.user.ui.main_frg.cart.bean.GwcJiaJianResBean
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by gu on 2018/07/25
 * desc: ${desc}
 */
object CartModel : CartContract.Model {
    override fun getMyGwcList(userid: Int, page: Int, limit: Int, rxLifecycle: RxLifecycle): Observable<GetMyGwcListResBean> {
        return RetrofitAPIManager.getProvideClientApi()
                .getMyGwcList(userid, page, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }

    override fun getMyGwcJiaJian(bean: GwcJiaJianReqBean, rxLifecycle: RxLifecycle): Observable<GwcJiaJianResBean> {
        return RetrofitAPIManager.getProvideClientApi()
                .gwcJiajian(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }

    override fun getMyGwcJiaJianReqBean(dataList: List<GetMyGwcListResBean.Data>, currentPosition: Int, userid: Int, count: Int, type: Int): GwcJiaJianReqBean {
        val gwcIdList = ArrayList<String>()
        for (item in dataList) {
            if (item.isCheck) {
                gwcIdList.add(item.gwcid.toString())
            }
        }
        return GwcJiaJianReqBean(gwcIdList, dataList[currentPosition].gwcid, userid, type, count)
    }

    override fun deleteGwcForid(gwcid: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean> {
        return RetrofitAPIManager.getProvideClientApi()
                .deletegwcforid(gwcid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }

    override fun clearGwc(userid: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean> {
        return RetrofitAPIManager.getProvideClientApi()
                .deleteAllgwcForUserid(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }

    override fun addCollect(userid: Int, mid: Int, type: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean> {
        return RetrofitAPIManager.getProvideClientApi()
                .addCollect(userid, mid, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))

    }
}