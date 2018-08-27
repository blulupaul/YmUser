package com.mars.user.ui.main_frg.mine.minemvp

import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.base.view.BaseView
import com.mars.user.bean.BaseIntResBean
import com.mars.user.ui.login.login.bean.UserLoginBean
import com.mars.user.ui.main_frg.home.bean.HomeModelEnterBean
import io.reactivex.Observable

/**
 * Created by gu on 2018/07/23
 * desc: ${desc}
 */
interface MineContract {
    interface View :BaseView{
        fun onRefreshStart()
        fun onRefreshDismiss()
        fun onGetMyInfoSuccess(bean: UserLoginBean)
        fun onGetMyInfoFail(msg: String)
        fun onUpdateNotiCountSuccess(bean: BaseIntResBean)
        fun onUpdateNotiCountFail(msg: String)
    }

    interface Model {
        fun sendGetMyInfo(userid: Int, rxLifecycle: RxLifecycle):Observable<UserLoginBean>
        fun sendUpdateNotiCount(userid: Int, type: Int, rxLifecycle: RxLifecycle):Observable<BaseIntResBean>
        fun getMineModuleEnterDataList(): List<HomeModelEnterBean>
    }

    interface Presenter {
        fun getUserInfo()
        fun updateNotiCount()
        fun onRefresh()
        fun getMineModuleEnterBeanList(): List<HomeModelEnterBean>

    }
}