package com.mars.user.ui.home.ympackage.list.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by gu on 2018/08/07
 * desc: ${desc}
 */


data class GetPackageDetailResBean(
        val success: Boolean,
        val msg: String,
        val data: List<Data>
) {
    data class Data(
            @SerializedName("package")
            val packageTemp: Package,
            val evaluationList: List<Any>,
            val sfdz: Int
    ) {
        data class Package(
                val yid: Int,
                val packageimgs: String,
                val price: Int,
                val pricestr: String,
                val originalprice: Int,
                val originalpricestr: String,
                val pname: String,
                val nursingtime: String,
                val functionaldes: String,
                val functionaldesinfo: List<String>,
                val projectIntro: String,
                val crowusers: Any,
                val crowusersinfo: Any,
                val considerations: String,
                val considerationsinfo: List<String>,
                val servicesteps: Any,
                val servicestepsinfo: Any,
                val projectproduct: Any,
                val projectproductinfo: Any,
                val nursingchar: Any,
                val nursingcharinfo: Any,
                val fbtime: String,
                val reservationcount: String,
                val tags: String,
                val shareurl: Any,
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
}