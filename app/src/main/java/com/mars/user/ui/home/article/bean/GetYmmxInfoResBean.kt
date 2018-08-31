package com.mars.user.ui.home.article.bean

/**
 * Created by gu on 2018/08/31
 * desc: ${desc}
 */

data class GetYmmxInfoResBean(
        val success: Boolean,
        val msg: String,
        val data: List<Data>
) {

    data class Data(
            val mid: Int,
            val mtype: Int,
            val mtitle: String,
            val mtime: String,
            val mcontent: String,
            val musers: List<Muser>,
            val myueducishu: Int,
            val mimages: String,
            val issc: Int
    ) {

        data class Muser(
                val uname: String
        )
    }
}