package com.mars.user.ui.main_frg.home.homemvp

import com.mars.user.constant.MD_ID
import com.mars.user.constant.USER_ID
import com.mars.user.ui.main_frg.home.bean.HomeModelEnterBean
import com.mars.user.utils.SpUtil

/**
 * Created by gu on 2018/07/19
 * desc: ${desc}
 */
class HomePresenter(private val view: HomeContract.View) : HomeContract.Presenter {

    private val homeModel = HomeModel
    private val bannerDetailList = ArrayList<String>()

    override fun onStart() {
        view.onRefreshStart()
        homeModel.sendGetStoreBanner(SpUtil.getInstance().getInt(USER_ID, 3197), view.getRxLifecycle())
                .subscribe({
                    view.onRefreshDismiss()
                    bannerDetailList.clear()
                    if (it.success) {
                        view.updateBanner(homeModel.getBannerImgList(it))
                        bannerDetailList.addAll(homeModel.getBannerDetailList(it))
                    } else {
                        view.onGetStoreBannerFail(it.msg)
                    }
                }, {
                    view.onRefreshDismiss()
                    view.onServerError(it)
                })

        sendGetGuanggaowei(0)
    }

    override fun sendGetGuanggaowei(type: Int) {
        homeModel.sendGetGuanggaowei(SpUtil.getInstance().getInt(USER_ID, 3197), view.getRxLifecycle())
                .subscribe({
                    view.onRefreshDismiss()
                    if (it.success) {
                        view.onGetGuanggaoweiSuccess(it, type)
                    } else {
                        view.onGetGuangGaoweiFail(it.msg)
                    }
                }, {
                    view.onRefreshDismiss()
                    view.onServerError(it)
                })
    }

    override fun onUpdateLHB() {
        homeModel.updateLHB(SpUtil.getInstance().getInt(USER_ID, 3197), view.getRxLifecycle())
                .subscribe({
                    if (it.success) {
                        view.onUpdateLHBSuccess(it)
                    } else {
                        view.onUpdateLHBFail(it?.msg!!)
                    }
                }, {
                    view.onServerError(it)
                })
    }

    override fun sendGetDownMoney() {
        homeModel.sendGetDownMoney(SpUtil.getInstance().getInt(USER_ID, 3197), SpUtil.getInstance().getInt(MD_ID, 0), view.getRxLifecycle())
                .subscribe({
                    if (it.success) {
                        view.onGetDownMoneySuccess(it)
                    } else {
                        view.onGetDownMoneyfail(it.msg)
                    }
                }, {
                    view.onServerError(it)
                })
    }

    override fun sendGetYmmxList(typeId: Int, page: Int, limit: Int) {
        homeModel.sendGetYmmxList(typeId, page, limit, view.getRxLifecycle())
                .subscribe({
                    if (it.success) {
                        view.onGetYmmxListSuccess(it)
                    } else {
                        view.onGetYmmxListFail(it.msg)
                    }
                }, {
                    view.onServerError(it)
                })
    }

    override fun sendUpdateNotiCount() {
        homeModel.sendGetUpdateNotiCount(SpUtil.getInstance().getInt(USER_ID, 3197), 0, view.getRxLifecycle())
                .subscribe({
                    if (it.success) {
                        view.onUpdateNotiCountSuccess(it)
                    } else {
                        view.onUpdateNotiCountFail(it.msg)
                    }
                }, {
                    view.onServerError(it)
                })
    }

    override fun onRefresh() {
        onStart()
    }

    override fun getHomeModuleEnterBeanList(count: Int): List<HomeModelEnterBean> {
        return homeModel.homeModuleEnterBeanList(count)
    }

    override fun getBannerDetailList(): ArrayList<String> {
        return bannerDetailList
    }
}