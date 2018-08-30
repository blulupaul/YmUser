package com.mars.user.utils

import android.content.Context
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by gu on 2018/08/30
 * desc: ${desc}
 */
abstract class RObserver<R>(private val context: Context) : Observer<R> {
    private var qmuiTipDialog: QMUITipDialog? = null

    init {
        qmuiTipDialog = T.showLoading(context)
    }

    abstract fun onRNext(bean: R)

    abstract fun onRError(t: Throwable)

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: R) {
        onRNext(t)
    }

    override fun onError(e: Throwable) {
        onRError(e)
    }

    override fun onComplete() {
        qmuiTipDialog?.dismiss()
    }
}
