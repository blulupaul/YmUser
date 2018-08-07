package com.mars.user.ui.main_frg.cart.bean

/**
 * Created by gu on 2018/07/24
 * desc: ${desc}
 */

data class GwcJiaJianReqBean(
    val gwcidlist: List<String>,
    val curgwcid: Int,
    val userid: Int,
    val type: Int,
    val count: Int
)