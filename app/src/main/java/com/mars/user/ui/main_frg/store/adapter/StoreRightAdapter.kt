package com.mars.user.ui.main_frg.store.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mars.user.R
import com.mars.user.ui.main_frg.store.bean.GetProductListByPTypeNewResBean
import com.mars.user.utils.glideLoad

/**
 * Created by gu on 2018/07/26
 * desc: ${desc}
 */
class StoreRightAdapter :
        BaseQuickAdapter<GetProductListByPTypeNewResBean.Data, BaseViewHolder>(R.layout.item_store_right) {

    override fun convert(helper: BaseViewHolder?, item: GetProductListByPTypeNewResBean.Data?) {
        glideLoad(mContext, item?.pimages!!, helper?.getView(R.id.proImg)!!)
        val jbdk = item.jbdk
        val price = item.price
        val spsl = item.spsl
        helper?.apply {
            setText(R.id.proName, item.pname)
            setText(R.id.proGuige, item.gglx)
            setText(R.id.proPrice, "￥ $price")
            setText(R.id.proCount, "x $spsl")
            setText(R.id.jbdkDesc, "金币最多抵扣 $jbdk 元")
        }
    }
}