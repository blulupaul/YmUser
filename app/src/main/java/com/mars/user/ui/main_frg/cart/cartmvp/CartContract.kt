package com.mars.user.ui.main_frg.cart.cartmvp

import cn.nekocode.rxlifecycle.RxLifecycle
import cn.pedant.SweetAlert.SweetAlertDialog
import com.mars.user.base.view.BaseView
import com.mars.user.bean.BaseNomalResBean
import com.mars.user.ui.main_frg.cart.bean.GetMyGwcListResBean
import com.mars.user.ui.main_frg.cart.bean.GwcJiaJianReqBean
import com.mars.user.ui.main_frg.cart.bean.GwcJiaJianResBean
import io.reactivex.Observable

/**
 * Created by gu on 2018/07/24
 * desc: ${desc}
 */
interface CartContract {
    interface View :BaseView{
        fun onRefreshStart()
        fun onRefreshDismiss()

        fun onGetMyGwcListSuceess(bean: GetMyGwcListResBean)
        fun onGetMyGwcListFail(msg: String)

        fun onGetMyGwcJiaJianSuccess(bean: GwcJiaJianResBean)
        fun onGetMyGwcJiaJianFail(msg: String)

        fun onDeleteGwcForIdSuccess(bean: BaseNomalResBean,position: Int)
        fun onDeleteGwcForIdFail(msg: String)

        fun onClearGwcSuccess(bean: BaseNomalResBean, dialog: SweetAlertDialog)
        fun onClearGwcFail(msg: String, dialog: SweetAlertDialog)

        fun onAddCollectSuccess(bean: BaseNomalResBean)
        fun onAddCollectFail(msg: String)

    }

    interface Model {
        fun getMyGwcList(userid: Int, page: Int, limit: Int, rxLifecycle: RxLifecycle): Observable<GetMyGwcListResBean>
        fun getMyGwcJiaJian(bean: GwcJiaJianReqBean, rxLifecycle: RxLifecycle): Observable<GwcJiaJianResBean>
        fun getMyGwcJiaJianReqBean(dataList: List<GetMyGwcListResBean.Data>, curPosition: Int, userid: Int, count: Int, type: Int): GwcJiaJianReqBean

        fun deleteGwcForid(gwcid: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean>
        fun clearGwc(userid: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean>
        fun addCollect(userid: Int, mid: Int, type: Int, rxLifecycle: RxLifecycle): Observable<BaseNomalResBean>
    }

    interface Presenter {
        fun onRefresh(userid: Int, page: Int, limit: Int)
        fun onLoadMore(userid: Int, page: Int, limit: Int)
        fun getMyGwcList(userid: Int, page: Int, limit: Int)
        fun getMyGwcJiaJian(bean: GwcJiaJianReqBean)
        fun getMyGwcJiaJianReqBean(dataList: List<GetMyGwcListResBean.Data>, curPosition: Int, userid: Int, count: Int, type: Int): GwcJiaJianReqBean

        fun deleteGwcForId(gwcid: Int, position: Int)
        fun clearGwc(userid: Int, dialog: SweetAlertDialog)
        fun addColect(userid: Int, mid: Int, type: Int)
    }
}