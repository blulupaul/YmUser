package com.mars.user.ui.main_frg.store.storemvp

import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.base.view.BaseView
import com.mars.user.ui.main_frg.store.bean.GetProductListByPTypeNewResBean
import com.mars.user.ui.main_frg.store.bean.GetPtypeListResBean
import io.reactivex.Observable

/**
 * Created by gu on 2018/07/26
 * desc: ${desc}
 */
interface StoreContract {
    interface View :BaseView{
        fun onRefreshStart()
        fun onRefreshFinish()
        fun onLoadMoreFinish()

        fun onGetPtypeListSuccess(bean: GetPtypeListResBean)
        fun onGetPtypeListFail(msg: String)

        fun onGetProListByPTypeNewSuccess(bean: GetProductListByPTypeNewResBean)
        fun onGetProListByPTypeNewFail(msg: String)
    }

    interface Model {
        fun getPTypeList(rxLifecycle: RxLifecycle): Observable<GetPtypeListResBean>
        fun getProListByPTypeNew(page: Int, limit: Int, ptype: Int, mdid: Int, userid: Int, rxLifecycle: RxLifecycle):Observable<GetProductListByPTypeNewResBean>
    }

    interface Presenter{
        fun getPTypeList()
        fun getProListByPTypeNew(page: Int, limit: Int, ptype: Int, mdid: Int, userid: Int)
    }
}