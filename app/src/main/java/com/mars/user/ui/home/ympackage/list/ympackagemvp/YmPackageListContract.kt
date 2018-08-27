package com.mars.user.ui.home.ympackage.list.ympackagemvp

import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.base.view.BaseView
import com.mars.user.ui.home.ympackage.list.bean.GetPackageListNewGroupResBean
import io.reactivex.Observable

/**
 * Created by gu on 2018/08/07
 * desc: ${desc}
 */
interface YmPackageListContract {
    interface View : BaseView {
        fun onGetYmPackageListSuccess(bean: GetPackageListNewGroupResBean)
        fun onGetYmPackageListFail(msg: String)
    }

    interface Model {
        fun getYmPackageList(mdid: Int, page: Int, limit: Int, rxLifecycle: RxLifecycle): Observable<GetPackageListNewGroupResBean>
    }

    interface Presenter {
        fun getYmPackageList(mdid: Int, page: Int, limit: Int)
    }
}