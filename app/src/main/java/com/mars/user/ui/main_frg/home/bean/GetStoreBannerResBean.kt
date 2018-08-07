package com.mars.user.ui.main_frg.home.bean

/**
 * Created by gu on 2018/07/19
 * desc: ${desc}
 */

data class GetStoreBannerResBean(
        val success: Boolean,
        val msg: String,
        val data: List<Data>
) {
    data class Data(
            val bid: Int,
            val bimage: String,
            val bimagetype: Int,
            val bimageurl: String,
            val details: String
    )
}

