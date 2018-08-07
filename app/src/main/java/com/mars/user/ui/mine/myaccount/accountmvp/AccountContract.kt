package com.mars.user.ui.mine.myaccount.accountmvp

import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.ui.mine.myaccount.bean.GetMyJinBiResBean
import io.reactivex.Observable

/**
 * Created by gu on 2018/07/27
 * desc: ${desc}
 */
interface AccountContract {
    interface View {
        fun onGetMyJinBiSuccess(bean: GetMyJinBiResBean)
        fun onGetMyJinbiFail(msg: String)

        fun onServerError(t: Throwable)
        fun getRxLifecycle(): RxLifecycle
    }

    interface Model {
        fun getMyJinbi(userid: Int, type: Int, page: Int, limit: Int, rxLifecycle: RxLifecycle): Observable<GetMyJinBiResBean>
    }

    interface Presenter {
        fun getMyJinbi(userid: Int, type: Int, page: Int, limit: Int)
    }
}