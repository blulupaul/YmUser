package com.mars.user.ui.home.article.mvp

import com.mars.user.ui.main_frg.home.bean.GetYmmxListResBean
import com.mars.user.utils.RObserver

/**
 * Created by gu on 2018/08/29
 * desc: ${desc}
 */
class ArticleListPresenter(private val view: ArticleListContract.View, private var typeId: Int, private val state: Int) : ArticleListContract.Presenter {
    private val model = ArticleListModel

    var page = 1
    private val limit = 20

    override fun onStart() {
        sendGetYmmxList(typeId, page, limit)
    }

    override fun onRefresh() {
        page = 1
        onStart()
    }

    override fun onLoadMore() {
        page += 1
        onStart()
    }

    override fun sendGetYmmxList(typeId: Int, page: Int, limit: Int) {
        // 首页切换不需要显示Loadding
        if (state == 0) {
            model.sendGetYmmxList(typeId, page, limit, view.getRxLifecycle())
                    .subscribe({
                        view.onLoadMoreFinish()
                        view.onRefreshFinish()
                        if (it.success) {
                            view.onGetYmmxListSuccess(it)
                        } else {
                            view.onGetYmmxListFail(it.msg)
                        }
                    }, {
                        view.onServerError(it)
                        view.onLoadMoreFinish()
                        view.onRefreshFinish()
                    })
        } else {
            // 云猫美学需要Loadding
            model.sendGetYmmxList(typeId, page, limit, view.getRxLifecycle())
                    .subscribe(object : RObserver<GetYmmxListResBean>(view.getRContext()) {
                        override fun onRNext(bean: GetYmmxListResBean) {
                            view.onLoadMoreFinish()
                            view.onRefreshFinish()
                            if (bean.success) {
                                view.onGetYmmxListSuccess(bean)
                            } else {
                                view.onGetYmmxListFail(bean.msg)
                            }
                        }

                        override fun onRError(t: Throwable) {
                            view.onServerError(t)
                            view.onLoadMoreFinish()
                            view.onRefreshFinish()
                        }
                    })
        }
    }
}