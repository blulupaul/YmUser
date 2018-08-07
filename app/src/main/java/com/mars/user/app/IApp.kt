package com.mars.user.app

import android.app.Activity
import android.app.Application
import com.mars.user.ui.main.MainActivity
import com.mars.user.utils.CrashHandler
import com.mars.user.utils.DynamicTimeFormat
import com.mars.user.utils.SpUtil
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import java.util.*


/**
 * Created by gu on 2018/07/17
 * desc: ${desc}
 */
class IApp : Application() {

    private var actList = ArrayList<Activity>()

    override fun onCreate() {
        super.onCreate()
        SpUtil.init(applicationContext, "user", MODE_PRIVATE)
        val crash = CrashHandler.getInstance()
        crash.init(applicationContext)
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            //            全局设置主题颜色（优先级第二低，可以覆盖 DefaultRefreshInitializer 的配置，与下面的ClassicsHeader绑定）
            //            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)
            val deta = Random().nextInt(7 * 24 * 60 * 60 * 1000)
            ClassicsHeader(context).apply {
                spinnerStyle = SpinnerStyle.Translate
                setLastUpdateTime(Date(System.currentTimeMillis() - deta))
                setTimeFormat(DynamicTimeFormat("更新于 %s"))
            }
        }

    }

    fun addActivity(act: Activity) {
        if (!actList.contains(act)) {
            actList.add(act)
        }
    }

    fun removeActivity(act: Activity) {
        if (!actList.contains(act)) {
            actList.remove(act)
        }
    }

    fun removeAllActivity() {
        if (actList.size != 0) {
            actList.clear()
        }
    }

    fun keepMainActivity() {
        actList.forEach {
            if (it !is MainActivity) {
                it.finish()
            }
        }
    }
}