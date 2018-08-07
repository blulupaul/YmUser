package com.mars.user.ui.main.bean

/**
 * Created by gu on 2018/07/18
 * desc: ${desc}
 */

data class GetUpdateAppResBean(
        val success: Boolean,
        val msg: String,
        val data: List<Data>
) {
    data class Data(
            val contents: String,
            val downurl: String,
            val versionCode: Int,
            val isforce: Int
    )
}