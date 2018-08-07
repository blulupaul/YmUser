package com.mars.user.ui.main_frg.home.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mars.user.R
import com.mars.user.ui.main_frg.home.bean.HomeModelEnterBean
import com.mars.user.utils.glideLoad
import q.rorbin.badgeview.QBadgeView


/**
 * Created by gu on 2018/07/20
 * desc: ${desc}
 */
class HomeModuleEnterRvAdapter :
        BaseQuickAdapter<HomeModelEnterBean, HomeModuleEnterRvAdapter.ViewHolder>(R.layout.item_home_module_enter_rv_adapter) {

    override fun convert(helper: ViewHolder, item: HomeModelEnterBean) {
        glideLoad(mContext!!, item.iconId, helper.mImgIcon)
        helper.mTvText.text = item.text
        helper.mQBadgeView.badgeNumber = 0
        if (helper.layoutPosition == 6) {
            helper.mQBadgeView.badgeNumber = item.count
        }
    }

    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val mImgIcon: ImageView = itemView.findViewById(R.id.img_icon) as ImageView
        val mTvText: TextView = itemView.findViewById(R.id.tv_text) as TextView
        val mQBadgeView: QBadgeView = QBadgeView(mContext)

        init {
            mQBadgeView.bindTarget(itemView.findViewById(R.id.root))
            mQBadgeView.setGravityOffset(10f, 2f, true)
        }
    }
}
