package com.mars.user.ui.main_frg.home.homemvp

import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.base.view.BaseView
import com.mars.user.bean.BaseIntResBean
import com.mars.user.bean.BaseNomalResBean
import com.mars.user.ui.main_frg.home.bean.*
import io.reactivex.Observable

/**
 * Created by gu on 2018/07/19
 * desc: ${desc}
 */
interface HomeContract {

    interface View : BaseView {
        fun onRefreshStart()
        fun onRefreshDismiss()

        fun onUpdateNotiCountSuccess(bean: BaseIntResBean)
        fun onUpdateNotiCountFail(msg: String)

//        fun onGetYmmxListSuccess(bean: GetYmmxListResBean)
//        fun onGetYmmxListFail(msg: String)

        fun onGetDownMoneySuccess(bean: GetDownMoneyResBean)
        fun onGetDownMoneyfail(msg: String)

        fun onGetGuanggaoweiSuccess(bean: GetGangGaoweiResBean, type: Int)
        fun onGetGuangGaoweiFail(msg: String)

        //        fun onGetStoreBannerSuccess(bean: GetStoreBannerResBean)
        fun updateBanner(bannerImgList: List<String>)

        fun onGetStoreBannerFail(msg: String)

        fun onUpdateLHBSuccess(bean: BaseNomalResBean)
        fun onUpdateLHBFail(msg: String)

        fun onGett_waddressSuccess(bean: Gett_waddressResBean)
        fun onGett_waddressFail(msg: String)
    }

    interface Model {
//        fun sendGetYmmxList(typeId: Int, page: Int, limit: Int, rxLifecycle: RxLifecycle): Observable<GetYmmxListResBean>
        fun sendGetDownMoney(userid: Int, mdid: Int, rxLifecycle: RxLifecycle): Observable<GetDownMoneyResBean>
        fun sendGetGuanggaowei(userid: Int, rxLifecycle: RxLifecycle): Observable<GetGangGaoweiResBean>
        fun sendGett_waddress(rxLifecycle: RxLifecycle): Observable<Gett_waddressResBean>
        fun updateLHB(userid: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean>
        fun sendGetStoreBanner(userid: Int, rxLifecycle: RxLifecycle): Observable<GetStoreBannerResBean>
        fun sendGetUpdateNotiCount(userid: Int, typeId: Int, rxLifecycle: RxLifecycle): Observable<BaseIntResBean>

        fun getBannerImgList(bean: GetStoreBannerResBean): List<String>

        fun getBannerDetailList(bean: GetStoreBannerResBean): List<String>

        fun homeModuleEnterBeanList(count: Int): List<HomeModelEnterBean>
    }

    interface Presenter  {
        fun onStart()
        fun onRefresh()
        /**
         * type： 0 展示图片，1 完成跳转
         */
        fun sendGetGuanggaowei(type: Int)

        fun onUpdateLHB()
//        fun sendGetYmmxList(typeId: Int, page: Int, limit: Int)
        fun sendGetDownMoney()
        fun sendUpdateNotiCount()
        fun getHomeModuleEnterBeanList(count: Int): List<HomeModelEnterBean>

        fun getBannerDetailList(): ArrayList<String>
    }
}