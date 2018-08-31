package com.mars.user.ui.home.article

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Message
import android.view.ViewGroup
import android.webkit.GeolocationPermissions
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.mars.user.R
import com.mars.user.base.act.BaseActivity
import com.mars.user.bean.BaseNomalResBean
import com.mars.user.constant.INT_KEY
import com.mars.user.constant.USER_ID
import com.mars.user.constant.Url
import com.mars.user.ui.home.article.articledetailmvp.ArticleDetailContract
import com.mars.user.ui.home.article.articledetailmvp.ArticleDetailPresenter
import com.mars.user.ui.home.article.bean.GetYmmxInfoResBean
import com.mars.user.utils.SpUtil
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import kotlinx.android.synthetic.main.activity_article_detail.*

class ArticleDetailActivity : BaseActivity(), ArticleDetailContract.View {
    override fun onGetYmmxInfoSuccess(bean: GetYmmxInfoResBean) {
        //  是否收藏 0-代表未收藏，其他代表收藏
        if (bean.data[0].issc == 0) {
            isCollect = false
            collect.setImageResource(R.mipmap.round_uncollect_icon)
        } else {
            isCollect = true
            collect.setImageResource(R.mipmap.round_collect_icon)
        }
    }

    override fun onGetYmmxInfoFail(msg: String) {
        showFailAlert(msg)
    }

    override fun onAddCollectSuccess(bean: BaseNomalResBean) {
        isCollect = true
        collect.setImageResource(R.mipmap.round_collect_icon)
        showSuccessAlert(bean.msg)
    }

    override fun onAddCollectFail(msg: String) {
        showFailAlert(msg)
    }

    override fun onCancelCollectSuccess(bean: BaseNomalResBean) {
        isCollect = false
        collect.setImageResource(R.mipmap.round_uncollect_icon)
        showSuccessAlert(bean.msg)
    }

    override fun onCancelCollectFail(msg: String) {
        showFailAlert(msg)
    }

    override fun onShareToWeiXin() {
        showCenterToast("分享到微信")
    }

    override fun onShareToPengyouquan() {
        showCenterToast("分享到朋友圈")
    }

    private var qmuiTipDialog: QMUITipDialog? = null
    private var mid: Int? = null
    private var userid: Int? = null
    private var url: String? = null
    private var isCollect: Boolean? = null
    private val presenter = ArticleDetailPresenter(this)

    companion object {
        fun startSelf(context: Context, mid: Int) {
            val intent = Intent(context, ArticleDetailActivity::class.java)
            intent.putExtra(INT_KEY, mid)
            context.startActivity(intent)
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_article_detail
    }

    override fun configViews() {
        mid = intent.getIntExtra(INT_KEY, 0)
        userid = SpUtil.getInstance().getInt(USER_ID)
        initListener()
        initWebView()
        presenter.sendGetYmmxInfo(mid!!, userid!!)

        url = Uri.parse(Url.ARTICLE_DETAIL_SHARE_URL).buildUpon()
                .appendQueryParameter("mxid", mid?.toString())
                .appendQueryParameter("userid", userid?.toString())
                .build()
                .toString()
        webView.loadUrl(url)
    }

    fun initWebView() {
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

        webView.webChromeClient = webChromeClient
    }

    private fun initListener() {
        back.setOnClickListener {
            finish()
        }

        share.setOnClickListener {
            presenter.showShareAlert()
        }

        collect.setOnClickListener {
            if (isCollect!!)
                presenter.cancelCollect(userid!!, mid!!)
            else
                presenter.addCollect(userid!!, mid!!)
        }
    }

    private var webViewClient: WebViewClient = object : WebViewClient() {

        /**
         * 多页面在同一个WebView中打开，就是不新建activity或者调用系统浏览器打开
         */
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
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

    override fun onDestroy() {
        super.onDestroy()
        if (webView != null) {
            webView.clearHistory()
            (webView.parent as ViewGroup).removeView(webView)
            webView.loadUrl("about:blank")
            webView.stopLoading()
            webView.webChromeClient = null
            webView.webViewClient = null
            webView.destroy()
        }
    }
}
