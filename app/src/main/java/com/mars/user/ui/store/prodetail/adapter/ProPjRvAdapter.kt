package com.mars.user.ui.store.prodetail.adapter

import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mars.user.R
import com.mars.user.ui.store.prodetail.bean.GetProDetailInfoResBean
import com.mars.user.widget.Show3PicView

/**
 * Created by gu on 2018/08/02
 * desc: ${desc}
 */
class ProPjRvAdapter(val isAll: Boolean) : BaseQuickAdapter<GetProDetailInfoResBean.Data.Evaluation, ProPjRvAdapter.ViewHolder>(R.layout.item_pro_pj_adapter) {


    override fun getItemCount(): Int {
        return if (!isAll) 3 else super.getItemCount()
    }

    override fun convert(helper: ProPjRvAdapter.ViewHolder?, item: GetProDetailInfoResBean.Data.Evaluation?) {

        helper?.pjUserName?.text = item?.uname
        helper?.pjUserConstant?.text = item?.pcontent
        helper?.ratingBar?.rating = item?.zhpf?.toFloat()!!
        val uploadimages = item.uploadimages
        if (uploadimages.isNotEmpty()) {
            val urlList = ArrayList<String>()
            val imgList = uploadimages.split(",")
            urlList.addAll(imgList)
            helper?.picShow?.urlList = urlList
        }
    }

    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        var pjUserName = itemView.findViewById<TextView>(R.id.pjUserName)
        var ratingBar = itemView.findViewById<RatingBar>(R.id.ratingBar)
        var pjUserConstant = itemView.findViewById<TextView>(R.id.proPjConstant)
        var picShow = itemView.findViewById<Show3PicView>(R.id.proPjImg)
    }
}