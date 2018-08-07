package com.mars.user.ui.main_frg.store.storemvp

/**
 * Created by gu on 2018/07/26
 * desc: ${desc}
 */
class StorePresenter(var view: StoreContract.View) : StoreContract.Presenter {

    private val model = StoreModel

    override fun getPTypeList() {
        model.getPTypeList(view.getRxLifecycle())
                .subscribe({
                    if (it.success) {
                        view.onGetPtypeListSuccess(it)
                    } else {
                        view.onGetPtypeListFail(it.msg)
                    }
                }, {
                    view.onServerError(it)
                })
    }

    override fun getProListByPTypeNew(page: Int, limit: Int, ptype: Int, mdid: Int, userid: Int) {
        view.onRefreshStart()
        model.getProListByPTypeNew(page, limit, ptype, mdid, userid, view.getRxLifecycle())
                .subscribe({
                    view.onRefreshFinish()
                    view.onLoadMoreFinish()
                    if (it.success) {
                        view.onGetProListByPTypeNewSuccess(it)
                    } else {
                        view.onGetProListByPTypeNewFail(it.msg)
                    }
                }, {
                    view.onRefreshFinish()
                    view.onLoadMoreFinish()
                    view.onServerError(it)
                })
    }
}