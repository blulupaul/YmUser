package com.mars.user.ui.main_frg.cart.bean

/**
 * Created by gu on 2018/07/24
 * desc: ${desc}
 */

data class GetMyGwcListResBean(
        val success: Boolean,
        val msg: String,
        val data: List<Data>
) {

    data class Data(
            val gwcid: Int,
            val spid: Int,
            val pname: String,
            var counts: Int,
            val pimages: String,
            val price: String,
            val gglx: String,
            val pid: Int,
            val promark: Int,
            val tjjb: Int,
            val isexpress: Int,
            var isCheck: Boolean
    )
}