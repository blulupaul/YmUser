package com.mars.user.ui.home.ympackage.bean

/**
 * Created by gu on 2018/08/06
 * desc: ${desc}
 */

data class GetPackageListNewGroupResBean(
        val success: Boolean,
        val msg: String,
        val data: List<Data>
) {
    data class Data(
            val yid: Int,
            val packageimgs: String,
            val price: Int,
            val pricestr: String,
            val originalprice: Int,
            val originalpricestr: String,
            val pname: String,
            val nursingtime: String,
            val functionaldes: String,
            val functionaldesinfo: String,
            val projectIntro: String,
            val crowusers: String,
            val crowusersinfo: String,
            val considerations: String,
            val considerationsinfo: String,
            val servicesteps: String,
            val servicestepsinfo: String,
            val projectproduct: String,
            val projectproductinfo: String,
            val nursingchar: String,
            val nursingcharinfo: String,
            val fbtime: String,
            val reservationcount: String,
            val tags: String,
            val shareurl: String,
            val projecttype: Int,
            val activitytype: Int,
            val packtype: Int,
            val packtypestr: String,
            val fwcount: Int,
            val pdetails: String,
            val sfgm: Int,
            val packageremark: Int,
            val parid: Int
    )
}