package com.mars.user.ui.home.ympackage.list.ympackagemvp

import com.mars.user.ui.home.ympackage.list.bean.GetPackageListNewGroupResBean
import com.mars.user.utils.RObserver

/**
 * Created by gu on 2018/08/07
 * desc: ${desc}
 */
class YmPackageListPresenter(private val view: YmPackageListContract.View) : YmPackageListContract.Presenter {
    private val model = YmPackageListModel

    override fun getYmPackageList(mdid: Int, page: Int, limit: Int) {
//        model.getYmPackageList(mdid, page, limit, view.getRxLifecycle())
//                .subscribe({
//                    if (it.success) {
//                        view.onGetYmPackageListSuccess(it)
//                    } else {
//                        view.onGetYmPackageListFail(it.msg)
//                    }
//                }, {
//                    view.onServerError(it)
//                })
        model.getYmPackageList(mdid,page,limit,view.getRxLifecycle())
                .subscribe(object :RObserver<GetPackageListNewGroupResBean>(view.getRContext()){
                    override fun onRNext(bean: GetPackageListNewGroupResBean) {
                        if (bean.success) {
                            view.onGetYmPackageListSuccess(bean)
                        } else {
                            view.onGetYmPackageListFail(bean.msg)
                        }
                    }

                    override fun onRError(t: Throwable) {
                        view.onServerError(t)
                    }
                })
    }
}