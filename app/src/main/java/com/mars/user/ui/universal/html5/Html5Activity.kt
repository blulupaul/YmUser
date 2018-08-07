package com.mars.user.ui.universal.html5

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Message
import android.view.ViewGroup
import android.webkit.*
import com.mars.user.R
import com.mars.user.base.BaseActivity
import com.mars.user.constant.BOOLEAN_KEY
import com.mars.user.constant.STRING_KEY
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import kotlinx.android.synthetic.main.activity_html5.*

/**
 * Created by gu on 2018/8/6
 * Desc: 加载Html5文本或者URL
 */

class Html5Activity : BaseActivity() {

    private var data: String? = null
    private var isUrl: Boolean? = null
    private var qmuiTipDialog: QMUITipDialog? = null

    companion object {
        fun startSelf(context: Context, data: String, isUrl: Boolean) {
            val intent = Intent(context, Html5Activity::class.java)
            intent.putExtra(STRING_KEY, data)
            intent.putExtra(BOOLEAN_KEY, isUrl)
            context.startActivity(intent)
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_html5
    }

    override fun configViews() {
        QMUIStatusBarHelper.translucent(this)

        initListener()
        data = intent.getStringExtra(STRING_KEY)
        isUrl = intent.getBooleanExtra(BOOLEAN_KEY, false)

        val mWebSettings = webView.settings
        mWebSettings.setSupportZoom(true)
        mWebSettings.loadWithOverviewMode = true
        mWebSettings.useWideViewPort = true
        mWebSettings.defaultTextEncodingName = "utf-8"
        mWebSettings.loadsImagesAutomatically = true

        //调用JS方法.安卓版本大于17,加上注解 @JavascriptInterface
        mWebSettings.javaScriptEnabled = true

        //        saveData(mWebSettings)
        //        newWin(mWebSettings)
        webView.webViewClient = webViewClient
        if (isUrl!!) {
            webView.webChromeClient = webChromeClient
            webView.loadUrl(data)
        } else {
            webView.loadData(data, "text/html; charset=UTF-8", null)
        }
    }

    private fun initListener() {
        backHtml.setOnClickListener {
            finish()
        }
    }

    private var webViewClient: WebViewClient = object : WebViewClient() {

        /**
         * 多页面在同一个WebView中打开，就是不新建activity或者调用系统浏览器打开
         */
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (isUrl!!) {
                view.loadUrl(url)
                return true
            }
            return super.shouldOverrideUrlLoading(view, url)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            qmuiTipDialog = showLoading()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            qmuiTipDialog?.dismiss()
        }
    }

    private var webChromeClient: WebChromeClient = object : WebChromeClient() {

        //=========HTML5定位==========================================================
        //需要先加入权限
        //<uses-permission android:name="android.permission.INTERNET"/>
        //<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
        //<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
        override fun onReceivedIcon(view: WebView, icon: Bitmap) {
            super.onReceivedIcon(view, icon)
        }

        override fun onGeolocationPermissionsHidePrompt() {
            super.onGeolocationPermissionsHidePrompt()
        }

        override fun onGeolocationPermissionsShowPrompt(origin: String, callback: GeolocationPermissions.Callback) {
            callback.invoke(origin, true, false)//注意个函数，第二个参数就是是否同意定位权限，第三个是是否希望内核记住
            super.onGeolocationPermissionsShowPrompt(origin, callback)
        }
        //=========HTML5定位==========================================================

        //=========多窗口的问题==========================================================
        override fun onCreateWindow(view: WebView, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message): Boolean {
            val transport = resultMsg.obj as WebView.WebViewTransport
            transport.webView = view
            resultMsg.sendToTarget()
            return true
        }
        //=========多窗口的问题==========================================================
    }

    public override fun onPause() {
        super.onPause()
        webView.onPause()
        webView.pauseTimers() //小心这个！！！暂停整个 WebView 所有布局、解析、JS。
    }

    public override fun onResume() {
        super.onResume()
        webView.onResume()
        webView.resumeTimers()
    }

    /**
     * 多窗口的问题
     */
    private fun newWin(mWebSettings: WebSettings) {
        //html中的_bank标签就是新建窗口打开，有时会打不开，需要加以下
        //然后 复写 WebChromeClient的onCreateWindow方法
        mWebSettings.setSupportMultipleWindows(false)
        mWebSettings.javaScriptCanOpenWindowsAutomatically = true
    }

    /**
     * HTML5数据存储
     */
    private fun saveData(mWebSettings: WebSettings) {
        //有时候网页需要自己保存一些关键数据,Android WebView 需要自己设置
        mWebSettings.domStorageEnabled = true
        mWebSettings.databaseEnabled = true
        mWebSettings.setAppCacheEnabled(true)
        val appCachePath = applicationContext.cacheDir.absolutePath
        mWebSettings.setAppCachePath(appCachePath)
    }

//    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_BACK && webView!!.canGoBack()) {
//            webView!!.goBack()
//            return true
//        }
//
//        return super.onKeyDown(keyCode, event)
//    }

    override fun onDestroy() {
        super.onDestroy()
        if (webView != null) {
            webView.clearHistory()
            (webView.parent as ViewGroup).removeView(webView)
            webView.loadUrl("about:blank")
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            webView.stopLoading()
            webView.webChromeClient = null
            webView.webViewClient = null
            webView.destroy()
        }
    }
}