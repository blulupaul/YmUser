package com.mars.user.ui.main_frg.home.bean

/**
 * Created by gu on 2018/07/19
 * desc: ${desc}
 */

data class GetYmmxListResBean(
        val success: Boolean,
        val msg: String,
        val data: List<Data>) {

    data class Data(
            val mid: Int,
            val mtitle: String,
            val mimages: String,
            val mtime: String,
            val myueducishu: Int
    )

}
