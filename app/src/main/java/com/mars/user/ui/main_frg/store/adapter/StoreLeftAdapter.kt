package com.mars.user.ui.main_frg.store.adapter

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mars.user.R
import com.mars.user.ui.main_frg.store.bean.GetPtypeListResBean

/**
 * Created by gu on 2018/07/26
 * desc: ${desc}
 */
class StoreLeftAdapter : BaseQuickAdapter<GetPtypeListResBean.Data, BaseViewHolder>(R.layout.item_store_left) {

    override fun convert(helper: BaseViewHolder?, item: GetPtypeListResBean.Data?) {
        if (item?.isCheck!!) {
            helper?.getView<View>(R.id.rootLeft)?.setBackgroundColor(Color.parseColor("#E5E5E5"))
            helper?.getView<TextView>(R.id.storeLeftText)?.setTextColor(Color.parseColor("#59DCE0"))
            helper?.getView<View>(R.id.indicator)?.visibility = View.VISIBLE
        } else {
            helper?.getView<View>(R.id.rootLeft)?.setBackgroundColor(Color.WHITE)
            helper?.getView<TextView>(R.id.storeLeftText)?.setTextColor(Color.parseColor("#666666"))
            helper?.getView<View>(R.id.indicator)?.visibility = View.INVISIBLE
        }

        helper?.setText(R.id.storeLeftText, item.ptypename)
    }
}