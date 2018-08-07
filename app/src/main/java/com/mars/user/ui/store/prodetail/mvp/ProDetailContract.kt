package com.mars.user.ui.store.prodetail.mvp

import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.base.mvpview.MvpView
import com.mars.user.bean.BaseNomalResBean
import com.mars.user.ui.store.prodetail.bean.GetProDetailInfoResBean
import io.reactivex.Observable

/**
 * Created by gu on 2018/07/27
 * desc: ${desc}
 */
interface ProDetailContract {
    interface View : MvpView {
        fun onRefreshStart()
        fun onRefreshDismiss()

        fun onGetIsGoumaiSuccess(bean: BaseNomalResBean)
        fun onGetIsGoumaiFail(msg: String)

        fun onGetProDetailInfoSuccess(bean: GetProDetailInfoResBean)
        fun onGetProDetailInfoFail(msg: String)

        fun onAddCollectSuccess(bean: BaseNomalResBean)
        fun onAddCollectFail(msg: String)

        fun onCancelCollectSuccess(bean: BaseNomalResBean)
        fun onCancelCollectFail(msg: String)

        fun onAddGwcSuccess(bean: BaseNomalResBean)
        fun onAddGwcFail(msg: String)
    }

    interface Model {
        fun getIsGoumai(userid: Int, pid: Int, _jinbi: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean>
        fun getProDetailInfo(pid: Int, userid: Int, rxLifecycle: RxLifecycle): Observable<GetProDetailInfoResBean>
        fun addCollect(userid: Int, mid: Int, type: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean>
        fun cancelCollect(userid: Int, spid: Int, type: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean>
        fun addGwc(spid: Int, counts: Int, userid: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean>
    }

    interface Presenter {
        fun onStart(userid: Int, pid: Int)
        fun getIsGoumai(userid: Int, pid: Int, _jinbi: Int)
        fun getProDetailInfo(pid: Int, userid: Int)
        fun addCollect(userid: Int, mid: Int, type: Int)
        fun cancelCollect(userid: Int, spid: Int, type: Int)
        fun addGwc(spid: Int, counts: Int, userid: Int)

        fun showShareAlert()
        fun shareToWeixin()
        fun shareToPengyouquan()
    }
}