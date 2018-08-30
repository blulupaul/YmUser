package com.mars.user.ui.home.article.mvp

import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.base.view.BaseView
import com.mars.user.ui.main_frg.home.bean.GetYmmxListResBean
import io.reactivex.Observable

/**
 * Created by gu on 2018/08/29
 * desc: ${desc}
 */
interface ArticleListContract {
    interface View :BaseView{
        fun onRefreshFinish()
        fun onLoadMoreFinish()

        fun onGetYmmxListSuccess(bean: GetYmmxListResBean)
        fun onGetYmmxListFail(msg: String)
    }

    interface Model{
        fun sendGetYmmxList(typeId: Int, page: Int, limit: Int, rxLifecycle: RxLifecycle): Observable<GetYmmxListResBean>
    }

    interface Presenter{
        fun onStart()
        fun onRefresh()
        fun onLoadMore()

        fun sendGetYmmxList(typeId: Int, page: Int, limit: Int)
    }
}