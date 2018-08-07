package com.mars.user.ui.mine.myaccount.accountmvp

/**
 * Created by gu on 2018/07/27
 * desc: ${desc}
 */
class AccountPresenter(val view: AccountContract.View) : AccountContract.Presenter {
    val model = AccountModel
    override fun getMyJinbi(userid: Int, type: Int, page: Int, limit: Int) {
        model.getMyJinbi(userid, type, page, limit, view.getRxLifecycle())
                .subscribe({
                    if (it.success) {
                        view.onGetMyJinBiSuccess(it)
                    } else {
                        view.onGetMyJinbiFail(it.msg)
                    }
                }, {
                    view.onServerError(it)
                })
    }
}