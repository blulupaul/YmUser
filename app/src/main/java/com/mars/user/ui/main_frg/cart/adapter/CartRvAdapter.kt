package com.mars.user.ui.main_frg.cart.adapter

import android.content.Context
import android.widget.CheckBox
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mars.user.R
import com.mars.user.ui.main_frg.cart.bean.GetMyGwcListResBean
import com.mars.user.utils.glideLoad

/**
 * Created by gu on 2018/07/25
 * desc: ${desc}
 */
class CartRvAdapter(var context: Context) :
        BaseQuickAdapter<GetMyGwcListResBean.Data, BaseViewHolder>(R.layout.item_cart_list) {

    override fun convert(helper: BaseViewHolder?, item: GetMyGwcListResBean.Data?) {
        helper?.getView<CheckBox>(R.id.checkItem)?.isChecked = item?.isCheck!!
        glideLoad(context, item.pimages, helper?.getView(R.id.proImg)!!)
        val price = item.price
        helper?.apply {
            addOnClickListener(R.id.toStore)
            addOnClickListener(R.id.collect)
            addOnClickListener(R.id.delete)
            addOnClickListener(R.id.btn_subtract)
            addOnClickListener(R.id.btn_add)
            addOnClickListener(R.id.checkItem)
            setText(R.id.proName, item.pname)
            setText(R.id.proMoney, "￥：$price")
            setText(R.id.proGuige, item.gglx)
            setText(R.id.proCount, item.counts.toString())
        }
    }
}