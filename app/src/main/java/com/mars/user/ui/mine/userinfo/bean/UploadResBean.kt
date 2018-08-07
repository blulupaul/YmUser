package com.mars.user.ui.mine.userinfo.bean

/**
 * Created by gu on 2018/07/27
 * desc: ${desc}
 */

data class UploadResBean(
    val success: Boolean,
    val msg: String,
    val data: List<Data>
){
    data class Data(
            val BigFileName: String,
            val SmallFileName: String,
            val width: Int,
            val height: Int
    )
}

