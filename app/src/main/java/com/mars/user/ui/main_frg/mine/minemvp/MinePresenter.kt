package com.mars.user.ui.main_frg.mine.minemvp

import com.mars.user.constant.IS_LOGIN
import com.mars.user.constant.USER_ID
import com.mars.user.ui.main_frg.home.bean.HomeModelEnterBean
import com.mars.user.utils.SpUtil

/**
 * Created by gu on 2018/07/23
 * desc: ${desc}
 */
class MinePresenter(var view: MineContract.View) : MineContract.Presenter {
    private val mineModel = MineModel

    override fun getUserInfo() {
        if (SpUtil.getInstance().getBoolean(IS_LOGIN))
            view.onRefreshStart()
        mineModel.sendGetMyInfo(SpUtil.getInstance().getInt(USER_ID), view.getRxLifecycle())
                .subscribe({
                    view.onRefreshDismiss()
                    if (it.success) {
                        view.onGetMyInfoSuccess(it)
                    } else {
                        view.onGetMyInfoFail(it.msg)
                    }
                }, {
                    view.onRefreshDismiss()
                    view.onServerError(it)
                })
    }

    override fun updateNotiCount() {
        if (SpUtil.getInstance().getBoolean(IS_LOGIN)) {
            mineModel.sendUpdateNotiCount(SpUtil.getInstance().getInt(USER_ID), 0, view.getRxLifecycle())
                    .subscribe({
                        view.onRefreshDismiss()
                        if (it.success) {
                            view.onUpdateNotiCountSuccess(it)
                        } else {
                            view.onUpdateNotiCountFail(it.msg)
                        }
                    }, {
                        view.onRefreshDismiss()
                        view.onServerError(it)
                    })
        } else {
            view.onRefreshDismiss()
        }
    }

    override fun onRefresh() {
        getUserInfo()
        updateNotiCount()
    }

    override fun getMineModuleEnterBeanList(): List<HomeModelEnterBean> {
        return mineModel.getMineModuleEnterDataList()
    }


}