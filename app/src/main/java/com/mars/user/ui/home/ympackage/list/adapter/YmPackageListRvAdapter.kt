package com.mars.user.ui.home.ympackage.list.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mars.user.R
import com.mars.user.ui.home.ympackage.list.bean.GetPackageListNewGroupResBean
import com.mars.user.utils.glideLoad

/**
 * Created by gu on 2018/08/07
 * desc: ${desc}
 */
class YmPackageListRvAdapter : BaseQuickAdapter<GetPackageListNewGroupResBean.Data, BaseViewHolder>(R.layout.item_ympackage_list_rv_adapter) {
    override fun convert(helper: BaseViewHolder?, item: GetPackageListNewGroupResBean.Data?) {
        glideLoad(mContext, item?.packageimgs!!, helper?.getView(R.id.packageImg)!!)
        var packageTypeStr = ""
        when (item.packtype) {
            1 -> packageTypeStr = "到店上门"
            2 -> packageTypeStr = "到店"
            3 -> packageTypeStr = "上门"
        }
        helper?.setText(R.id.packageType, packageTypeStr)
        helper?.setText(R.id.packageName, item.considerations)
        helper?.setText(R.id.packagePrice, "¥ ${item.price}")
        helper?.setText(R.id.packageBuyCount, item.reservationcount)
        helper?.setText(R.id.packageTime, item.nursingtime)

        if (helper?.layoutPosition!! % 2 == 0) {
            helper.getView<View>(R.id.split).visibility = View.GONE
        }else{
            helper.getView<View>(R.id.split).visibility = View.VISIBLE
        }
    }
}