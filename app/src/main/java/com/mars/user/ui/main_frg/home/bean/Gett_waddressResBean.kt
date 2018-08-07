package com.mars.user.ui.main_frg.home.bean

data class Gett_waddressResBean(
        val success: Boolean,
        val msg: String,
        val data: List<Data>) {

    data class Data(
            val ids: Int,
            val name: String,
            val _val: String,
            val des: String,
            val able: Int
    )
}