package com.mars.user.base.act

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.app.IApp
import com.mars.user.base.view.BaseView
import com.mars.user.utils.T
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import me.imid.swipebacklayout.lib.app.SwipeBackActivity

/**
 * Created by gu on 2018/7/17
 * Desc: Activity 基类
 */

abstract class BaseActivity : SwipeBackActivity(), BaseView {
    private var iApp: IApp? = null
    var context: Context? = null

    override fun onServerError(t: Throwable) {
        showCenterToast("服务器返回错误：${t.message}")
    }

    override fun getRxLifecycle(): RxLifecycle {
        return RxLifecycle.bind(this)
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
//            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.LTGRAY   //这里动态修改颜色
        }
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(setLayoutId(), null)
        setContentView(view)
        context = this
        addAct(this)
        iApp = application as IApp?
        swipeBackLayout.setEdgeSize(100)
        QMUIStatusBarHelper.setStatusBarLightMode(this)
        configViews()
    }

    protected abstract fun setLayoutId(): Int

    protected abstract fun configViews()

    fun addAct(act: Activity) {
        iApp?.addActivity(act)
    }

    fun removeAct(act: Activity) {
        iApp?.removeActivity(act)
    }

    fun removeAllAct() {
        iApp?.removeAllActivity()
    }

    fun keepMainActivity() {
        iApp?.keepMainActivity()
    }

    fun showSuccessAlert(msg: String): QMUITipDialog {
        return T.showSuccessAlert(context!!, msg)
    }

    fun showFailAlert(msg: String) {
        T.showFailAlert(context!!, msg)
    }

    fun showLoading(): QMUITipDialog {
        return T.showLoading(this)
    }

    private var toast: Toast? = null

    fun showCenterToast(content: String) {
        if (toast == null) {
            toast = Toast.makeText(context!!, content, Toast.LENGTH_SHORT)
            toast?.setGravity(Gravity.CENTER, 0, 0)
        } else {
            toast?.setText(content)
        }
        toast?.show()
    }

    override fun onDestroy() {
        removeAct(this)
        super.onDestroy()
    }
}
