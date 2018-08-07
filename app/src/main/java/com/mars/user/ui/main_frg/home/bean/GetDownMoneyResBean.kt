package com.mars.user.ui.main_frg.home.bean

/**
 * Created by gu on 2018/01/04
 * desc: ${desc}
 */

data class GetDownMoneyResBean(
        val success: Boolean,
        val msg: String,
        val data: List<Data>
) {

    data class Data(
            val ids: Int,
            val pname: String,
            val pimg: String,
            val pdetails: String,
            val oldprice: String,
            val price: String,
            val minprice: String,
            val maxprice: String,
            val depricemin: Double,
            val pid: Int,
            val ptype: Int
    )
}