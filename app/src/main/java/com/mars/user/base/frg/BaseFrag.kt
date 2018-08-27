package com.lesincs.simpleread.base

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.base.view.BaseView


/**
 * Created by Administrator on 2017/9/2.
 */
abstract class BaseFrag : Fragment(), BaseView {
    private var toast: Toast? = null

    override fun onServerError(t: Throwable) {
        showCenterToast("服务器返回错误：${t.message}")
    }

    override fun getRxLifecycle(): RxLifecycle {
        return RxLifecycle.bind(context as Activity)
    }

    /*免去每次新建frag都要加载view*/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configViews()
    }

    abstract fun getLayoutId(): Int

    abstract fun configViews()

    fun showCenterToast(content: String) {
        if (toast == null) {
            toast = Toast.makeText(context!!, content, Toast.LENGTH_SHORT)
            toast?.setGravity(Gravity.CENTER, 0, 0)
        } else {
            toast?.setText(content)
        }
        toast?.show()
    }
}