package com.mars.user.ui.main_frg.home.homemvp

import cn.nekocode.rxlifecycle.LifecycleEvent
import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.R
import com.mars.user.bean.BaseIntResBean
import com.mars.user.bean.BaseNomalResBean
import com.mars.user.retrofit.RetrofitAPIManager
import com.mars.user.ui.main_frg.home.bean.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by gu on 2018/07/19
 * desc: ${desc}
 */
object HomeModel : HomeContract.Model {
//    override fun sendGetYmmxList(typeId: Int, page: Int, limit: Int, rxLifecycle: RxLifecycle): Observable<GetYmmxListResBean> {
//        return RetrofitAPIManager.getProvideClientApi().getYmmxList(typeId, page, limit)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
//    }

    override fun sendGetDownMoney(userid: Int, mdid: Int, rxLifecycle: RxLifecycle): Observable<GetDownMoneyResBean> {
        return RetrofitAPIManager.getProvideClientApi().getDownMoney(userid, mdid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))

    }

    override fun sendGetGuanggaowei(userid: Int, rxLifecycle: RxLifecycle): Observable<GetGangGaoweiResBean> {
        return RetrofitAPIManager.getProvideClientApi().getGuanggaowei(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }

    override fun updateLHB(userid: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean> {
        return RetrofitAPIManager.getProvideClientApi().updateLHB(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }

    override fun sendGett_waddress(rxLifecycle: RxLifecycle): Observable<Gett_waddressResBean> {
        return RetrofitAPIManager.getProvideClientApi().gett_waddress()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }

    override fun sendGetStoreBanner(userid: Int, rxLifecycle: RxLifecycle): Observable<GetStoreBannerResBean> {
        return RetrofitAPIManager.getProvideClientApi().getStoreBanner(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }

    override fun sendGetUpdateNotiCount(userid: Int, typeId: Int, rxLifecycle: RxLifecycle): Observable<BaseIntResBean> {
        return RetrofitAPIManager.getProvideClientApi().getNtypeOneCount(userid, typeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxLifecycle.disposeObservableWhen(LifecycleEvent.DESTROY))
    }

    override fun getBannerImgList(bean: GetStoreBannerResBean): List<String> {
        var bannerImgList = ArrayList<String>()
        for (item in bean.data) {
            bannerImgList.add(item.bimage)
        }

        return bannerImgList
    }

    override fun getBannerDetailList(bean: GetStoreBannerResBean): List<String> {
        var bannerDetailsList = ArrayList<String>()
        for (item in bean.data) {
            bannerDetailsList.add(item.details)
        }
        return bannerDetailsList
    }

    override fun homeModuleEnterBeanList(count: Int): List<HomeModelEnterBean> {
        //recycleview 数据源
        val mTextList = listOf("云猫项目", "拼团", "卡项", "云猫养成", "倒计钱", "美圈", "通知列表", "皮肤测试")
        val mIconList = listOf(R.mipmap.ym_xiangmu_new, R.mipmap.ym_pintuan_new,
                R.mipmap.ym_kaxiang_new, R.mipmap.ym_feed_new, R.mipmap.ym_down_money,
                R.mipmap.ym_meiquan_new, R.mipmap.ym_gonggao_new, R.mipmap.ym_skin_test_new)
        val list = ArrayList<HomeModelEnterBean>()
        for (i in mTextList.indices) {
            val bean: HomeModelEnterBean
            if (i == 6) {
                bean = HomeModelEnterBean(mTextList[i], mIconList[i], count)
            } else {
                bean = HomeModelEnterBean(mTextList[i], mIconList[i], 0)
            }
            list.add(bean)
        }
        return list
    }

}