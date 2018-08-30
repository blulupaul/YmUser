package com.mars.user.ui.main_frg.home

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.WindowManager
import com.lesincs.simpleread.base.BaseFrag
import com.mars.user.R
import com.mars.user.bean.BaseIntResBean
import com.mars.user.bean.BaseNomalResBean
import com.mars.user.ui.home.article.ArticleActivity
import com.mars.user.ui.home.article.mvp.ArticleListContract
import com.mars.user.ui.home.article.mvp.ArticleListPresenter
import com.mars.user.ui.home.ympackage.list.YmPackageListActivity
import com.mars.user.ui.main_frg.home.adapter.HomeModuleEnterRvAdapter
import com.mars.user.ui.main_frg.home.adapter.MeiWenRvAdapter
import com.mars.user.ui.main_frg.home.bean.GetDownMoneyResBean
import com.mars.user.ui.main_frg.home.bean.GetGangGaoweiResBean
import com.mars.user.ui.main_frg.home.bean.GetYmmxListResBean
import com.mars.user.ui.main_frg.home.bean.Gett_waddressResBean
import com.mars.user.ui.main_frg.home.homemvp.HomeContract
import com.mars.user.ui.main_frg.home.homemvp.HomePresenter
import com.mars.user.ui.store.prodetail.ProDetailActivity
import com.mars.user.ui.universal.html5.Html5Activity
import com.mars.user.utils.glideLoad
import com.mars.user.utils.isLogin
import com.mars.user.utils.selfAdaptionImageView
import com.mars.user.utils.showLoginAlert
import com.scwang.smartrefresh.layout.footer.FalsifyFooter
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.frag_home.*

/**
 * Created by gu on 2018/07/18
 * desc: 首页Fragment
 */
class HomeFrag : BaseFrag(), HomeContract.View, ArticleListContract.View {
    override fun onRefreshFinish() {

    }

    override fun onLoadMoreFinish() {

    }

    override fun onRefreshStart() {
        refreshLayout.autoRefresh()
    }

    override fun onRefreshDismiss() {
        refreshLayout.finishRefresh()
    }

    override fun onUpdateNotiCountSuccess(bean: BaseIntResBean) {
        homeModuleEnterRvAdapter.setNewData(presenter.getHomeModuleEnterBeanList(bean.data))
    }

    override fun onUpdateNotiCountFail(msg: String) {
        homeModuleEnterRvAdapter.setNewData(presenter.getHomeModuleEnterBeanList(0))
    }

    override fun onGetYmmxListSuccess(bean: GetYmmxListResBean) {
        meiWenRvAdapter.setNewData(bean.data)
    }

    override fun onGetYmmxListFail(msg: String) {

    }

    override fun onGetDownMoneySuccess(bean: GetDownMoneyResBean) {

    }

    override fun onGetDownMoneyfail(msg: String) {

    }

    override fun onGetGuanggaoweiSuccess(bean: GetGangGaoweiResBean, type: Int) {
        guangGaowei.isClickable = true
        if (type == 0) {
            glideLoad(context!!, bean.data.actmimg, guangGaowei)
        } else {
            if (bean.data.ptype == 1)// 疗程
                ProDetailActivity.startSelf(context!!, bean.data.pid, bean.data.canbuy)
            else if (bean.data.ptype == 2)// 商品
                ProDetailActivity.startSelf(context!!, bean.data.pid, bean.data.canbuy)
        }
    }

    override fun onGetGuangGaoweiFail(msg: String) {

    }

    override fun updateBanner(bannerImgList: List<String>) {
        configBanner(bannerImgList)
    }

    override fun onGetStoreBannerFail(msg: String) {

    }

    override fun onUpdateLHBSuccess(bean: BaseNomalResBean) {

    }

    override fun onUpdateLHBFail(msg: String) {

    }

    override fun onGett_waddressSuccess(bean: Gett_waddressResBean) {

    }

    override fun onGett_waddressFail(msg: String) {

    }

    companion object {
        fun newInstence(): HomeFrag {
            val homeFrag = HomeFrag()
            val bundle = Bundle()
            homeFrag.arguments = bundle
            return homeFrag
        }
    }

    // 首页功能列表
    val homeModuleEnterRvAdapter = HomeModuleEnterRvAdapter()
    //首页美文列表
    val meiWenRvAdapter = MeiWenRvAdapter()

    private val presenter = HomePresenter(this)
    private val meiwenPresenter = ArticleListPresenter(this, 0, 0)

    override fun getLayoutId(): Int {
        return R.layout.frag_home
    }

    override fun configViews() {
        selfAdaptionImageView(context!!, 675, 246, guangGaowei)
        configRefreshLayout()
        setAdapter()
        initListener()
        presenter.onStart()
        meiwenPresenter.sendGetYmmxList(4, 1, 3)
    }

    override fun onResume() {
        super.onResume()
        presenter.sendUpdateNotiCount()
    }

    /**
     * SmartRefreshLayout 配置
     */
    private fun configRefreshLayout() {
        refreshLayout.isEnableLoadMore = false
        refreshLayout.setRefreshFooter(FalsifyFooter(context!!))
        refreshLayout.isEnablePureScrollMode = false
    }

    private fun setAdapter() {
        rv_homeList.layoutManager = GridLayoutManager(context!!, 4)
        rv_homeList.adapter = homeModuleEnterRvAdapter
        homeModuleEnterRvAdapter.setNewData(presenter.getHomeModuleEnterBeanList(0))

        rv_homeShowMeiWen.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        rv_homeShowMeiWen.adapter = meiWenRvAdapter
    }

    private fun initListener() {
        val windowManager = activity!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val screenHeight = windowManager.defaultDisplay.height / 6
        tv_homeBeautiful.isSelected = true
        refreshLayout.setOnRefreshListener {
            presenter.onRefresh()
            presenter.sendUpdateNotiCount()
        }

        tv_homeBeautiful.setOnClickListener {
            isSelected(false)
            it.isSelected = true
            meiwenPresenter.sendGetYmmxList(4, 1, 3)
        }

        tv_homeHealthy.setOnClickListener {
            isSelected(false)
            it.isSelected = true
            meiwenPresenter.sendGetYmmxList(5, 1, 3)
        }

        tv_homeLookMore.setOnClickListener {
            ArticleActivity.startSelf(context!!)
        }

        sl_homeScroll.setOnScrollListener {
            val alpha = it.toFloat() / 1.5f / screenHeight.toFloat() * 1.0f
            if (alpha >= 0 || alpha <= 1) {
                if (alpha >= 0.6) {
                    locationCity.setTextColor(resources.getColor(R.color.gray555))
                    signIn.setTextColor(resources.getColor(R.color.gray555))
                    tv_homeAppName.setTextColor(resources.getColor(R.color.gray555))
                } else {
                    locationCity.setTextColor(resources.getColor(R.color.gray555))
                    signIn.setTextColor(resources.getColor(R.color.gray555))
                    tv_homeAppName.setTextColor(resources.getColor(R.color.gray555))
                }
                bg_view.alpha = alpha
                bg_windowTop.alpha = alpha
            }
        }

        homeModuleEnterRvAdapter.setOnItemClickListener { _, _, position ->
            when (position) {
                0 -> YmPackageListActivity.startSelf(context!!)
                else -> showCenterToast("$position")
            }
        }

        // 美文列表点击
        meiWenRvAdapter.setOnItemClickListener { _, _, position ->
            val mid = meiWenRvAdapter.data[position].mid
            showCenterToast("$mid")
        }

        banner.setOnBannerListener {
            val list = presenter.getBannerDetailList()
            if (!list.isEmpty()) {
                val data = list[it]
                Html5Activity.startSelf(context!!, data, false)
            }
        }

        guangGaowei.setOnClickListener {
            if (isLogin()) {
                guangGaowei.isClickable = false
                presenter.sendGetGuanggaowei(1)
            } else {
                showLoginAlert(context)
            }
        }
    }

    private fun configBanner(list: List<String>) {
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        //设置图片加载器
        banner.setImageLoader(GlideImageLoader())
        //设置图片集合
        banner.setImages(list)
        //设置自动轮播，默认为true
        banner.isAutoPlay(true)
        //设置轮播时间
        banner.setDelayTime(3000)
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER)
        //banner设置方法全部调用完毕时最后调用
        banner.start(640, 375)
    }

    private fun isSelected(bool: Boolean) {
        tv_homeHealthy.isSelected = bool
        tv_homeBeautiful.isSelected = bool
    }
}