package com.mars.user.ui.home.ympackage

import android.content.Context
import android.content.Intent
import com.mars.user.R
import com.mars.user.base.BaseActivity

/**
 * Created by gu on 2018/8/6
 * Desc: 云猫项目
 */
class YmPackageActivity : BaseActivity() {

    companion object {
        fun startSelf(context: Context) {
            val intent = Intent(context, YmPackageActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_ym_package
    }

    override fun configViews() {

    }
}
