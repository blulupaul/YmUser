package com.mars.user.ui.home.ympackage.details.packagedetialmvp

import com.mars.user.base.view.BaseView
import com.mars.user.ui.home.ympackage.list.bean.GetPackageDetailResBean
import com.mars.user.ui.home.ympackage.list.bean.GetPackageListNewGroupPariidResBean

/**
 * Created by gu on 2018/08/07
 * desc: ${desc}
 */
interface PackageDetailContract {
    interface View : BaseView {
        // 获取套餐规格
        fun onGetPackagePariidSuccess(bean: GetPackageListNewGroupPariidResBean)
        fun onGetPackagePariidFail(msg: String)

        // 套餐详情
        fun onGetPackageDetailSuccess(bean:GetPackageDetailResBean)
        fun onGetPackageDetailFail(msg:String)


    }
}