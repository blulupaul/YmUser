package com.mars.user.ui.main.mvp

import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.ui.main.bean.GetUpdateAppResBean
import io.reactivex.Observable

/**
 * Created by gu on 2018/07/18
 * desc: ${desc}
 */
interface MainContract {

    interface Model {
        fun getUpdateApp(type: Int, rxLifecycle: RxLifecycle): Observable<GetUpdateAppResBean>
    }

    interface View {
        fun onUpdateSuccess(bean: GetUpdateAppResBean)
        fun onUpdateFail(msg: String)
        fun onServerResError(t: Throwable)
        fun getContentId():Int
        fun getRxLifeCycle(): RxLifecycle
    }

    interface Presenter {
        fun updateAppInfo(type: Int)
        fun changeFragment(itemId: Int)
    }
}