package com.mars.user.ui.main_frg.home.adapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mars.user.R
import com.mars.user.ui.main_frg.home.bean.GetYmmxListResBean
import com.mars.user.utils.glideLoad

/**
 * Created by gu on 2018/07/20
 * desc: ${desc}
 */
class MeiWenRvAdapter :
        BaseQuickAdapter<GetYmmxListResBean.Data, BaseViewHolder>(R.layout.item_home_frag_meiwen_rv_adapter) {

    override fun convert(helper: BaseViewHolder, item: GetYmmxListResBean.Data) {
        glideLoad(mContext!!, item.mimages, helper.getView<View>(R.id.img_itemHomeIcon) as ImageView)
        helper.setText(R.id.tv_itemHomeTitle, item.mtitle)
        helper.setText(R.id.tv_itemHomeTime, "发表时间:" + item.mtime)
        helper.setText(R.id.tv_itemHomeRead, "阅读数:" + item.myueducishu)
    }
}
