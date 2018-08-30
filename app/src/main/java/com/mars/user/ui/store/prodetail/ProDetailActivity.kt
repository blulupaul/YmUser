package com.mars.user.ui.store.prodetail

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import com.mars.user.R
import com.mars.user.base.act.BaseActivity
import com.mars.user.bean.BaseNomalResBean
import com.mars.user.constant.CAN_BUY
import com.mars.user.constant.P_ID
import com.mars.user.event.MainTabChangeEvent
import com.mars.user.ui.store.prodetail.adapter.ProPjRvAdapter
import com.mars.user.ui.store.prodetail.bean.GetProDetailInfoResBean
import com.mars.user.ui.store.prodetail.mvp.ProDetailContract
import com.mars.user.ui.store.prodetail.mvp.ProDetailPresenter
import com.mars.user.ui.store.proevaluation.ProEvalutionActivity
import com.mars.user.utils.getUserid
import com.mars.user.utils.glideLoad
import com.mars.user.utils.selfAdaptionImageView
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.scwang.smartrefresh.layout.footer.FalsifyFooter
import kotlinx.android.synthetic.main.activity_pro_detail.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by gu on 2018/07/31
 * desc: 云猫商城商品详情
 */

class ProDetailActivity : BaseActivity(), ProDetailContract.View {
    override fun onRefreshStart() {
        refreshLayout.autoRefresh()
    }

    override fun onRefreshDismiss() {
        refreshLayout.finishRefresh(500)
    }

    override fun onGetProDetailInfoSuccess(bean: GetProDetailInfoResBean) {
        setClickable(true)
        bindViews(bean)
    }

    override fun onGetProDetailInfoFail(msg: String) {
        setClickable(false)
        showFailAlert(msg)
    }

    override fun onGetIsGoumaiSuccess(bean: BaseNomalResBean) {
        buyNow.setBackgroundResource(R.drawable.buy_main_theme_color_bg_shape)

        isGoumai = true
    }

    override fun onGetIsGoumaiFail(msg: String) {
        buyNow.isClickable = false
        buyNow.isFocusable = false
        buyNow.setBackgroundResource(R.drawable.buy_jbdk_unclick_bg_shape)

        isGoumai = false
    }

    override fun onAddCollectSuccess(bean: BaseNomalResBean) {
        collect.setImageResource(R.mipmap.round_collect_icon)
        isCollect = true
    }

    override fun onAddCollectFail(msg: String) {
        showFailAlert(msg)
    }

    override fun onCancelCollectSuccess(bean: BaseNomalResBean) {
        showSuccessAlert(bean.msg)
        collect.setImageResource(R.mipmap.round_uncollect_icon)
        isCollect = false
    }

    override fun onCancelCollectFail(msg: String) {
        showFailAlert(msg)
    }

    override fun onAddGwcSuccess(bean: BaseNomalResBean) {
        QMUIDialog.MessageDialogBuilder(this)
                .setTitle("加入购物车成功")
                .setMessage("是否直接进入购物车")
                .addAction("取消") { dialog, _ ->
                    dialog.dismiss()
                }
                .addAction("确定") { dialog, _ ->
                    dialog.dismiss()
                    val event = MainTabChangeEvent()
                    event.id = R.id.fragment_cart
                    EventBus.getDefault().post(event)
                    finish()
                }
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show()
    }

    override fun onAddGwcFail(msg: String) {
        showFailAlert(msg)
    }

    private val presenter = ProDetailPresenter(this, this)
    private var proPjRvAdapter: ProPjRvAdapter? = null
    private var userid: Int? = null
    private var isGoumai: Boolean? = null
    private var isCollect: Boolean? = null
    private var pid: Int? = 0
    private var canbuy: Int? = 0

    companion object {
        fun startSelf(context: Context, pid: Int, canbuy: Int) {
            val intent = Intent(context, ProDetailActivity::class.java)
            intent.putExtra(P_ID, pid)
            intent.putExtra(CAN_BUY, canbuy)
            context.startActivity(intent)
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_pro_detail
    }

    override fun configViews() {
//        QMUIStatusBarHelper.translucent(this)
        userid = getUserid()
        pid = intent.getIntExtra(P_ID, 0)
        canbuy = intent.getIntExtra(CAN_BUY, 0)
        selfAdaptionImageView(this, 640, 542, proImg)
        configWebView()
        configRv()
        initListener()

        if (canbuy == 0) {
            addgwc.isClickable = false
            addgwc.isFocusable = false
            addgwc.setBackgroundResource(R.drawable.add_gwc_unclick_bg_shape)

            buyNow.isClickable = false
            buyNow.isFocusable = false
            buyNow.setBackgroundResource(R.drawable.buy_unclick_bg_shape)
        }
        oldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        presenter.onStart(userid!!, pid!!)
    }

    private fun configRv() {
        proPjRvAdapter = ProPjRvAdapter(false)
        pjList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        pjList.adapter = proPjRvAdapter
    }

    private fun configWebView() {
        val settings = proDetail.settings
        //支持缩放，默认为true。
        settings.setSupportZoom(false)
        settings.javaScriptEnabled = true
        //调整图片至适合webview的大小
        settings.useWideViewPort = true
        // 缩放至屏幕的大小
        settings.loadWithOverviewMode = true
        //设置默认编码
        settings.defaultTextEncodingName = "utf-8"
        //设置自动加载图片
        settings.loadsImagesAutomatically = true
        //关闭webview中缓存
        settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
    }

    /**
     * 点击事件
     */
    private fun initListener() {
        back.setOnClickListener {
            finish()
        }

        collect.setOnClickListener {
            if (isCollect!!) {
                presenter.cancelCollect(userid!!, pid!!, 0)
            } else {
                presenter.addCollect(userid!!, pid!!, 0)
            }
        }

        share.setOnClickListener {
            presenter.showShareAlert()
        }

        addgwc.setOnClickListener {
            presenter.addGwc(pid!!, 1, userid!!)
        }

        refreshLayout.apply {
            isEnableLoadMore = false
            setRefreshFooter(FalsifyFooter(this@ProDetailActivity))
            setOnRefreshListener {
                presenter.onStart(userid!!, pid!!)
            }
        }

        buyNow.setOnClickListener {
            if (isGoumai == null) {
                showCenterToast("立即购买")
            } else {
                showCenterToast("立即兑换")
            }
        }

        proPjAll.setOnClickListener {
            ProEvalutionActivity.startSelf(this, pjDataList)
        }
    }

    private val pjDataList = ArrayList<GetProDetailInfoResBean.Data.Evaluation>()

    private fun bindViews(bean: GetProDetailInfoResBean) {
        pjDataList.clear()
        val data = bean.data[0].product
        val sppjcount = bean.data[0].sppjcount

        if (sppjcount != 0) {
            pjList.visibility = View.VISIBLE
            proPjAll.visibility = View.VISIBLE
            pjDataList.addAll(bean.data[0].evaluationList)
            proPjRvAdapter?.setNewData(pjDataList)
        } else {
            pjList.visibility = View.GONE
            proPjAll.visibility = View.GONE
        }

        if (data.buymiss == 1) {
            addgwc.isClickable = false
            addgwc.isFocusable = false
            addgwc.setBackgroundResource(R.drawable.add_gwc_unclick_bg_shape)
        }

        if (data.issc == 1) {
            isCollect = true
            collect.setImageResource(R.mipmap.round_collect_icon)
        } else {
            isCollect = false
            collect.setImageResource(R.mipmap.round_uncollect_icon)
        }

        glideLoad(this, data.pimages, proImg)
        proName.text = data.pname
        proPrice.text = "￥ ${data.price}"
        oldPrice.text = "￥ ${data.oldprice}"
        proPj.text = "商品评价( $sppjcount )"
        functionaldes.text = data.pdis
        proDetail.loadData(data.pdetails, "text/html; charset=UTF-8", null)

        val jbdk = data.jbdk
        val pid = data.pid
        if (data.promark == 1) {
            addgwc.isClickable = false
            addgwc.isFocusable = false
            addgwc.visibility = View.GONE
            addgwc.setBackgroundResource(R.drawable.add_gwc_unclick_bg_shape)
            buyNow.text = "立即抵扣"

            presenter.getIsGoumai(userid!!, pid, jbdk.toInt())
        }
    }

    private fun setClickable(isClick: Boolean) {
        collect.isEnabled = isClick
        share.isEnabled = isClick
        addgwc.isEnabled = isClick
        buyNow.isEnabled = isClick
        proPjAll.isEnabled = isClick
    }

    @Override
    override fun onDestroy() {
        proDetail.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
        proDetail.clearHistory()
        (proDetail.parent as ViewGroup).removeView(proDetail)
        proDetail.destroy()
        super.onDestroy()
    }
}
