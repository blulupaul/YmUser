package com.mars.user.ui.main_frg.cart.bean

/**
 * Created by gu on 2018/07/24
 * desc: ${desc}
 */

data class GwcJiaJianResBean(
        val success: Boolean,
        val msg: String,
        val data: List<Data>
) {
    data class Data(
            val totalprice: String
    )
}