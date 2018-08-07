package com.mars.user.utils

import android.content.Context
import com.mars.user.R
import com.mars.user.interfac.OnSheetItemClickListener
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet

/**
 * Created by gu on 2018/08/06
 * desc: ${desc}
 */
//class ShareAlert(private val context: Context) {

fun showShareAlert(context: Context,listener: OnSheetItemClickListener) {
    val SHARE_TO_PENGYOUQUAN = 1
    val SHARE_TO_WEIXIN = 2
    QMUIBottomSheet.BottomGridSheetBuilder(context).run {
        addItem(R.mipmap.weixinshare, "微信", SHARE_TO_WEIXIN, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
        addItem(R.mipmap.pengyouquan, "朋友圈", SHARE_TO_PENGYOUQUAN, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
        setOnSheetItemClickListener { dialog, itemView ->
            dialog.dismiss()
            val tag = itemView.tag as Int
            when (tag) {
                SHARE_TO_WEIXIN -> {
                    listener.shareToweixin()
                }

                SHARE_TO_PENGYOUQUAN -> {
                    listener.shareTopengyouquan()
                }
            }
        }
        build().show()
    }
}
//}