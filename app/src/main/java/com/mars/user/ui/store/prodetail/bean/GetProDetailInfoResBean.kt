package com.mars.user.ui.store.prodetail.bean

import java.io.Serializable

/**
 * Created by gu on 2018/07/27
 * desc: ${desc}
 */

data class GetProDetailInfoResBean(
        val success: Boolean,
        val msg: String,
        val data: List<Data>
):Serializable {
    data class Data(
            val evaluationList: List<Evaluation>,
            val sppjcount: Int,
            val product: Product
    ):Serializable {
        data class Evaluation(
                val eid: Int,
                val userid: Int,
                val littleimg: String,
                val uname: String,
                val unickname: String,
                val pcontent: String,
                val satisfaction: Int,
                val attitude: Int,
                val skill: Int,
                val ptime: String,
                val uploadimages: String,
                val pid: String,
                val hdid: String,
                val productid: Int,
                val clstate: Int,
                val zhpf: Int
        ):Serializable

        data class Product(
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
        ):Serializable
    }
}