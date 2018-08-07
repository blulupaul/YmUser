package com.lesincs.simpleread.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

/**
 * Created by Administrator on 2017/9/2.
 */
abstract class BaseFrag : Fragment() {
    private var toast: Toast? = null

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