package com.mars.user.ui.main_frg.home.bean

/**
 * Created by gu on 2018/07/19
 * desc: ${desc}
 */

data class GetGangGaoweiResBean(
        val success: Boolean,
        val msg: String,
        val data: Data
) {
    data class Data(
            val actmimg: String,
            val pid: Int,
            val promark: Int,
            val jbdk: Int,
            val buymiss: Int,
            val isexpress: Int,
            val canbuy: Int,
            val ptype: Int
    )
}