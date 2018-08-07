package com.mars.user.ui.store.prodetail.mvp

import android.content.Context
import com.mars.user.interfac.OnSheetItemClickListener
import com.mars.user.utils.T
import com.mars.user.utils.showShareAlert

/**
 * Created by gu on 2018/07/31
 * desc: ${desc}
 */
class ProDetailPresenter(val context: Context, val view: ProDetailContract.View) : ProDetailContract.Presenter {

    private val model = ProDetailModel
    var isFirstLoad = true

    override fun onStart(userid: Int, pid: Int) {
        if (!isFirstLoad) {
            view.onRefreshStart()
        }
        getProDetailInfo(pid, userid)
    }

    override fun getProDetailInfo(pid: Int, userid: Int) {
        model.getProDetailInfo(pid, userid, view.getRxLifecycle())
                .subscribe({
                    view.onRefreshDismiss()
                    isFirstLoad = false
                    if (it.success) {
                        view.onGetProDetailInfoSuccess(it)
                    } else {
                        view.onGetProDetailInfoFail(it.msg)
                    }
                }, {
                    view.onRefreshDismiss()
                    view.onServerError(it)
                })
    }

    override fun getIsGoumai(userid: Int, pid: Int, _jinbi: Int) {
        model.getIsGoumai(userid, pid, _jinbi, view.getRxLifecycle())
                .subscribe({
                    if (it.success) {
                        view.onGetIsGoumaiSuccess(it)
                    } else {
                        view.onGetIsGoumaiFail(it.msg)
                    }
                }, {
                    view.onServerError(it)
                })
    }

    override fun showShareAlert() {
        showShareAlert(context, object : OnSheetItemClickListener {
            override fun shareToweixin() {
                shareToWeixin()
            }

            override fun shareTopengyouquan() {
                shareToPengyouquan()
            }

        })
    }

    override fun shareToWeixin() {
        T.showToastCenter(context, "微信")
    }

    override fun shareToPengyouquan() {
        T.showToastCenter(context, "朋友圈")
    }

    override fun addCollect(userid: Int, mid: Int, type: Int) {
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

    override fun cancelCollect(userid: Int, spid: Int, type: Int) {
        model.cancelCollect(userid, spid, type, view.getRxLifecycle())
                .subscribe({
                    if (it.success) {
                        view.onCancelCollectSuccess(it)
                    } else {
                        view.onCancelCollectFail(it.msg)
                    }
                }, {
                    view.onServerError(it)
                })
    }

    override fun addGwc(spid: Int, counts: Int, userid: Int) {
        model.addGwc(spid, counts, userid, view.getRxLifecycle())
                .subscribe({
                    if (it.success) {
                        view.onAddGwcSuccess(it)
                    } else {
                        view.onAddGwcFail(it.msg)
                    }
                }, {
                    view.onServerError(it)
                })
    }
}