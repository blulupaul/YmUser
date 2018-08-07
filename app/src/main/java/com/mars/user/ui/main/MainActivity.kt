package com.mars.user.ui.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import cn.nekocode.rxlifecycle.RxLifecycle
import com.mars.user.R
import com.mars.user.event.MainTabChangeEvent
import com.mars.user.ui.main.bean.GetUpdateAppResBean
import com.mars.user.ui.main.mvp.MainContract
import com.mars.user.ui.main.mvp.MainPresenter
import com.mars.user.utils.getVersionCode
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by gu on 2018/8/6
 * Desc: 主界面
 */

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, MainContract.View {

    override fun onUpdateSuccess(bean: GetUpdateAppResBean) {
        if (getVersionCode(this) < bean.data[0].versionCode) {
//            T.showSuccessAlert(this, "有更新")
        }
    }

    override fun onUpdateFail(msg: String) {
        // no operation
//        T.showFailAlert(this, "没有更新")
    }

    override fun onServerResError(t: Throwable) {
        // no operation
//        T.showFailAlert(this, "错误了")
    }

    override fun getContentId(): Int {
        return R.id.contentMain
    }

    override fun getRxLifeCycle(): RxLifecycle {
        return RxLifecycle.bind(this)
    }

    val mainPresenter = MainPresenter(this, supportFragmentManager!!)

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        mainPresenter.changeFragment(item.itemId)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EventBus.getDefault().register(this)
        QMUIStatusBarHelper.translucent(this)
        QMUIStatusBarHelper.setStatusBarLightMode(this)
        configBottomNv()
    }

    override fun onResume() {
        super.onResume()
        mainPresenter.updateAppInfo(0)
    }

    /**
     * 配置底部导航
     */
    private fun configBottomNv() {
        bnv_mainBottom.onNavigationItemSelectedListener = this
        bnv_mainBottom.selectedItemId = R.id.fragment_home
        bnv_mainBottom.enableShiftingMode(false)
        bnv_mainBottom.enableItemShiftingMode(false)
        bnv_mainBottom.setIconSize(20F, 20F)
        bnv_mainBottom.setTextSize(10f)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onChangeEvent(event: MainTabChangeEvent) {
        mainPresenter.changeFragment(event.id!!)
        bnv_mainBottom.selectedItemId = event.id!!
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
