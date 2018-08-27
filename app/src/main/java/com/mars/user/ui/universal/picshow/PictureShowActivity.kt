package com.mars.user.ui.universal.picshow

import android.content.Context
import android.content.Intent
import com.mars.user.R
import com.mars.user.base.act.BaseActivity
import com.mars.user.constant.URL_LIST
import com.mars.user.ui.main_frg.home.GlideImageLoader
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.frag_home.*

/**
 * Created by gu on 2018/8/6
 * Desc: 大图列表展示
 */
class PictureShowActivity : BaseActivity() {
    override fun setLayoutId(): Int {
        return R.layout.activity_pic_show
    }

    companion object {
        fun startSelf(context: Context, list: ArrayList<String>) {
            val intent = Intent(context, PictureShowActivity::class.java)
            intent.putExtra(URL_LIST, list)
            context.startActivity(intent)
        }
    }

    override fun configViews() {
        QMUIStatusBarHelper.translucent(this)
        val list = intent.getStringArrayListExtra(URL_LIST)
        configBanner(list)
        bannerClick()
    }

    private fun bannerClick() {
        banner.setOnBannerListener {
            finish()
        }
    }

    private fun configBanner(list: List<String>) {
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR)
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
        banner.start()
    }
}
