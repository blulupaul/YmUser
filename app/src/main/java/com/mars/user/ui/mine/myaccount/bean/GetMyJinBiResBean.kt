package com.mars.user.ui.mine.myaccount.bean

/**
 * Created by gu on 2018/07/27
 * desc: ${desc}
 */

data class GetMyJinBiResBean(
    val success: Boolean,
    val msg: String,
    val data: List<Data>
){
    data class Data(
            val jid: Int,
            val price: Double,
            val cztime: String,
            val reamk: String,
            val jbtype: Int,
            val totalprice: Double
    )
}

