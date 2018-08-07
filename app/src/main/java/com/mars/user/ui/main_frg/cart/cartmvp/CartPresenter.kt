package com.mars.user.ui.main_frg.cart.cartmvp

import cn.pedant.SweetAlert.SweetAlertDialog
import com.mars.user.ui.main_frg.cart.bean.GetMyGwcListResBean
import com.mars.user.ui.main_frg.cart.bean.GwcJiaJianReqBean

/**
 * Created by gu on 2018/07/25
 * desc: ${desc}
 */
class CartPresenter(var view: CartContract.View) : CartContract.Presenter {

    private val model = CartModel

    override fun onRefresh(userid: Int, page: Int, limit: Int) {
        getMyGwcList(userid, page, limit)
    }

    override fun onLoadMore(userid: Int, page: Int, limit: Int) {
        getMyGwcList(userid, page, limit)
    }

    override fun getMyGwcList(userid: Int, page: Int, limit: Int) {
        view.onRefreshStart()
        model.getMyGwcList(userid, page, limit, view.getRxLifecycle())
                .subscribe({
                    view.onRefreshDismiss()
                    if (it.success) {
                        view.onGetMyGwcListSuceess(it)
                    } else {
                        view.onGetMyGwcListFail(it.msg)
                    }
                }, {
                    view.onRefreshDismiss()
                    view.onServerError(it)
                })
    }

    override fun getMyGwcJiaJian(bean: GwcJiaJianReqBean) {
        model.getMyGwcJiaJian(bean, view.getRxLifecycle())
                .subscribe({
                    if (it.success) {
                        view.onGetMyGwcJiaJianSuccess(it)
                    } else {
                        view.onGetMyGwcJiaJianFail(it.msg)
                    }
                }, {
                    view.onServerError(it)
                })
    }

    override fun getMyGwcJiaJianReqBean(dataList: List<GetMyGwcListResBean.Data>, curPosition: Int, userid: Int, count: Int, type: Int): GwcJiaJianReqBean {
        return model.getMyGwcJiaJianReqBean(dataList, curPosition, userid, count, type)
    }

    override fun deleteGwcForId(gwcid: Int,position:Int) {
        model.deleteGwcForid(gwcid, view.getRxLifecycle())
                .subscribe({
                    if (it.success) {
                        view.onDeleteGwcForIdSuccess(it,position)
                    } else {
                        view.onDeleteGwcForIdFail(it.msg)
                    }
                }, {
                    view.onServerError(it)
                })
    }

    override fun clearGwc(userid: Int, dialog: SweetAlertDialog) {
        model.clearGwc(userid, view.getRxLifecycle())
                .subscribe({
                    if (it.success) {
                        view.onClearGwcSuccess(it, dialog)
                    } else {
                        view.onClearGwcFail(it.msg, dialog)
                    }
                }, {
                    view.onServerError(it)
                })
    }

    override fun addColect(userid: Int, mid: Int, type: Int) {
        model.addCollect(userid, mid, type, view.getRxLifecycle())
                .subscribe({
                    if (it.success) {
                        view.onAddCollectSuccess(it)
                    } else {
                        view.onAddCollectFail(it.msg)
                    }
                }, {
                    view.onServerError(it)
                })

    }
}