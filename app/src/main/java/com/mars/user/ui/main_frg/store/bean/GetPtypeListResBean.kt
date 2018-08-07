package com.mars.user.ui.main_frg.store.bean

/**
 * Created by gu on 2018/07/26
 * desc: ${desc}
 */

data class GetPtypeListResBean(
        val success: Boolean,
        val msg: String,
        val data: List<Data>
) {
    data class Data(
            val ptypeid: Int,
            val ptypename: String,
            var isCheck: Boolean
    )
}

