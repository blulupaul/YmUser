package com.mars.user.ui.home.article.articledetailmvp

import com.mars.user.interfac.OnSheetItemClickListener

/**
 * Created by gu on 2018/08/31
 * desc: ${desc}
 */
class ArticleDetailPresenter(private val view: ArticleDetailContract.View) : ArticleDetailContract.presenter {

    private val modelArticle = ArticleDetailModel

    override fun sendGetYmmxInfo(mxid: Int, userid: Int) {
        modelArticle.getYmmxInfo(mxid, userid, view.getRxLifecycle())
                .subscribe({
                    if (it.success) {
                        view.onGetYmmxInfoSuccess(it)
                    } else {
                        view.onGetYmmxInfoFail(it.msg)
                    }
                }, {
                    view.onServerError(it)
                })
    }

    override fun addCollect(userid: Int, mid: Int) {
        modelArticle.addScMyMw(userid, mid, view.getRxLifecycle())
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

    override fun cancelCollect(userid: Int, mid: Int) {
        modelArticle.cancelScMyMw(userid, mid, view.getRxLifecycle())
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

    override fun showShareAlert() {
        com.mars.user.utils.showShareAlert(view.getRContext(), object : OnSheetItemClickListener {
            override fun shareToweixin() {
                view.onShareToWeiXin()
            }

            override fun shareTopengyouquan() {
               view.onShareToPengyouquan()
            }

        })
    }

}