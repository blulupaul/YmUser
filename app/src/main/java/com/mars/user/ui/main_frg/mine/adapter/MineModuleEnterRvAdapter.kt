package com.mars.user.ui.main_frg.mine.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mars.user.R
import com.mars.user.ui.main_frg.home.bean.HomeModelEnterBean
import com.mars.user.utils.glideLoad


/**
 * Created by gu on 2018/07/20
 * desc: ${desc}
 */
class MineModuleEnterRvAdapter :
        BaseQuickAdapter<HomeModelEnterBean, MineModuleEnterRvAdapter.ViewHolder>(R.layout.item_mine_module_enter_rv_adapter) {

    override fun convert(helper: ViewHolder, item: HomeModelEnterBean) {
        glideLoad(mContext!!, item.iconId, helper.mImgIcon)
        helper.mTvText.text = item.text
    }

    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val mImgIcon: ImageView = itemView.findViewById(R.id.img_icon) as ImageView
        val mTvText: TextView = itemView.findViewById(R.id.tv_text) as TextView
    }
}
