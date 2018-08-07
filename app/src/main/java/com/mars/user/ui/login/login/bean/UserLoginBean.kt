package com.mars.user.ui.login.login.bean

/**
 * Created by gu on 2018/7/17
 * Desc: 获取用户信息实体类
 */


data class UserLoginBean(
        val success: Boolean,
        val msg: String,
        val data: List<Data>
) {

    data class Data(
            val userid: Int,
            val telphone: String,
            val password: String,
            val openid: String,
            val invitationcode: Any,
            val uname: String,
            val unickname: String,
            val uimage: String,
            val kxye: Int,
            val jhjbye: Int,
            val wjhjbye: Any,
            val tcyl: Int,
            val sex: Int,
            val nl: Int,
            val wxno: String,
            val birthday: String,
            val usertype: Int,
            val usertypestr: String,
            val idcard: String,
            val sfzzmz: String,
            val sfzfmz: String,
            val bzm: Any,
            val littleimg: String,
            val mdid: Int,
            val storename: String,
            val hobbies: String,
            val constellation: String,
            val introduction: String,
            val otherphones: Any,
            val score: String,
            val islhb: Int,
            val mlstype: Int,
            val hxpass: String,
            val myfiend: List<Myfiend>,
            val pingfen: String,
            val aliplayaccount: Any,
            val aliplayname: Any,
            val wechatrealname: String,
            val saddress: String,
            val unionid: String
    ) {

        data class Myfiend(
                val uimage: String,
                val xiugaiids: Int,
                val uname: String,
                val telphone: String,
                val beizhu: String,
                val userid: Int,
                val usertype: Int
        )
    }

}