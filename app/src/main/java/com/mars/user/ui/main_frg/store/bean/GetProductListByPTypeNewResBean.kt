package com.mars.user.ui.main_frg.store.bean

/**
 * Created by gu on 2018/07/26
 * desc: ${desc}
 */

data class GetProductListByPTypeNewResBean(
        val success: Boolean,
        val msg: String,
        val data: List<Data>
) {
    data class Data(
            val pid: Int,
            val pimages: String,
            val pname: String,
            val price: Double,
            val oldprice: Double,
            val pdis: String,
            val pdeta: String,
            val pdetainfo: List<String>,
            val hzpbzq: String,
            val xq: String,
            val spfl: Int,
            val gglx: String,
            val syfz: String,
            val hzpjhl: String,
            val fbtime: String,
            val spsl: Int,
            val jbdk: String,
            val pssm: String,
            val psfy: String,
            val gmrs: String,
            val issc: Int,
            val promark: Int,
            val tjjb: Int,
            val isexpress: Int,
            val buymiss: Int,
            val pdetails: String,
            val canbuy: String
    )
}