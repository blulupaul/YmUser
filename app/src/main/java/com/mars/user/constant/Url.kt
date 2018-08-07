package com.mars.user.constant

/**
 * Created by gu on 2018/07/17
 * desc: 接口 & H5 地址
 */

class Url{
    companion object {
        /**
         * 接口地址
         */
        val BASE_URL_NEW = "http://www.yuekee.com.cn/ymmyapi/"
        val BASE_URL_TEST = "http://116.62.226.32/ymmyapi/"

        /**
         * 三方接口地址
         */
        val BASE_URL_PUSH_NEW = "http://www.yuekee.com.cn/ymmyAPIThree/"

        val LOADIMGFILE = "fileUpLoad/fileUpLoad"

        /**
         * 图片的地址 字符串拼接
         */
        val PIC_URL = "http://www.yuekee.com.cn:8080/Upload/"

        /**
         * 老板业绩H5地址
         */
        val BOSS_YE_JI_H5_URL = "http://www.yuekee.com.cn/ymmyh5/bossachievement.html?userid="

        /**
         * 美疗师业绩H5地址
         */
        val MLS_YE_JI_H5_URL = "http://www.yuekee.com.cn/ymmyh5/grade.html?mlsid="
    }
}