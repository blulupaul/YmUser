package com.mars.user.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.view.Gravity
import android.widget.Toast
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog


object T {

    @SuppressLint("StaticFieldLeak")
    private var builder: QMUITipDialog.Builder? = null
    private var qmuiTipDialog: QMUITipDialog? = null
    private var toast: Toast? = null

    fun showSuccessAlert(context: Context, msg: String): QMUITipDialog {
//        if (builder == null) {
        builder = QMUITipDialog.Builder(context)
//        }

        builder!!.setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(msg)
        qmuiTipDialog = builder!!.create()
        qmuiTipDialog!!.show()

        Handler().postDelayed({ qmuiTipDialog!!.dismiss() }, 1000)

        return qmuiTipDialog!!
    }

    fun showFailAlert(context: Context, msg: String) {
//        if (builder == null) {
        builder = QMUITipDialog.Builder(context)
//        }

        builder!!.setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord(msg)
        qmuiTipDialog = builder!!.create()
        qmuiTipDialog!!.show()

        Handler().postDelayed({ qmuiTipDialog!!.dismiss() }, 1000)
    }

    fun showLoading(context: Context): QMUITipDialog {
//        if (builder == null) {
        builder = QMUITipDialog.Builder(context)
//        }

        builder!!.setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载···")
        qmuiTipDialog = builder!!.create()
        qmuiTipDialog!!.show()

//        Handler().postDelayed({ qmuiTipDialog!!.dismiss() }, 1000)

        return qmuiTipDialog!!
    }

    fun showToast(context: Context,
                  content: String) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT)
        } else {
            toast?.setText(content)
        }
        toast?.show()
    }

    fun showToastCenter(context: Context,
                        content: String) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT)
            toast?.setGravity(Gravity.CENTER, 0, 0)
        } else {
            toast?.setText(content)
        }
        toast?.show()
    }
}
