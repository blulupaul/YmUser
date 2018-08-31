package com.mars.user.ui.home.article.articledetailmvp

import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.base.view.BaseView
import com.mars.user.bean.BaseNomalResBean
import com.mars.user.ui.home.article.bean.GetYmmxInfoResBean
import io.reactivex.Observable

/**
 * Created by gu on 2018/08/31
 * desc: ${desc}
 */
interface ArticleDetailContract {
    interface View : BaseView {
        fun onGetYmmxInfoSuccess(bean: GetYmmxInfoResBean)
        fun onGetYmmxInfoFail(msg: String)

        fun onAddCollectSuccess(bean: BaseNomalResBean)
        fun onAddCollectFail(msg: String)

        fun onCancelCollectSuccess(bean: BaseNomalResBean)
        fun onCancelCollectFail(msg: String)

        fun onShareToWeiXin()
        fun onShareToPengyouquan()
    }

    interface model {
        fun getYmmxInfo(mxid: Int, userid: Int, rxLifecycle: RxLifecycle): Observable<GetYmmxInfoResBean>
        fun addScMyMw(userid: Int, mid: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean>
        fun cancelScMyMw(userid: Int, mid: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean>
    }

    interface presenter {
        fun sendGetYmmxInfo(mxid: Int, userid: Int)
        fun addCollect(userid: Int, mid: Int)
        fun cancelCollect(userid: Int, mid: Int)
        fun showShareAlert()
    }
}